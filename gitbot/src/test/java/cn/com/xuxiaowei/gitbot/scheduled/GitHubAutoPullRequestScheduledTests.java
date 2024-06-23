package cn.com.xuxiaowei.gitbot.scheduled;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
// @SpringBootTest
class GitHubAutoPullRequestScheduledTests {

	// @Autowired
	private GitHubAutoPullRequestScheduled gitHubAutoPullRequestScheduled;

	// @Test
	void autoPullRequest() throws IOException {
		gitHubAutoPullRequestScheduled.autoPullRequest();
	}

}
