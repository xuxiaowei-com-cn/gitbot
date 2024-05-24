package cn.com.xuxiaowei.gitbot.constant;

import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * OAuth2 常量
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class OAuth2Constants {

	/**
	 * 放入 JWT 中的自定义字段，用户储存用户权限
	 * <p>
	 * 根据 {@link JwtGrantedAuthoritiesConverter} 类中的 getAuthoritiesClaimName 方法 和
	 * WELL_KNOWN_AUTHORITIES_CLAIM_NAMES 属性可知，默认配置从 scope、scp 中获取
	 * @see JwtGrantedAuthoritiesConverter
	 */
	public static final String AUTHORITIES_CLAIM_NAME = "scope";

	/**
	 * 权限前缀
	 * <p>
	 * 根据 {@link JwtGrantedAuthoritiesConverter} 类中的 getAuthoritiesClaimName 方法 和
	 * DEFAULT_AUTHORITY_PREFIX 属性可知，默认配置前缀是 SCOPE_
	 * @see JwtGrantedAuthoritiesConverter
	 */
	public static final String AUTHORITY_PREFIX = "";

}
