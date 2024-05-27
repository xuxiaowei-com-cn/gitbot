package cn.com.xuxiaowei.gitbot.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
	@Test
	void gitClone() {
		String url = "https://gitee.com/xuxiaowei-cloud/spring-cloud-xuxiaowei.git";
		String username = "";
		String token = "";
		String branch = "";
		String folder = "spring-cloud-xuxiaowei-2";

		GitUtils.gitClone(url, username, token, branch, folder);
	}

}
