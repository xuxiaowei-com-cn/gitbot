package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.ScheduledToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class IScheduledTokenServiceTests {

	@Autowired
	private IScheduledTokenService scheduledTokenService;

	@Test
	void list() {
		List<ScheduledToken> scheduledTokenList = scheduledTokenService.list();
		log.info(String.valueOf(scheduledTokenList.size()));
	}

	@Test
	void listByHostNotGitHub() {
		List<ScheduledToken> scheduledTokenList = scheduledTokenService.listByHostNotGitHub();
		log.info(String.valueOf(scheduledTokenList.size()));
	}

	@Test
	void getByHostGitHub() {
		ScheduledToken scheduledToken = scheduledTokenService.getByHostGitHub();
		log.info(String.valueOf(scheduledToken));
	}

}
