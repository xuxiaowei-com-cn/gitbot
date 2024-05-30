package cn.com.xuxiaowei.gitbot.utils;

import cn.com.xuxiaowei.gitbot.vo.CommandVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Git 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class GitUtilsTests {

	/**
	 * 克隆
	 */
	// @Test
	void gitClone() throws IOException, InterruptedException {
		String url = "https://gitee.com/xuxiaowei-cloud/spring-cloud-xuxiaowei.git";
		String username = "";
		String token = "";
		String branch = "";
		String folder = "spring-cloud-xuxiaowei-2";

		CommandVo commandVo = GitUtils.gitClone("gitlab-go", url, username, token, branch, folder);
		log.info(String.valueOf(commandVo));
	}

	/**
	 * 迁移
	 */
	// @Test
	void transfer() throws IOException, InterruptedException {

		String sourceUrl = "https://jihulab.com/xuxiaowei-jihu/xuxiaowei-com-cn/gitbot.git";
		String sourceUsername = "";
		String sourceToken = "";
		String sourceBranch = "";
		String folder = "";
		String targetUrl = "https://gitlab.xuxiaowei.com.cn/xuxiaowei-com-cn/gitbot.git";
		String targetUsername = "";
		String targetToken = "";
		String targetBranch = "";
		boolean reserve = false;

		GitUtils.transfer("gitexe", sourceUrl, sourceUsername, sourceToken, sourceBranch, folder, targetUrl,
				targetUsername, targetToken, targetBranch, reserve);

	}

}
