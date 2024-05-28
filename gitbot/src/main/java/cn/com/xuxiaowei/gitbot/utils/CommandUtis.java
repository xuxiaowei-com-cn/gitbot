package cn.com.xuxiaowei.gitbot.utils;

import cn.com.xuxiaowei.gitbot.vo.CommandVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
public class CommandUtis {

	/**
	 * @param process
	 * @param charsetName
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static CommandVo process(Process process, String charsetName) throws IOException, InterruptedException {
		InputStream is = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}

		int exitCode = process.waitFor();

		CommandVo commandVo = new CommandVo();
		commandVo.setExitCode(exitCode);
		commandVo.setMessage(sb.toString());
		return commandVo;
	}

}
