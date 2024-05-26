package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.bo.GlBO;
import cn.com.xuxiaowei.gitbot.entity.GlProject;
import cn.com.xuxiaowei.gitbot.mapper.GlProjectMapper;
import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import cn.com.xuxiaowei.gitbot.service.IGlProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gitlab4j.api.Constants.ProjectOrderBy;
import org.gitlab4j.api.Constants.SortOrder;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.AccessLevel;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.ProjectFilter;
import org.gitlab4j.api.models.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Service
public class GlProjectServiceImpl extends ServiceImpl<GlProjectMapper, GlProject> implements IGlProjectService {

	private GitbotProperties gitbotProperties;

	@Autowired
	public void setGitbotProperties(GitbotProperties gitbotProperties) {
		this.gitbotProperties = gitbotProperties;
	}

	@Override
	public boolean saveOrUpdate(GlProject entity) {
		QueryWrapper<GlProject> queryWrapper = new QueryWrapper<GlProject>()
			//
			.eq("`host`", entity.getHost())
			//
			.eq("id", entity.getId());
		long count = count(queryWrapper);
		return count == 0 ? save(entity) : update(entity, queryWrapper);
	}

	@Override
	public GlProject getByPrimaryKey(Long id, String host) {
		QueryWrapper<GlProject> queryWrapper = new QueryWrapper<GlProject>()
			//
			.eq("`host`", host)
			//
			.eq("id", id);
		return getOne(queryWrapper);
	}

