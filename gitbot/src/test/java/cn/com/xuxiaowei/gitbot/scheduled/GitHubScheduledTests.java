package cn.com.xuxiaowei.gitbot.scheduled;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
// @SpringBootTest
class GitHubScheduledTests {

	// @Autowired
	private GitHubScheduled gitHubScheduled;

	// @Test
	void saveMyselfRepository() throws IOException {
		gitHubScheduled.saveMyselfRepository();
	}

}
