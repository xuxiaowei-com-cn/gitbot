package cn.com.xuxiaowei.gitbot.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.kohsuke.github.GHIssueState;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class SaveMyOrganizationRepositoryBO {

	/**
	 * 授权凭证，权限范围：read:org
	 */
	@NotEmpty(message = "授权凭证不能为空")
	private String oauthToken;

	/**
	 * 保存分支
	 */
	private boolean saveBranch;

	/**
	 * 是否保存 PR
	 */
	private boolean savePullRequest;

	/**
	 * PR 状态
	 */
	private GHIssueState issueState;

}
