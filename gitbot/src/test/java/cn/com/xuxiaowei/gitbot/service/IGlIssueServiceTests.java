package cn.com.xuxiaowei.gitbot.service;

import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class IGlIssueServiceTests {

	@Autowired
	private IGlIssueService glIssueService;

	@Test
	void saveIssue() throws MalformedURLException, GitLabApiException {
		String hostUrl = "https://jihulab.com";
		boolean ignoreCertificateErrors = true;
		String personalAccessToken = System.getenv("GITBOT_GITLAB_TOKEN");
		glIssueService.saveIssue(hostUrl, ignoreCertificateErrors, personalAccessToken);
	}

}
