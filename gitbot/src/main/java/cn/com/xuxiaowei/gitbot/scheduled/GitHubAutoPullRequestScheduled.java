package cn.com.xuxiaowei.gitbot.scheduled;

import cn.com.xuxiaowei.gitbot.constant.RedisKeyConstants;
import cn.com.xuxiaowei.gitbot.entity.ScheduledToken;
import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import cn.com.xuxiaowei.gitbot.service.IScheduledTokenService;
import cn.com.xuxiaowei.gitbot.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 1. 增加锁定分支的功能，一个分支的 PR，限制同时只有一个进行触发 rebase
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "gitbot.scheduled.auto-pull-request", havingValue = "true")
public class GitHubAutoPullRequestScheduled {

	private StringRedisTemplate stringRedisTemplate;

	private GitbotProperties gitbotProperties;

	private IScheduledTokenService scheduledTokenService;

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Autowired
	public void setGitbotProperties(GitbotProperties gitbotProperties) {
		this.gitbotProperties = gitbotProperties;
	}

	@Autowired
	public void setScheduledTokenService(IScheduledTokenService scheduledTokenService) {
		this.scheduledTokenService = scheduledTokenService;
	}

	/**
	 * 注意：项目未异步方法执行功能时，定时器如果执行时间大于间隔时间，不会重复执行
	 * @throws IOException
	 */
	@Scheduled(fixedRate = 6_000)
	public void autoPullRequest() throws IOException {
		String className = getClass().getName();
		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		log.info("Start Class: {} Method: {}", className, methodName);

		String autoPullRequestRedisKeyPrefix = gitbotProperties.getAutoPullRequestRedisKeyPrefix();
		String autoPullRequestBranchRedisKeyPrefix = gitbotProperties.getAutoPullRequestBranchRedisKeyPrefix();

		Set<String> keys = stringRedisTemplate.keys(autoPullRequestRedisKeyPrefix + "*");

		int size = keys.size();
		if (size == 0) {
			log.info("不存在需要进行的 PR");
			return;
		}

		ScheduledToken scheduledToken = scheduledTokenService.getByHostGitHub();
		String token = scheduledToken.getToken();
		GitHub github = new GitHubBuilder().withOAuthToken(token).build();

		for (String key : keys) {

			Object startStr = stringRedisTemplate.opsForHash().get(key, RedisKeyConstants.EXECUTE_AT);
			if (startStr != null) {
				LocalDateTime parse = DateUtils.parse(String.valueOf(startStr));
				if (LocalDateTime.now().isBefore(parse)) {
					log.info("{} 执行时间未到", key);
					continue;
				}
			}

			String pullRequestUrl = key.substring(gitbotProperties.getAutoPullRequestRedisKeyPrefix().length());
			String pullRequestIdStr = pullRequestUrl.substring(pullRequestUrl.lastIndexOf("/") + 1);
			String repositoryKey = pullRequestUrl.substring(0, pullRequestUrl.lastIndexOf("/") - 5);
			String repositoryName = pullRequestUrl.substring(11, pullRequestUrl.lastIndexOf("/") - 5);
			int pullRequestId = Integer.parseInt(pullRequestIdStr);

			GHRepository ghRepository = github.getRepository(repositoryName);
			GHPullRequest pullRequest = ghRepository.getPullRequest(pullRequestId);
			URL htmlUrl = pullRequest.getHtmlUrl();
			GHCommitPointer base = pullRequest.getBase();
			String ref = base.getRef();
			String sha = base.getSha();
			String sha1 = ghRepository.getBranch(ref).getSHA1();

			String branchKey = autoPullRequestBranchRedisKeyPrefix + repositoryKey + ":" + ref;

			stringRedisTemplate.opsForHash().put(key, RedisKeyConstants.REF, ref);

			GHIssueState state = pullRequest.getState();
			if (state == GHIssueState.CLOSED) {
				log.info("此 PR: {} 已关闭", htmlUrl);
				stringRedisTemplate.delete(key);
				stringRedisTemplate.delete(branchKey);
				continue;
			}

			Boolean mergeable = pullRequest.getMergeable();
			if (mergeable == null) {
				long minutes = 1;
				stringRedisTemplate.opsForHash()
					.put(key, RedisKeyConstants.EXECUTE_AT, DateUtils.format(LocalDateTime.now().plusMinutes(minutes)));
				log.error("此 PR: {} 状态尚未确定，{} 分钟后才能继续执行", htmlUrl, minutes);
				continue;
			}
			else if (!mergeable) {
				long minutes = 1;
				stringRedisTemplate.opsForHash()
					.put(key, RedisKeyConstants.EXECUTE_AT, DateUtils.format(LocalDateTime.now().plusMinutes(minutes)));
				log.error("此 PR: {} 无法进行合并，{} 分钟后才能继续执行", htmlUrl, minutes);
				continue;
			}

			String mergeableState = pullRequest.getMergeableState();
			if ("dirty".equals(mergeableState)) {
				long minutes = 1;
				stringRedisTemplate.opsForHash()
					.put(key, RedisKeyConstants.EXECUTE_AT, DateUtils.format(LocalDateTime.now().plusMinutes(minutes)));
				log.error("{} : 无法执行合并，{} 分钟后才能继续执行", htmlUrl, minutes);
				continue;
			}
			else if ("clean".equals(mergeableState)) {
				log.info("{} : {}", htmlUrl, mergeableState);
			}
			else if ("unstable".equals(mergeableState)) {
				long minutes = 1;
				stringRedisTemplate.opsForHash()
					.put(key, RedisKeyConstants.EXECUTE_AT, DateUtils.format(LocalDateTime.now().plusMinutes(minutes)));
				log.warn("{} : 流水线正在执行，{} 分钟后才能继续执行", htmlUrl, minutes);

				String string = stringRedisTemplate.opsForValue().get(branchKey);
				if (string == null) {
					stringRedisTemplate.opsForValue().set(branchKey, pullRequestUrl);
				}

				continue;
			}
			else {
				long minutes = 1;
				stringRedisTemplate.opsForHash()
					.put(key, RedisKeyConstants.EXECUTE_AT, DateUtils.format(LocalDateTime.now().plusMinutes(minutes)));
				log.error("{} : 未知 mergeableState: {}，{} 分钟后才能继续执行", htmlUrl, mergeableState, minutes);
				continue;
			}

			boolean equals = sha.equals(sha1);
			if (equals) {
				pullRequest.merge("此 PR 由 https://github.com/xuxiaowei-com-cn/gitbot 执行合并");
				stringRedisTemplate.delete(key);
				stringRedisTemplate.delete(branchKey);
				log.info("{} 已完成合并", htmlUrl);
			}
			else {

				String string = stringRedisTemplate.opsForValue().get(branchKey);
				if (string == null || pullRequestUrl.equals(string)) {
					stringRedisTemplate.opsForValue().set(branchKey, pullRequestUrl);

					pullRequest.comment("@dependabot rebase");
					long minutes = 1;
					stringRedisTemplate.opsForHash()
						.put(key, RedisKeyConstants.EXECUTE_AT,
								DateUtils.format(LocalDateTime.now().plusMinutes(minutes)));
					log.info("此 PR: {} 正在执行 rebase，{} 分钟后才能继续执行", htmlUrl, minutes);
				}
				else {

					long minutes = 1;
					stringRedisTemplate.opsForHash()
						.put(key, RedisKeyConstants.EXECUTE_AT,
								DateUtils.format(LocalDateTime.now().plusMinutes(minutes)));

					log.info("分支 {} 已被 PR {} 锁定，等待 PR 完成", ref, htmlUrl);
				}
			}
		}

		log.info("End Class: {} Method: {}", className, methodName);
	}

}
