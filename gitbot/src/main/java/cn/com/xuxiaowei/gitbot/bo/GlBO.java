package cn.com.xuxiaowei.gitbot.bo;

import lombok.Data;

/**
 * GitLab 参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class GlBO {

	private String hostUrl;

	private String personalAccessToken;

}
