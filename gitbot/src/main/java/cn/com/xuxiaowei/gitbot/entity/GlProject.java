package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Data
@TableName("gl_project")
public class GlProject implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String host;

	private Integer approvalsBeforeMerge;

	private Boolean archived;

	private String avatarUrl;

	private Boolean containerRegistryEnabled;

	private LocalDateTime createdAt;

	private Long creatorId;

	private String defaultBranch;

	private String description;

	private Integer forksCount;

	private Long forkedFromProjectId;

	private String httpUrlToRepo;

	private Boolean isPublic;

	private Boolean issuesEnabled;

	private Boolean jobsEnabled;

	private LocalDateTime lastActivityAt;

	private Boolean lfsEnabled;

	private String mergeMethod;

	private Boolean mergeRequestsEnabled;

	private String name;

	private Long namespaceId;

	private String nameWithNamespace;

	private Boolean onlyAllowMergeIfPipelineSucceeds;

	private Boolean onlyAllowMergeIfAllDiscussionsAreResolved;

	private Integer openIssuesCount;

	private Long ownerId;

	private String path;

	private String pathWithNamespace;

	private String permissions;

	private Boolean publicJobs;

	private String repositoryStorage;

	private Boolean requestAccessEnabled;

	private String runnersToken;

	private Boolean sharedRunnersEnabled;

	private String sharedWithGroups;

	private Boolean snippetsEnabled;

	private String sshUrlToRepo;

	private Integer starCount;

	private String tagList;

	private String topics;

	private Integer visibilityLevel;

	private String visibility;

	private Boolean wallEnabled;

	private String webUrl;

	private Boolean wikiEnabled;

	private Boolean printingMergeRequestLinkEnabled;

	private Boolean resolveOutdatedDiffDiscussions;

	private String statistics;

	private Boolean initializeWithReadme;

	private Boolean packagesEnabled;

	private Boolean emptyRepo;

	private String licenseUrl;

	private String license;

	private String customAttributes;

	private String buildCoverageRegex;

	private String buildGitStrategy;

	private String readmeUrl;

	private Boolean canCreateMergeRequestIn;

	private String importStatus;

	private Integer ciDefaultGitDepth;

	private Boolean ciForwardDeploymentEnabled;

	private String ciConfigPath;

	private Boolean removeSourceBranchAfterMerge;

	private Boolean autoDevopsEnabled;

	private String autoDevopsDeployStrategy;

	private Boolean autocloseReferencedIssues;

	private Boolean emailsDisabled;

	private String suggestionCommitMessage;

	private String squashOption;

	private LocalDateTime markedForDeletionOn;

}
