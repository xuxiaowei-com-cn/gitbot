package cn.com.xuxiaowei.gitbot.utils;

import cn.com.xuxiaowei.gitbot.entity.GlProject;
import cn.com.xuxiaowei.gitbot.service.IGlProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * Git 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class GitUtilsTests {

	@Autowired
	private IGlProjectService glProjectService;

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

		GitUtils.gitClone("gitlab-go", url, username, token, branch, folder);
	}

	// @Test
	void transfer() throws IOException, InterruptedException {

		List<GlProject> list = glProjectService.list();
		int size = list.size();

		for (int i = 0; i < size; i++) {
			GlProject project = list.get(i);

			String sourceUrl = project.getHttpUrlToRepo();

			log.info("{}: {}: {}", size, i, sourceUrl);

			if (i < 137) {
				continue;
			}

			if (sourceUrl.contains("xuxiaowei-com-cn/nexus")) {
				continue;
			}

			if (sourceUrl.contains("https://jihulab.com/xuxiaowei-jihu/custom-roles-test.git")) {
				continue;
			}

			String sourceUsername = "xuxiaowei-com-cn";
			String sourceToken = "";
			String sourceBranch = "";
			String folder = "";
			String targetUrl = sourceUrl
				.replace("https://jihulab.com/xuxiaowei-jihu", "https://gitlab.xuxiaowei.com.cn")
				.replace("https://jihulab.com", "https://gitlab.xuxiaowei.com.cn");
			String targetUsername = "xuxiaowei-com-cn";
			String targetToken = "";
			boolean reserve = false;
			GitUtils.transfer("gitlab-go", sourceUrl, sourceUsername, sourceToken, sourceBranch, folder, targetUrl,
					targetUsername, targetToken, reserve);
		}

	}

}
