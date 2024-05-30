package cn.com.xuxiaowei.gitbot.properties;

import cn.com.xuxiaowei.gitbot.utils.RSAUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Git 机器人 安全配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@ConfigurationProperties("gitbot")
public class GitbotProperties {

	private String title;

	private String gitexe;

	/**
	 * RSA 公钥
	 */
	private String publicKey;

	/**
	 * RSA 私钥
	 */
	private String privateKey;

	/**
	 * GitLab 配置
	 */
	private GitLabProperties gitlab = new GitLabProperties();

	public PublicKey publicKey() {
		return RSAUtils.publicKey(this.publicKey);
	}

	public RSAPublicKey rsaPublicKey() {
		return (RSAPublicKey) publicKey();
	}

	public PrivateKey privateKey() {
		return RSAUtils.privateKey(this.privateKey);
	}

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
