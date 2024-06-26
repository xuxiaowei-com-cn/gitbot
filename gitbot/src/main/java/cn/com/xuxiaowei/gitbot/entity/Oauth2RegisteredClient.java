package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Data
@TableName("oauth2_registered_client")
public class Oauth2RegisteredClient implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String clientId;

	private LocalDateTime clientIdIssuedAt;

	private String clientSecret;

	private LocalDateTime clientSecretExpiresAt;

	private String clientName;

	private String clientAuthenticationMethods;

	private String authorizationGrantTypes;

	private String redirectUris;

	private String postLogoutRedirectUris;

	private String scopes;

	private String clientSettings;

	private String tokenSettings;

}