	@Override
	public boolean updateBatch(List<GlProject> glNamespaceUpdateList) {
		for (GlProject glNamespace : glNamespaceUpdateList) {
			UpdateWrapper<GlProject> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("`host`", glNamespace.getHost());
			updateWrapper.eq("id", glNamespace.getId());
			update(glNamespace, updateWrapper);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Project> listProjects(GlBO glBO) throws GitLabApiException {
		List<Project> projects;
		try (GitLabApi gitLabApi = new GitLabApi(glBO.getHostUrl(), glBO.getPersonalAccessToken())) {
			gitLabApi.setIgnoreCertificateErrors(true);
			projects = gitLabApi.getProjectApi().getOwnedProjects();
		}
		catch (GitLabApiException e) {
			log.error("列出项目异常：", e);
			throw e;
		}

		URL url;
		String hostUrl = glBO.getHostUrl();
		try {
			url = new URL(hostUrl);
		}
		catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		String host = url.getHost();

		List<GlProject> glNamespaceSaveList = new ArrayList<>();
		List<GlProject> glNamespaceUpdateList = new ArrayList<>();
		for (Project project : projects) {

			Long id = project.getId();

			// @formatter:off
			GlProject glProject = new GlProject();
			glProject.setId(project.getId());
			glProject.setHost(host);
			glProject.setApprovalsBeforeMerge(project.getApprovalsBeforeMerge());
			glProject.setArchived(project.getArchived());
			glProject.setAvatarUrl(project.getAvatarUrl());
			glProject.setContainerRegistryEnabled(project.getContainerRegistryEnabled());
			glProject.setCreatedAt(project.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			glProject.setCreatorId(project.getCreatorId());
			glProject.setDefaultBranch(project.getDefaultBranch());
			glProject.setDescription(project.getDescription());
			glProject.setForksCount(project.getForksCount());
			glProject.setForkedFromProjectId(project.getForkedFromProject() == null ? null : project.getForkedFromProject().getId());
			glProject.setHttpUrlToRepo(project.getHttpUrlToRepo());
			glProject.setIsPublic(project.getPublic());
			glProject.setIssuesEnabled(project.getIssuesEnabled());
			glProject.setJobsEnabled(project.getJobsEnabled());
			glProject.setLastActivityAt(project.getLastActivityAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			glProject.setLfsEnabled(project.getLfsEnabled());
			glProject.setMergeMethod(project.getMergeMethod().toString());
			glProject.setMergeRequestsEnabled(project.getMergeRequestsEnabled());
			glProject.setName(project.getName());
			glProject.setNamespaceId(project.getNamespace().getId());
			glProject.setNameWithNamespace(project.getNameWithNamespace());
			glProject.setOnlyAllowMergeIfPipelineSucceeds(project.getOnlyAllowMergeIfPipelineSucceeds());
			glProject.setOnlyAllowMergeIfAllDiscussionsAreResolved(project.getOnlyAllowMergeIfAllDiscussionsAreResolved());
			glProject.setOpenIssuesCount(project.getOpenIssuesCount());
			glProject.setOwnerId(project.getOwner() == null ? null : project.getOwner().getId());
			glProject.setPath(project.getPath());
			glProject.setPathWithNamespace(project.getPathWithNamespace());
			glProject.setPermissions(project.getPermissions().toString());
			glProject.setPublicJobs(project.getPublicJobs());
			glProject.setRepositoryStorage(project.getRepositoryStorage());
			glProject.setRequestAccessEnabled(project.getRequestAccessEnabled());
			glProject.setRunnersToken(gitbotProperties.getGitlab().isSaveRunnersToken() ? project.getRunnersToken() : null);
			glProject.setSharedRunnersEnabled(project.getSharedRunnersEnabled());
			glProject.setSharedWithGroups(project.getNameWithNamespace());
			glProject.setSnippetsEnabled(project.getSnippetsEnabled());
			glProject.setSshUrlToRepo(project.getSshUrlToRepo());
			glProject.setStarCount(project.getStarCount());
			// glProject.setTagList(project.getTagList());
			// glProject.setTopics(project.getTopics());
			glProject.setVisibilityLevel(project.getVisibilityLevel());
			glProject.setVisibility(project.getVisibility().toString());
			glProject.setWallEnabled(project.getWallEnabled());
			glProject.setWebUrl(project.getWebUrl());
			glProject.setWikiEnabled(project.getWikiEnabled());
			glProject.setPrintingMergeRequestLinkEnabled(project.getPrintingMergeRequestLinkEnabled());
			glProject.setResolveOutdatedDiffDiscussions(project.getResolveOutdatedDiffDiscussions());
			// glProject.setStatistics(project.getStatistics());
			glProject.setInitializeWithReadme(project.getInitializeWithReadme());
			glProject.setPackagesEnabled(project.getPackagesEnabled());
			glProject.setEmptyRepo(project.getEmptyRepo());
			glProject.setLicenseUrl(project.getLicenseUrl());
			// glProject.setLicense(project.getLicense());
			// glProject.setCustomAttributes(project.getCustomAttributes());
			glProject.setBuildCoverageRegex(project.getBuildCoverageRegex());
			glProject.setBuildGitStrategy(project.getBuildGitStrategy() == null ? null : project.getBuildGitStrategy().toString());
			glProject.setReadmeUrl(project.getReadmeUrl());
			glProject.setCanCreateMergeRequestIn(project.getCanCreateMergeRequestIn());
			glProject.setImportStatus(project.getImportStatus().toString());
			glProject.setCiDefaultGitDepth(project.getCiDefaultGitDepth());
			glProject.setCiForwardDeploymentEnabled(project.getCiForwardDeploymentEnabled());
			glProject.setCiConfigPath(project.getCiConfigPath());
			glProject.setRemoveSourceBranchAfterMerge(project.getRemoveSourceBranchAfterMerge());
			glProject.setAutoDevopsEnabled(project.getAutoDevopsEnabled());
			glProject.setAutoDevopsDeployStrategy(project.getAutoDevopsDeployStrategy() == null ? null : project.getAutoDevopsDeployStrategy().toString());
			glProject.setAutocloseReferencedIssues(project.getAutocloseReferencedIssues());
			glProject.setEmailsDisabled(project.getEmailsDisabled());
			glProject.setSuggestionCommitMessage(project.getSuggestionCommitMessage());
			glProject.setSquashOption(project.getSquashOption().toString());
			glProject.setMarkedForDeletionOn(project.getMarkedForDeletionOn() == null ? null : project.getMarkedForDeletionOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			// @formatter:on

			GlProject byPrimaryKey = getByPrimaryKey(id, host);
			if (byPrimaryKey == null) {
				glNamespaceSaveList.add(glProject);
			}
			else {
				glNamespaceUpdateList.add(glProject);
			}
		}

		saveBatch(glNamespaceSaveList);
		updateBatch(glNamespaceUpdateList);

		return projects;
	}

}
