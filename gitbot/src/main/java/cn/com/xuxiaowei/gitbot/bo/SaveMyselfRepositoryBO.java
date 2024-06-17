package cn.com.xuxiaowei.gitbot.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.kohsuke.github.GHIssueState;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class SaveMyselfRepositoryBO {

	/**
	 * 授权凭证
	 * <p>
	 * 1. 不提供任何授权，仅可获取公开仓库
	 * <p>
	 * 2. 提供 repo 或 public_repo 权限，可获取所有仓库
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
