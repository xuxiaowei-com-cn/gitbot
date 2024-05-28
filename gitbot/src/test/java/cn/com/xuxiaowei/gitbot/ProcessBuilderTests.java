package cn.com.xuxiaowei.gitbot;

import cn.com.xuxiaowei.gitbot.utils.ProcessBuilderUtils;
import cn.com.xuxiaowei.gitbot.vo.CommandVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class ProcessBuilderTests {

	@Test
	void command() throws IOException, InterruptedException {
		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String[] command;
		if (isWindows) {
			command = new String[] { "ipconfig" };
		}
		else {
			command = new String[] { "mvn", "-v" };
		}

		CommandVo commandVo = ProcessBuilderUtils.command(command);
		log.info(String.valueOf(commandVo));
	}

}
