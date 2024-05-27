package cn.com.xuxiaowei.gitbot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
	 * @param url
	 * @param username
	 * @param token
	 * @param branch
	 * @param folder
	 */
	public static String gitClone(String url, String username, String token, String branch, String folder) {

		if (StringUtils.hasText(username) && StringUtils.hasText(token)) {
			url = UriComponentsBuilder.fromHttpUrl(url).userInfo(username + ":" + token).build().toString();
		}

		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String tempDir = System.getProperty("java.io.tmpdir");
		log.debug("用户临时文件夹：{}", tempDir);

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().path(tempDir).path("gitbot-tmp");

		if (folder != null) {
			if (folder.contains("..")) {
				throw new GitbotRuntimeException("文件夹不合法");
			}
			else {
				uriComponentsBuilder.path(folder);
			}
		}

		String uuid = UUID.randomUUID().toString();

		String tmp = uriComponentsBuilder.path("/").path(uuid).build().toString();

		List<String> commandList = new ArrayList<>();
		commandList.add("git");
		commandList.add("clone");
		commandList.add("-v");
		if (StringUtils.hasText(branch)) {
			commandList.add("-b");
			commandList.add(branch);
		}
		commandList.add(url);
		commandList.add(tmp);

		String[] command = commandList.toArray(new String[0]);

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

			log.debug("克隆日志：{}", string);

			int exitCode = process.waitFor();
			log.info("Process exited with code: {}", exitCode);

			if (exitCode == 0) {
				log.info("克隆成功");
			}
			else if (exitCode == 128) {
				throw new GitbotRuntimeException(String.format("仓库、分支不存在或凭证不正确，克隆异常代码：%s，异常日志：%s", exitCode, string));
			}
			else {
				throw new GitbotRuntimeException(String.format("克隆异常代码：%s，异常日志：%s", exitCode, string));
			}
		}
		catch (IOException | InterruptedException e) {
			throw new GitbotRuntimeException("克隆代码异常", e);
		}

		return tmp;
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

		String[] command;
		String charsetName;
		if (isWindows) {
			charsetName = "GBK";
			command = new String[] { "cmd", "/c", "cd /d", folder, "&&", "git", "remote", "add", remoteName, url };
		}
		else {
			command = new String[] { "sh", "/c", "cd /d", folder, "&&", "git", "remote", "add", remoteName, url };
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
	public static void gitPush(String folder, String remoteName) {

		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows = os.contains("windows");

		String[] command;
		String charsetName;
		if (isWindows) {
			charsetName = "GBK";
			command = new String[] { "cmd", "/c", "cd /d", folder, "&&", "git", "push", remoteName };
		}
		else {
			command = new String[] { "sh", "/c", "cd /d", folder, "&&", "git", "add", remoteName };
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
	 * @param sourceUrl
	 * @param sourceUsername
	 * @param sourceToken
	 * @param sourceBranch
	 * @param folder
	 * @param targetUrl
	 * @param targetUsername
	 * @param targetToken
	 * @param targetBranch
	 * @param reserve
	 */
	public static void transfer(String sourceUrl, String sourceUsername, String sourceToken, String sourceBranch,
			String folder, String targetUrl, String targetUsername, String targetToken, String targetBranch,
			boolean reserve) {

		String tmp = gitClone(sourceUrl, sourceUsername, sourceToken, sourceBranch, folder);

		String remoteName = UUID.randomUUID().toString().substring(0, 4);

		addRemote(remoteName, targetUrl, targetUsername, targetToken, tmp);

		gitPush(tmp, remoteName);

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
