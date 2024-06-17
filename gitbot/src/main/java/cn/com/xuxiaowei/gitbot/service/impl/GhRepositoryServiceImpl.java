package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GhBranch;
import cn.com.xuxiaowei.gitbot.entity.GhRepository;
import cn.com.xuxiaowei.gitbot.mapper.GhRepositoryMapper;
import cn.com.xuxiaowei.gitbot.service.IGhBranchService;
import cn.com.xuxiaowei.gitbot.service.IGhOrganizationService;
import cn.com.xuxiaowei.gitbot.service.IGhPullRequestService;
import cn.com.xuxiaowei.gitbot.service.IGhRepositoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-16
 */
@Slf4j
@Service
public class GhRepositoryServiceImpl extends ServiceImpl<GhRepositoryMapper, GhRepository>
		implements IGhRepositoryService {

	private IGhOrganizationService ghOrganizationService;

	private IGhBranchService ghBranchService;

	private IGhPullRequestService ghPullRequestService;

	@Autowired
	public void setGhOrganizationService(IGhOrganizationService ghOrganizationService) {
		this.ghOrganizationService = ghOrganizationService;
	}

	@Autowired
	public void setGhBranchService(IGhBranchService ghBranchService) {
		this.ghBranchService = ghBranchService;
	}

	@Autowired
	public void setGhPullRequestService(IGhPullRequestService ghPullRequestService) {
		this.ghPullRequestService = ghPullRequestService;
	}

	/**
	 * 需要授权：read:org
	 */
	@Override
	public void saveMyOrganizationRepository(String oauthToken, boolean saveBranch, boolean savePullRequest,
			GHIssueState issueState) throws IOException {

		int saved = 0;
		int updated = 0;

		try {
			List<GHOrganization> ghOrganizations = ghOrganizationService.saveMyOrganizations(oauthToken);
			for (GHOrganization ghOrganization : ghOrganizations) {

				PagedIterable<GHRepository> ghRepositories = ghOrganization.listRepositories();
				for (GHRepository repository : ghRepositories) {

					GhRepository ghRepository = copyProperty(repository);

					QueryWrapper<GhRepository> queryWrapper = new QueryWrapper<GhRepository>()
						//
						.eq("id", ghRepository.getId());
					long count = count(queryWrapper);
					if (count == 0) {
						save(ghRepository);
						saved++;
					}
					else {
						update(ghRepository, queryWrapper);
						updated++;
					}

					if (saveBranch) {
						Map<String, GHBranch> branches = repository.getBranches();

						for (GHBranch branch : branches.values()) {

							GhBranch ghBranch = new GhBranch();
							ghBranch.setId(repository.getId());
							ghBranch.setName(branch.getName());
							ghBranch.setSha(branch.getSHA1());
							ghBranch.setProtection(branch.isProtected());
							// @formatter:off
							ghBranch.setProtectionUrl(branch.getProtectionUrl() == null ? null : branch.getProtectionUrl().toString());
							// @formatter:on

							ghBranchService.saveOrUpdate(ghBranch);
						}
					}

					if (savePullRequest) {
						ghPullRequestService.savePullRequest(oauthToken, repository.getId(), issueState);
					}
				}
			}
		}
		finally {
			log.debug("saved: {}", saved);
			log.debug("updated: {}", updated);
		}
	}

	/**
	 * 保存自己组织的仓库
	 */
	@Override
	public void saveMyselfOrganizationRepository(String oauthToken, boolean saveBranch, boolean savePullRequest,
			GHIssueState issueState) throws IOException {

		int saved = 0;
		int updated = 0;

		try {

			GitHubBuilder gitHubBuilder = new GitHubBuilder();
			gitHubBuilder.withOAuthToken(oauthToken);
			GitHub github = gitHubBuilder.build();
			GHMyself myself = github.getMyself();

			GHPersonSet<GHOrganization> allOrganizations = myself.getAllOrganizations();

			for (GHOrganization organization : allOrganizations) {

				Map<String, GHRepository> allRepositories = organization.getRepositories();

				for (GHRepository repository : allRepositories.values()) {

					GhRepository ghRepository = copyProperty(repository);

					QueryWrapper<GhRepository> queryWrapper = new QueryWrapper<GhRepository>()
						//
						.eq("id", ghRepository.getId());
					long count = count(queryWrapper);
					if (count == 0) {
						save(ghRepository);
						saved++;
					}
					else {
						update(ghRepository, queryWrapper);
						updated++;
					}

					if (saveBranch) {
						Map<String, GHBranch> branches = repository.getBranches();

						for (GHBranch branch : branches.values()) {

							GhBranch ghBranch = new GhBranch();
							ghBranch.setId(repository.getId());
							ghBranch.setName(branch.getName());
							ghBranch.setSha(branch.getSHA1());
							ghBranch.setProtection(branch.isProtected());
							// @formatter:off
							ghBranch.setProtectionUrl(branch.getProtectionUrl() == null ? null : branch.getProtectionUrl().toString());
							// @formatter:on

							ghBranchService.saveOrUpdate(ghBranch);
						}
					}

					if (savePullRequest) {
						ghPullRequestService.savePullRequest(oauthToken, repository.getId(), issueState);
					}
				}
			}
		}
		finally {
			log.debug("saved: {}", saved);
			log.debug("updated: {}", updated);
		}
	}

	/**
	 * 1. 不提供任何授权，仅可获取公开仓库
	 * <p>
	 * 2. 提供 repo 或 public_repo 权限，可获取所有仓库
	 */
	@Override
	public void saveMyselfRepository(String oauthToken, boolean saveBranch, boolean savePullRequest,
			GHIssueState issueState) throws IOException {

		int saved = 0;
		int updated = 0;

		try {

			GitHubBuilder gitHubBuilder = new GitHubBuilder();
			gitHubBuilder.withOAuthToken(oauthToken);
			GitHub github = gitHubBuilder.build();
			GHMyself myself = github.getMyself();

			Map<String, GHRepository> allRepositories = myself.getAllRepositories();

			for (GHRepository repository : allRepositories.values()) {

				GhRepository ghRepository = copyProperty(repository);

				QueryWrapper<GhRepository> queryWrapper = new QueryWrapper<GhRepository>()
					//
					.eq("id", ghRepository.getId());
				long count = count(queryWrapper);
				if (count == 0) {
					save(ghRepository);
					saved++;
				}
				else {
					update(ghRepository, queryWrapper);
					updated++;
				}

				if (saveBranch) {
					Map<String, GHBranch> branches = repository.getBranches();

					for (GHBranch branch : branches.values()) {

						GhBranch ghBranch = new GhBranch();
						ghBranch.setId(repository.getId());
						ghBranch.setName(branch.getName());
						ghBranch.setSha(branch.getSHA1());
						ghBranch.setProtection(branch.isProtected());
						// @formatter:off
						ghBranch.setProtectionUrl(branch.getProtectionUrl() == null ? null : branch.getProtectionUrl().toString());
						// @formatter:on

						ghBranchService.saveOrUpdate(ghBranch);
					}
				}

				if (savePullRequest) {
					ghPullRequestService.savePullRequest(oauthToken, repository.getId(), issueState);
				}
			}
		}
		finally {
			log.debug("saved: {}", saved);
			log.debug("updated: {}", updated);
		}
	}

	private GhRepository copyProperty(GHRepository repository) throws IOException {
		// @formatter:off
		GhRepository ghRepository = new GhRepository();
		ghRepository.setId(repository.getId());
		ghRepository.setNodeId(repository.getNodeId());
		ghRepository.setDescription(repository.getDescription());
		ghRepository.setName(repository.getName());
		ghRepository.setFullName(repository.getFullName());
		ghRepository.setHtmlUrl(repository.getHtmlUrl().toString());
		ghRepository.setLicense(repository.getLicense() == null ? null : repository.getLicense().toString());
		ghRepository.setGitUrl(repository.getGitTransportUrl());
		ghRepository.setSshUrl(repository.getSshUrl());
		ghRepository.setSvnUrl(repository.getSvnUrl());
		ghRepository.setMirrorUrl(repository.getMirrorUrl());
		ghRepository.setHasIssues(repository.hasIssues());
		ghRepository.setHasWiki(repository.hasWiki());
		ghRepository.setFork(repository.isFork());
		ghRepository.setHasDownloads(repository.hasDownloads());
		ghRepository.setHasPages(repository.hasPages());
		ghRepository.setArchived(repository.isArchived());
		ghRepository.setDisabled(repository.isDisabled());
		ghRepository.setHasProjects(repository.hasProjects());
		ghRepository.setAllowSquashMerge(repository.isAllowSquashMerge());
		ghRepository.setAllowMergeCommit(repository.isAllowMergeCommit());
		ghRepository.setAllowRebaseMerge(repository.isAllowRebaseMerge());
		ghRepository.setAllowForking(repository.isAllowForking());
		ghRepository.setDeleteBranchOnMerge(repository.isDeleteBranchOnMerge());
		ghRepository.setIsPrivate(repository.isPrivate());
		ghRepository.setVisibility(repository.getVisibility().toString());
		ghRepository.setForksCount(repository.getForksCount());
		ghRepository.setStargazersCount(repository.getStargazersCount());
		ghRepository.setWatchersCount(repository.getWatchersCount());
		ghRepository.setSize(repository.getSize());
		ghRepository.setOpenIssuesCount(repository.getOpenIssueCount());
		ghRepository.setSubscribersCount(repository.getSubscribersCount());
		ghRepository.setPushedAt(repository.getPushedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		ghRepository.setDefaultBranch(repository.getDefaultBranch());
		ghRepository.setLanguage(repository.getLanguage());
		ghRepository.setTemplateRepositoryId(repository.getTemplateRepository() == null ? null : repository.getTemplateRepository().getId());
		ghRepository.setSourceId(repository.getSource() == null ? null : repository.getSource().getId());
		ghRepository.setParentId(repository.getParent() == null ? null : repository.getParent().getId());
		ghRepository.setIsTemplate(repository.isTemplate());
		ghRepository.setUrl(repository.getUrl().toString());
		ghRepository.setCreatedAt(repository.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		ghRepository.setUpdatedAt(repository.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		// @formatter:on

		return ghRepository;
	}

}
