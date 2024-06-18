package cn.com.xuxiaowei.gitbot.properties;

import cn.com.xuxiaowei.gitbot.utils.RSAUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

	private String cmd;

	/**
	 * RSA 公钥
	 */
	private String publicKey;

	/**
	 * RSA 私钥
	 */
	private String privateKey;

	/**
	 * Token URL
	 */
	private String tokenUrl;

	/**
	 * 请求授权客户ID
	 */
	private String authorizeClientId;

	/**
	 * 请求授权客户秘钥
	 */
	private String authorizeClientSecret;

	/**
	 * 请求授权成功后重定向地址
	 */
	private String authorizeRedirectUri;

	/**
	 * 请求授权范围，多个值使用空格隔开，请勿将空格转译
	 */
	private String authorizeScope;

	/**
	 * 请求授权成功后的 URL
	 */
	private String authorizeSuccessUrl;

	/**
	 * Token 票据 Redis 前缀
	 */
	private String ticketPrefix;

	/**
	 * 允许跨域的来源
	 */
	private List<String> allowedOrigins = new ArrayList<>();

	/**
	 * 允许跨域的方法
	 */
	private List<String> allowedMethods;

	/**
	 * 允许跨域的请求头
	 */
	private List<String> allowedHeaders;

	/**
	 * 允许跨域时是否携带凭证
	 */
	private Boolean allowCredentials;

	/**
	 * 配置客户端可以缓存来自预检请求的响应多长时间（以秒为单位）。 默认情况下，未设置此项。
	 */
	private Long maxAge;

	/**
	 * 允许跨域路径
	 */
	private String pattern;

	/**
	 * 允许 IP 访问的 Redis key
	 */
	private String allowIpRedisKey = "allow:ip";

	/**
	 * 保存命名空间的 Redis Key 前缀
	 */
	private String saveNamespaceRedisKeyPrefix = "save:namespace:";

	/**
	 * 保存项目的 Redis Key 前缀
	 */
	private String saveProjectRedisKeyPrefix = "save:project:";

	/**
	 * 保存分支的 Redis Key 前缀
	 */
	private String saveBranchRedisKeyPrefix = "save:branch:";

	/**
	 * 数据储存时间
	 */
	private Long dataTimeout = 1L;

	/**
	 * 数据储存时间单位
	 */
	private TimeUnit dataUnit = TimeUnit.DAYS;

	/**
	 * GitLab 配置
	 */
	private GitLabProperties gitlab = new GitLabProperties();

	/**
	 * 定时器配置
	 */
	private Scheduled scheduled = new Scheduled();

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

	/**
	 * @author xuxiaowei
	 * @since 0.0.1
	 */
	@Data
	public static class Scheduled {

		/**
		 * 是否开启 GitLab
		 */
		private boolean enabledGitlab;

	}

}
