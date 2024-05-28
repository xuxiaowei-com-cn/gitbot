package cn.com.xuxiaowei.gitbot;

import cn.com.xuxiaowei.gitbot.utils.RuntimeUtils;
import cn.com.xuxiaowei.gitbot.vo.CommandVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class RuntimeTests {

	@Test
	void command() throws IOException, InterruptedException {

		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String command;
		if (isWindows) {
			command = "ipconfig /all";
		}
		else {
			command = "mvn -v";
		}

		CommandVo commandVo = RuntimeUtils.command(command);
		log.info(String.valueOf(commandVo));
	}

}
