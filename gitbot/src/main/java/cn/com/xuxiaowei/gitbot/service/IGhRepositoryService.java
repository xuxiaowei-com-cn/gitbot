package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.GhRepository;
import com.baomidou.mybatisplus.extension.service.IService;
import org.kohsuke.github.GHIssueState;

import java.io.IOException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-16
 */
public interface IGhRepositoryService extends IService<GhRepository> {

	/**
	 * 需要授权：read:org
	 */
	void saveMyOrganizationRepository(String oauthToken, boolean saveBranch, boolean savePullRequest,
			GHIssueState issueState) throws IOException;

	/**
	 * 保存自己组织的仓库
	 */
	void saveMyselfOrganizationRepository(String oauthToken, boolean saveBranch, boolean savePullRequest,
			GHIssueState issueState) throws IOException;

	/**
	 * 1. 不提供任何授权，仅可获取公开仓库
	 * <p>
	 * 2. 提供 repo 或 public_repo 权限，可获取所有仓库
	 */
	void saveMyselfRepository(String oauthToken, boolean saveBranch, boolean savePullRequest, GHIssueState issueState)
			throws IOException;

}
