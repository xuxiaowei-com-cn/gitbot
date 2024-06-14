package cn.com.xuxiaowei.gitbot.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class SaveOrganizationBO {

	/**
	 * 授权凭证，权限范围：read:org
	 */
	@NotEmpty(message = "授权凭证不能为空")
	private String oauthToken;

}
