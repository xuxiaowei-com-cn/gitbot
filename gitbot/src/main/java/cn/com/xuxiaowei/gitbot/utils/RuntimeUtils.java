package cn.com.xuxiaowei.gitbot.utils;

import cn.com.xuxiaowei.gitbot.vo.CommandVo;

import java.io.IOException;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
public class RuntimeUtils {

	/**
	 * @param command
	 * @param charsetName
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static CommandVo command(String command, String charsetName) throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec(command);

		return CommandUtis.process(process, charsetName);
	}

	/**
	 * @param command
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static CommandVo command(String command) throws IOException, InterruptedException {
		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String charsetName;
		if (isWindows) {
			charsetName = "GBK";
		}
		else {
			charsetName = "UTF-8";
		}

		return command(command, charsetName);
	}

}
