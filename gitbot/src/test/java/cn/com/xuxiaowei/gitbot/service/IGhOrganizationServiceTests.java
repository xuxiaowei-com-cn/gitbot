package cn.com.xuxiaowei.gitbot.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class IGhOrganizationServiceTests {

	@Autowired
	private IGhOrganizationService ghOrganizationService;

	@Test
	void saveMyOrganizations() throws IOException {
		String gitbotGithubEnable = System.getenv("GITBOT_GITHUB_ENABLE");
		log.info(gitbotGithubEnable);
		if (Boolean.TRUE.toString().equals(gitbotGithubEnable)) {
			String oauthToken = System.getenv("GITBOT_GITHUB_TOKEN");
			ghOrganizationService.saveMyOrganizations(oauthToken);
		}
		else {
			log.info("跳过 GitHub 测试");
		}
	}

}
