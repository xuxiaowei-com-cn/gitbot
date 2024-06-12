package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Data
@TableName("oauth2_authorization_consent")
public class Oauth2AuthorizationConsent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String registeredClientId;

	private String principalName;

	private String authorities;

}
