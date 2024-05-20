package cn.com.xuxiaowei.gitbot.gitlab;

import cn.com.xuxiaowei.gitbot.entity.GlNamespace;
import cn.com.xuxiaowei.gitbot.entity.GlProject;
import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import cn.com.xuxiaowei.gitbot.service.IGlNamespaceService;
import cn.com.xuxiaowei.gitbot.service.IGlProjectService;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
public class GitLabTests {

	@Autowired
	private IGlProjectService glProjectService;

	@Autowired
	private IGlNamespaceService glNamespaceService;

	@Autowired
	private GitbotProperties gitbotProperties;

	@Test
	void getProjects() throws GitLabApiException, MalformedURLException {
		try (GitLabApi gitLabApi = new GitLabApi("https://gitlab.xuxiaowei.com.cn", null)) {
			gitLabApi.setIgnoreCertificateErrors(true);
			String gitLabServerUrl = gitLabApi.getGitLabServerUrl();
			URL url = new URL(gitLabServerUrl);
			String host = url.getHost();
			List<Project> projects = gitLabApi.getProjectApi().getProjects();
			for (Project project : projects) {
				log.info(project.getName());
				saveOrUpdate(project, host);
			}
		}
	}

	@Test
	void getOwnedProjects() throws GitLabApiException, MalformedURLException {
		String personalAccessToken = System.getenv("GITBOT_GITLAB_TOKEN");
		try (GitLabApi gitLabApi = new GitLabApi("https://jihulab.com", personalAccessToken)) {
			gitLabApi.setIgnoreCertificateErrors(true);
			String gitLabServerUrl = gitLabApi.getGitLabServerUrl();
			URL url = new URL(gitLabServerUrl);
			String host = url.getHost();
			List<Project> projects = gitLabApi.getProjectApi().getOwnedProjects();
			for (Project project : projects) {
				log.info(project.getName());
				saveOrUpdate(project, host);
			}
		}
	}

	void saveOrUpdate(Project project, String host) {
		GlProject glProject = new GlProject();
		glProject.setId(project.getId());
		glProject.setHost(host);
		glProject.setApprovalsBeforeMerge(project.getApprovalsBeforeMerge());
		glProject.setArchived(project.getArchived());
		glProject.setAvatarUrl(project.getAvatarUrl());
		glProject.setContainerRegistryEnabled(project.getContainerRegistryEnabled());
		// @formatter:off
		glProject.setCreatedAt(project.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		// @formatter:on
		glProject.setCreatorId(project.getCreatorId());
		glProject.setDefaultBranch(project.getDefaultBranch());
		glProject.setDescription(project.getDescription());
		glProject.setForksCount(project.getForksCount());
		// @formatter:off
		glProject.setForkedFromProjectId(project.getForkedFromProject() == null ? null : project.getForkedFromProject().getId());
		// @formatter:on
		glProject.setHttpUrlToRepo(project.getHttpUrlToRepo());
		glProject.setIsPublic(project.getPublic());
		glProject.setIssuesEnabled(project.getIssuesEnabled());
		glProject.setJobsEnabled(project.getJobsEnabled());
		// @formatter:off
		glProject.setLastActivityAt(project.getLastActivityAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		// @formatter:on
		glProject.setLfsEnabled(project.getLfsEnabled());
		glProject.setMergeMethod(project.getAvatarUrl());
		glProject.setMergeRequestsEnabled(project.getMergeRequestsEnabled());
		glProject.setName(project.getName());
		glProject.setNamespaceId(project.getNamespace().getId());

		GlNamespace glNamespace = new GlNamespace();
		glNamespace.setId(project.getNamespace().getId());
		glNamespace.setHost(host);
		glNamespace.setName(project.getNamespace().getName());
		glNamespace.setPath(project.getNamespace().getPath());
		glNamespace.setKind(project.getNamespace().getKind());
		glNamespace.setFullPath(project.getNamespace().getFullPath());
		glNamespace.setAvatarUrl(project.getNamespace().getAvatarUrl());
		glNamespace.setWebUrl(project.getNamespace().getWebUrl());
		glNamespaceService.saveOrUpdate(glNamespace);

		glProject.setNameWithNamespace(project.getNameWithNamespace());
		glProject.setOnlyAllowMergeIfPipelineSucceeds(project.getOnlyAllowMergeIfPipelineSucceeds());
		// @formatter:off
		glProject.setOnlyAllowMergeIfAllDiscussionsAreResolved(project.getOnlyAllowMergeIfAllDiscussionsAreResolved());
		// @formatter:on
		glProject.setOpenIssuesCount(project.getOpenIssuesCount());
		glProject.setOwnerId(project.getOwner() == null ? null : project.getOwner().getId());
		glProject.setPath(project.getPath());
		glProject.setPathWithNamespace(project.getPathWithNamespace());
		// glProject.setPermissions(project.getPermissions());
		glProject.setPublicJobs(project.getPublicJobs());
		glProject.setRepositoryStorage(project.getRepositoryStorage());
		glProject.setRequestAccessEnabled(project.getRequestAccessEnabled());
		glProject.setRunnersToken(gitbotProperties.getGitlab().isSaveRunnersToken() ? project.getRunnersToken() : null);
		glProject.setSharedRunnersEnabled(project.getSharedRunnersEnabled());
		// glProject.setSharedWithGroups(project.getSharedWithGroups());
		glProject.setSnippetsEnabled(project.getSnippetsEnabled());
		glProject.setSshUrlToRepo(project.getSshUrlToRepo());
		glProject.setStarCount(project.getStarCount());
		// glProject.setTagList(project.getTagList());
		// glProject.setTopics(project.getTopics());
		glProject.setVisibilityLevel(project.getVisibilityLevel());
		glProject.setVisibility(project.getVisibility() == null ? null : project.getVisibility().toString());
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
		// @formatter:off
		glProject.setBuildGitStrategy(project.getBuildGitStrategy() == null ? null : project.getBuildGitStrategy().toString());
		// @formatter:on
		glProject.setReadmeUrl(project.getReadmeUrl());
		glProject.setCanCreateMergeRequestIn(project.getCanCreateMergeRequestIn());
		glProject.setImportStatus(project.getImportStatus() == null ? null : project.getImportStatus().toString());
		glProject.setCiDefaultGitDepth(project.getCiDefaultGitDepth());
		glProject.setCiForwardDeploymentEnabled(project.getCiForwardDeploymentEnabled());
		glProject.setCiConfigPath(project.getCiConfigPath());
		glProject.setRemoveSourceBranchAfterMerge(project.getRemoveSourceBranchAfterMerge());
		glProject.setAutoDevopsEnabled(project.getAutoDevopsEnabled());
		// @formatter:off
		glProject.setAutoDevopsDeployStrategy(project.getAutoDevopsDeployStrategy() == null ? null : project.getAutoDevopsDeployStrategy().toString());
		// @formatter:on
		glProject.setAutocloseReferencedIssues(project.getAutocloseReferencedIssues());
		glProject.setEmailsDisabled(project.getEmailsDisabled());
		glProject.setSuggestionCommitMessage(project.getSuggestionCommitMessage());
		glProject.setSquashOption(project.getSquashOption() == null ? null : project.getSquashOption().toString());
		// @formatter:off
		glProject.setMarkedForDeletionOn(project.getMarkedForDeletionOn() == null ? null : project.getMarkedForDeletionOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		// @formatter:on

		glProjectService.saveOrUpdate(glProject);
	}

}
