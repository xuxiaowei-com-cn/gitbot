package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.bo.GlProjectBO;
import cn.com.xuxiaowei.gitbot.constant.LogConstants;
import cn.com.xuxiaowei.gitbot.constant.RedisKeyConstants;
import cn.com.xuxiaowei.gitbot.entity.GlProject;
import cn.com.xuxiaowei.gitbot.mapper.GlProjectMapper;
import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import cn.com.xuxiaowei.gitbot.service.IGlBranchService;
import cn.com.xuxiaowei.gitbot.service.IGlEnvironmentService;
import cn.com.xuxiaowei.gitbot.service.IGlProjectService;
import cn.com.xuxiaowei.gitbot.service.IGlVariableService;
import cn.com.xuxiaowei.gitbot.utils.DateUtils;
import cn.com.xuxiaowei.gitbot.utils.RedisKeyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Slf4j
@Service
public class GlProjectServiceImpl extends ServiceImpl<GlProjectMapper, GlProject> implements IGlProjectService {

	private GitbotProperties gitbotProperties;

	private StringRedisTemplate stringRedisTemplate;

	private IGlBranchService glBranchService;

	private IGlEnvironmentService glEnvironmentService;

	private IGlVariableService glVariableService;

	@Autowired
	public void setGitbotProperties(GitbotProperties gitbotProperties) {
		this.gitbotProperties = gitbotProperties;
	}

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Autowired
	public void setGlBranchService(IGlBranchService glBranchService) {
		this.glBranchService = glBranchService;
	}

	@Autowired
	public void setGlEnvironmentService(IGlEnvironmentService glEnvironmentService) {
		this.glEnvironmentService = glEnvironmentService;
	}

	@Autowired
	public void setGlVariableService(IGlVariableService glVariableService) {
		this.glVariableService = glVariableService;
	}

	@Override
	public Page<GlProject> pageByGlProjectBO(GlProjectBO glProjectBO) {

		Page<GlProject> page = new Page<>(glProjectBO.getCurrent(), glProjectBO.getSize());

		String host = glProjectBO.getHost();
		String name = glProjectBO.getName();

		return lambdaQuery().eq(StringUtils.hasText(host), GlProject::getHost, host)
			.like(StringUtils.hasText(name), GlProject::getName, name)
			.page(page);
	}

	@Override
	public void saveOwnedProject(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken)
			throws GitLabApiException, MalformedURLException {

		URL url = new URL(hostUrl);
		String host = url.getHost();

		String id = MDC.get(LogConstants.G_REQUEST_ID);
		if (!StringUtils.hasText(id)) {
			id = UUID.randomUUID().toString();
		}

		Long dataTimeout = gitbotProperties.getDataTimeout();
		TimeUnit dataUnit = gitbotProperties.getDataUnit();
		String saveProjectRedisKeyPrefix = gitbotProperties.getSaveProjectRedisKeyPrefix();
		String saveProjectRedisHashKey = RedisKeyUtils.hash(saveProjectRedisKeyPrefix, host, id);

		// @formatter:off
		stringRedisTemplate.opsForHash().put(saveProjectRedisHashKey, RedisKeyConstants.START_AT, DateUtils.format(LocalDateTime.now()));
		// @formatter:on

		try (GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken)) {
			gitLabApi.setIgnoreCertificateErrors(true);
			List<Project> projects = gitLabApi.getProjectApi().getOwnedProjects();
			for (Project project : projects) {

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
				glProject.setSquashOption(project.getSquashOption() == null ? null : project.getSquashOption().toString());
				glProject.setMarkedForDeletionOn(project.getMarkedForDeletionOn() == null ? null : project.getMarkedForDeletionOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				// @formatter:on

				QueryWrapper<GlProject> queryWrapper = new QueryWrapper<GlProject>()
					//
					.eq("id", project.getId())
					//
					.eq("`host`", glProject.getHost());
				long count = count(queryWrapper);
				if (count == 0) {
					save(glProject);
					// @formatter:off
					stringRedisTemplate.opsForHash().increment(saveProjectRedisHashKey, RedisKeyConstants.SAVE, 1);
					// @formatter:on
				}
				else {
					update(glProject, queryWrapper);
					// @formatter:off
					stringRedisTemplate.opsForHash().increment(saveProjectRedisHashKey, RedisKeyConstants.UPDATE, 1);
					// @formatter:on
				}

				glBranchService.saveOwnedBranch(hostUrl, ignoreCertificateErrors, personalAccessToken, project.getId());

				glEnvironmentService.saveEnvironment(hostUrl, ignoreCertificateErrors, personalAccessToken,
						project.getId());

				glVariableService.saveVariable(hostUrl, ignoreCertificateErrors, personalAccessToken, project.getId());

			}

		}
		finally {
			stringRedisTemplate.expire(saveProjectRedisHashKey, dataTimeout, dataUnit);

			// @formatter:off
			log.debug("saved: {}", stringRedisTemplate.opsForHash().get(saveProjectRedisHashKey, RedisKeyConstants.SAVE));
			log.debug("updated: {}", stringRedisTemplate.opsForHash().get(saveProjectRedisHashKey, RedisKeyConstants.UPDATE));
			// @formatter:on
		}

		// @formatter:off
		stringRedisTemplate.opsForHash().put(saveProjectRedisHashKey, RedisKeyConstants.END_AT, DateUtils.format(LocalDateTime.now()));
		// @formatter:on
	}

}
