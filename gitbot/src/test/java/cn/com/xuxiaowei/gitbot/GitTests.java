package cn.com.xuxiaowei.gitbot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Git 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class GitTests {

	@Test
	void gitClone() {
		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String[] command = new String[] { "git", "clone", "-b", "spring-boot-2",
				"https://gitee.com/xuxiaowei-cloud/spring-cloud-xuxiaowei.git", "spring-cloud-xuxiaowei-2" };
		String charsetName;
		if (isWindows) {
			charsetName = "GBK";
		}
		else {
			charsetName = "UTF-8";
		}

		try {
			ProcessBuilder builder = new ProcessBuilder(command);
			Process process = builder.start();

			InputStream is = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));

			String line;
			while ((line = reader.readLine()) != null) {
				log.info(line);
			}

			int exitCode = process.waitFor();
			log.info("Process exited with code: {}", exitCode);

			assertEquals(0, exitCode);
		}
		catch (IOException | InterruptedException e) {
			log.error("克隆代码异常：", e);
		}
	}

}
