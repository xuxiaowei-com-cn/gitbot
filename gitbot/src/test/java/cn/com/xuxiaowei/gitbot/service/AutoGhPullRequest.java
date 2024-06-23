package cn.com.xuxiaowei.gitbot.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
// @SpringBootTest
class AutoGhPullRequest {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	// @Test
	void mb() throws IOException {

		String oauthToken = System.getenv("GITBOT_GITHUB_TOKEN");

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1085));

		GitHub github = new GitHubBuilder().withOAuthToken(oauthToken).withProxy(proxy).build();

		GHRepository ghRepository = github.getRepositoryById(766709711);

		List<GHPullRequest> pullRequests = ghRepository.getPullRequests(GHIssueState.OPEN);

		for (GHPullRequest pullRequest : pullRequests) {

			URL htmlUrl = pullRequest.getHtmlUrl();
			String string = htmlUrl.toString();
			String substring = string.substring(8);

			stringRedisTemplate.opsForHash().put("auto:pull-request:" + substring, "1", "1");

			log.info(String.valueOf(htmlUrl));
		}
	}

	@Test
	void ma() throws IOException {

		String oauthToken = System.getenv("GITBOT_GITHUB_TOKEN");

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1085));

		GitHub github = new GitHubBuilder().withOAuthToken(oauthToken).withProxy(proxy).build();

		GHRepository ghRepository = github.getRepositoryById(766709711);

		GHPullRequest pullRequest = ghRepository.getPullRequest(172);
		URL htmlUrl = pullRequest.getHtmlUrl();

		Boolean mergeable = pullRequest.getMergeable();
		if (mergeable == null) {
			throw new RuntimeException(String.format("此 PR: %s 状态尚未确定", htmlUrl));
		}
		else if (!mergeable) {
			throw new RuntimeException(String.format("此 PR: %s 无法进行合并", htmlUrl));
		}

		GHIssueState state = pullRequest.getState();
		if (state == GHIssueState.CLOSED) {
			log.info("此 PR: {} 已关闭", htmlUrl);
			return;
		}

		GHCommitPointer base = pullRequest.getBase();
		String sha = base.getSha();
		String sha1 = ghRepository.getBranch(base.getRef()).getSHA1();

		boolean equals = sha.equals(sha1);
		if (equals) {
			pullRequest.merge("此 PR 由 https://github.com/xuxiaowei-com-cn/gitbot 执行合并");
		}
		else {
			pullRequest.comment("@dependabot rebase");
			throw new RuntimeException(String.format("此 PR: %s 正在执行 rebase", htmlUrl));
		}

		String mergeableState = pullRequest.getMergeableState();
		if ("dirty".equals(mergeableState)) {
			log.info("");
		}
		else if ("clean".equals(mergeableState)) {
			log.info("");
		}
		else if ("unstable".equals(mergeableState)) {
			log.info("");
		}
		else {
			throw new RuntimeException(String.format("%s : 未知 mergeableState: %s", htmlUrl, mergeableState));
		}

		log.info("");
	}

}
