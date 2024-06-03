package cn.com.xuxiaowei.gitbot.utils;

import cn.com.xuxiaowei.gitbot.vo.CommandVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.util.UUID;

/**
 * Git 工具
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
public class GitUtils {

	/**
	 * 克隆
	 * @param gitexe
	 * @param url
	 * @param username
	 * @param token
	 * @param branch
	 * @param folder
	 */
	public static CommandVo gitClone(String gitexe, String url, String username, String token, String branch,
			String folder) throws IOException, InterruptedException {

		String tempDir = System.getProperty("java.io.tmpdir");
		log.debug("用户临时文件夹：{}", tempDir);

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().path(tempDir).path("gitbot-tmp");

		if (folder != null) {
			if (folder.contains("..")) {
				throw new GitbotRuntimeException("文件夹不合法");
			}
			else {
				uriComponentsBuilder.path("/").path(folder);
			}
		}

		String uuid = UUID.randomUUID().toString();

		String tmp = uriComponentsBuilder.path("/").path(uuid).build().toString();

		String[] command = new String[] { gitexe, "mix-export", "single", "-url=" + url, "-folder=" + tmp,
				"-username=" + username, "-token=" + token, "--track=true" };

		CommandVo commandVo = ProcessBuilderUtils.command(command, "UTF-8");
		commandVo.setTmp(tmp);

		return commandVo;
	}

	/**
	 * 添加远端
	 * @param remoteName
	 * @param url
	 * @param username
	 * @param token
	 * @param folder
	 */
	public static void addRemote(String remoteName, String url, String username, String token, String folder) {

		if (StringUtils.hasText(username) && StringUtils.hasText(token)) {
			url = UriComponentsBuilder.fromHttpUrl(url).userInfo(username + ":" + token).build().toString();
		}

		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String[] command = new String[] { "git", "-C", folder, "remote", "add", remoteName, url };
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

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			String string = sb.toString();

			log.debug("添加远端日志：{}", string);

			int exitCode = process.waitFor();
			log.info("Process exited with code: {}", exitCode);

			if (exitCode == 0) {
				log.info("添加远端成功");
			}
			else {
				throw new GitbotRuntimeException(String.format("添加远端异常代码：%s，异常日志：%s", exitCode, string));
			}
		}
		catch (IOException | InterruptedException e) {
			throw new GitbotRuntimeException("添加远端异常", e);
		}

	}

	/**
	 * 推送远端
	 * @param folder
	 * @param remoteName
	 */
	public static void gitPushAll(String folder, String remoteName) {

		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String[] command = new String[] { "git", "-C", folder, "push", remoteName, "--all" };
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

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			String string = sb.toString();

			log.debug("推送远端日志：{}", string);

			int exitCode = process.waitFor();
			log.info("Process exited with code: {}", exitCode);

			if (exitCode == 0) {
				log.info("推送远端成功");
			}
			else {
				throw new GitbotRuntimeException(String.format("推送远端异常代码：%s，异常日志：%s", exitCode, string));
			}
		}
		catch (IOException | InterruptedException e) {
			throw new GitbotRuntimeException("推送远端异常", e);
		}

	}

	/**
	 * 推送远端
	 * @param folder
	 * @param remoteName
	 */
	public static void gitPushTags(String folder, String remoteName) {

		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String[] command = new String[] { "git", "-C", folder, "push", remoteName, "--tags" };
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

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			String string = sb.toString();

			log.debug("推送远端日志：{}", string);

			int exitCode = process.waitFor();
			log.info("Process exited with code: {}", exitCode);

			if (exitCode == 0) {
				log.info("推送远端成功");
			}
			else {
				throw new GitbotRuntimeException(String.format("推送远端异常代码：%s，异常日志：%s", exitCode, string));
			}
		}
		catch (IOException | InterruptedException e) {
			throw new GitbotRuntimeException("推送远端异常", e);
		}

	}

	/**
	 * 迁移
	 * @param gitexe
	 * @param sourceUrl
	 * @param sourceUsername
	 * @param sourceToken
	 * @param sourceBranch
	 * @param folder
	 * @param targetUrl
	 * @param targetUsername
	 * @param targetToken
	 * @param reserve
	 */
	public static void transfer(String gitexe, String sourceUrl, String sourceUsername, String sourceToken,
			String sourceBranch, String folder, String targetUrl, String targetUsername, String targetToken,
			boolean reserve) throws IOException, InterruptedException {

		CommandVo commandVo = gitClone(gitexe, sourceUrl, sourceUsername, sourceToken, sourceBranch, folder);

		String tmp = commandVo.getTmp();

		String remoteName = UUID.randomUUID().toString().substring(0, 4);

		addRemote(remoteName, targetUrl, targetUsername, targetToken, tmp);

		gitPushAll(tmp, remoteName);
		gitPushTags(tmp, remoteName);

		if (!reserve) {
			deleteFolder(new File(tmp));
		}

	}

	/**
	 * 删除文件夹
	 * @param folder
	 */
	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					// 递归删除子文件夹
					deleteFolder(file);
				}
				else {
					// 删除文件
					boolean delete = file.delete();
				}
			}
		}
		// 删除空文件夹
		boolean delete = folder.delete();
	}

}
