package cn.com.xuxiaowei.gitbot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class RuntimeTests {

	@Test
	void command() {

		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String command;
		String charsetName;
		if (isWindows) {
			command = "ipconfig /all";
			charsetName = "GBK";
		}
		else {
			command = "mvn -v";
			charsetName = "UTF-8";
		}

		try {
			Process process = Runtime.getRuntime().exec(command);

			InputStream is = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));

			String line;
			while ((line = reader.readLine()) != null) {
				log.info(line);
			}

			int exitCode = process.waitFor();
			log.info("Process exited with code: {}", exitCode);
		}
		catch (IOException | InterruptedException e) {
			log.error("测试命令异常：", e);
		}
	}

}
