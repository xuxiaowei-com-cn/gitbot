package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.constant.LogConstants;
import cn.com.xuxiaowei.gitbot.constant.RedisKeyConstants;
import cn.com.xuxiaowei.gitbot.entity.GlBranch;
import cn.com.xuxiaowei.gitbot.mapper.GlBranchMapper;
import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import cn.com.xuxiaowei.gitbot.service.IGlBranchService;
import cn.com.xuxiaowei.gitbot.utils.DateUtils;
import cn.com.xuxiaowei.gitbot.utils.RedisKeyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Slf4j
@Service
public class GlBranchServiceImpl extends ServiceImpl<GlBranchMapper, GlBranch> implements IGlBranchService {

	private GitbotProperties gitbotProperties;

	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	public void setGitbotProperties(GitbotProperties gitbotProperties) {
		this.gitbotProperties = gitbotProperties;
	}

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Override
	public void saveOwnedBranch(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken,
			Object projectIdOrPath) throws GitLabApiException, MalformedURLException {

		URL url = new URL(hostUrl);
		String host = url.getHost();

		String id = MDC.get(LogConstants.G_REQUEST_ID);
		if (!StringUtils.hasText(id)) {
			id = UUID.randomUUID().toString();
		}

		Long dataTimeout = gitbotProperties.getDataTimeout();
		TimeUnit dataUnit = gitbotProperties.getDataUnit();
		String saveBranchRedisKeyPrefix = gitbotProperties.getSaveBranchRedisKeyPrefix();
		String saveBranchRedisHashKey = RedisKeyUtils.hash(saveBranchRedisKeyPrefix, host, id);

		// @formatter:off
		stringRedisTemplate.opsForHash().put(saveBranchRedisHashKey, RedisKeyConstants.START_AT, DateUtils.format(LocalDateTime.now()));
		// @formatter:on

		try (GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken)) {
			gitLabApi.setIgnoreCertificateErrors(true);

			long projectId;
			if (projectIdOrPath instanceof Long) {
				projectId = (Long) projectIdOrPath;
			}
			else {
				Project project = gitLabApi.getProjectApi().getProject(projectIdOrPath);
				projectId = project.getId();
			}

			List<Branch> branches;
			try {
				branches = gitLabApi.getRepositoryApi().getBranches(projectIdOrPath);
			}
			catch (GitLabApiException e) {
				int httpStatus = e.getHttpStatus();
				if (httpStatus == 404) {
					log.error("分支不存在：", e);
				}
				else {
					log.error("获取分支异常：", e);
				}
				return;
			}
			for (Branch branch : branches) {

				// @formatter:off
				GlBranch glBranch = new GlBranch();
				glBranch.setProjectId(projectId);
				glBranch.setHost(host);
				glBranch.setName(branch.getName());
				glBranch.setDevelopersCanMerge(branch.getDevelopersCanMerge());
				glBranch.setDevelopersCanPush(branch.getDevelopersCanPush());
				glBranch.setMerged(branch.getMerged());
				glBranch.setIsProtected(branch.getProtected());
				glBranch.setIsDefault(branch.getDefault());
				glBranch.setCanPush(branch.getCanPush());
				glBranch.setWebUrl(branch.getWebUrl());

				Commit commit = branch.getCommit();

				// glBranch.setCommitAuthor(commit.getAuthor());
				glBranch.setCommitAuthoredDate(commit.getAuthoredDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glBranch.setCommitAuthorEmail(commit.getAuthorEmail());
				glBranch.setCommitAuthorName(commit.getAuthorName());
				glBranch.setCommitCommittedDate(commit.getCommittedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glBranch.setCommitCommitterEmail(commit.getCommitterEmail());
				glBranch.setCommitCommitterName(commit.getCommitterName());
				glBranch.setCommitCreatedAt(commit.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glBranch.setCommitId(commit.getId());
				glBranch.setCommitMessage(commit.getMessage());
				glBranch.setCommitParentIds(commit.getParentIds() == null ? null : Joiner.on(",").join(commit.getParentIds()));
				glBranch.setCommitShortId(commit.getShortId());
				// glBranch.setCommitStats(commit.getStats());
				glBranch.setCommitStatus(commit.getStatus());
				glBranch.setCommitTimestamp(commit.getTimestamp() == null ? null : commit.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glBranch.setCommitTitle(commit.getTitle());
				glBranch.setCommitUrl(commit.getUrl());
				glBranch.setCommitWebUrl(commit.getWebUrl());
				glBranch.setCommitProjectId(commit.getProjectId());
				glBranch.setCommitLastPipelineId(commit.getLastPipeline() == null ? null : commit.getLastPipeline().getId());
				glBranch.setCommitLastPipelineIid(commit.getLastPipeline() == null ? null : commit.getLastPipeline().getIid());
				// @formatter:on

				QueryWrapper<GlBranch> queryWrapper = new QueryWrapper<GlBranch>()
					//
					.eq("project_id", glBranch.getProjectId())
					//
					.eq("`host`", glBranch.getHost())
					//
					.eq("name", glBranch.getName());
				long count = count(queryWrapper);
				if (count == 0) {
					save(glBranch);
					// @formatter:off
					stringRedisTemplate.opsForHash().increment(saveBranchRedisHashKey, RedisKeyConstants.SAVE, 1);
					// @formatter:on
				}
				else {
					update(glBranch, queryWrapper);
					// @formatter:off
					stringRedisTemplate.opsForHash().increment(saveBranchRedisHashKey, RedisKeyConstants.UPDATE, 1);
					// @formatter:on
				}

			}

		}
		finally {
			stringRedisTemplate.expire(saveBranchRedisHashKey, dataTimeout, dataUnit);

			// @formatter:off
			log.debug("saved: {}", stringRedisTemplate.opsForHash().get(saveBranchRedisHashKey, RedisKeyConstants.SAVE));
			log.debug("updated: {}", stringRedisTemplate.opsForHash().get(saveBranchRedisHashKey, RedisKeyConstants.UPDATE));
			// @formatter:on
		}

		// @formatter:off
		stringRedisTemplate.opsForHash().put(saveBranchRedisHashKey, RedisKeyConstants.END_AT, DateUtils.format(LocalDateTime.now()));
		// @formatter:on
	}

}
