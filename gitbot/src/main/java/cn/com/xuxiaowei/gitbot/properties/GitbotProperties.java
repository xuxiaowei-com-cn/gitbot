package cn.com.xuxiaowei.gitbot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微服务 安全配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@ConfigurationProperties("gitbot")
public class GitbotProperties {

	/**
	 * GitLab 配置
	 */
	private GitLabProperties gitlab = new GitLabProperties();

	/**
	 * @author xuxiaowei
	 * @since 0.0.1
	 */
	@Data
	public static class GitLabProperties {

		/**
		 * 是否保存 Runner Token
		 */
		private boolean saveRunnersToken;

	}

}
