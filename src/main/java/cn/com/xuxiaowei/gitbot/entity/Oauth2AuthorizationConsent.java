package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Getter
@Setter
@TableName("oauth2_authorization_consent")
public class Oauth2AuthorizationConsent implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId("registered_client_id")
	private String registeredClientId;

	@TableId("principal_name")
	private String principalName;

	private String authorities;

}
