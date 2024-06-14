package cn.com.xuxiaowei.gitbot.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class SaveNamespaceBO {

	/**
	 * 实例地址
	 */
	@NotEmpty(message = "实例地址不能为空")
	@URL(message = "实例地址必须是有效的URL")
	private String hostUrl;

	/**
	 * 忽略证书错误
	 */
	private boolean ignoreCertificateErrors;

	/**
	 * 个人授权凭证
	 */
	@NotEmpty(message = "个人授权凭证不能为空")
	private String personalAccessToken;

}
