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
@TableName("oauth2_authorization")
public class Oauth2Authorization implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String registeredClientId;

	private String principalName;

	private String authorizationGrantType;

	private String authorizedScopes;

	private byte[] attributes;

	private String state;

	private byte[] authorizationCodeValue;

	private LocalDateTime authorizationCodeIssuedAt;

	private LocalDateTime authorizationCodeExpiresAt;

	private byte[] authorizationCodeMetadata;

	private byte[] accessTokenValue;

	private LocalDateTime accessTokenIssuedAt;

	private LocalDateTime accessTokenExpiresAt;

	private byte[] accessTokenMetadata;

	private String accessTokenType;

	private String accessTokenScopes;

	private byte[] oidcIdTokenValue;

	private LocalDateTime oidcIdTokenIssuedAt;

	private LocalDateTime oidcIdTokenExpiresAt;

	private byte[] oidcIdTokenMetadata;

	private byte[] refreshTokenValue;

	private LocalDateTime refreshTokenIssuedAt;

	private LocalDateTime refreshTokenExpiresAt;

	private byte[] refreshTokenMetadata;

	private byte[] userCodeValue;

	private LocalDateTime userCodeIssuedAt;

	private LocalDateTime userCodeExpiresAt;

	private byte[] userCodeMetadata;

	private byte[] deviceCodeValue;

	private LocalDateTime deviceCodeIssuedAt;

	private LocalDateTime deviceCodeExpiresAt;

	private byte[] deviceCodeMetadata;

}
