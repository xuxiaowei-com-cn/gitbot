package cn.com.xuxiaowei.gitbot.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHIssueState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class IGhRepositoryServiceTests {

	@Autowired
	private IGhRepositoryService ghRepositoryService;

	@Test
	void saveMyOrganizationRepository() throws IOException {
		String gitbotGithubEnable = System.getenv("GITBOT_GITHUB_ENABLE");
		log.info(gitbotGithubEnable);
		if (Boolean.TRUE.toString().equals(gitbotGithubEnable)) {
			String oauthToken = System.getenv("GITBOT_GITHUB_TOKEN");
			boolean saveBranch = false;
			boolean savePullRequest = false;
			GHIssueState issueState = GHIssueState.OPEN;
			ghRepositoryService.saveMyOrganizationRepository(oauthToken, saveBranch, savePullRequest, issueState);
		}
		else {
			log.info("跳过 GitHub 测试");
		}
	}

	@Test
	void saveMyselfRepository() throws IOException {
		String gitbotGithubEnable = System.getenv("GITBOT_GITHUB_ENABLE");
		log.info(gitbotGithubEnable);
		if (Boolean.TRUE.toString().equals(gitbotGithubEnable)) {
			String oauthToken = System.getenv("GITBOT_GITHUB_TOKEN");
			boolean saveBranch = true;
			boolean savePullRequest = true;
			GHIssueState issueState = GHIssueState.OPEN;
			ghRepositoryService.saveMyselfRepository(oauthToken, saveBranch, savePullRequest, issueState);
		}
		else {
			log.info("跳过 GitHub 测试");
		}
	}

}
