package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Getter
@Setter
@TableName("gh_repository")
public class GhRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nodeId;

	private String description;

	private String name;

	private String fullName;

	private String htmlUrl;

	private String license;

	private String gitUrl;

	private String sshUrl;

	private String svnUrl;

	private String mirrorUrl;

	private Boolean hasIssues;

	private Boolean hasWiki;

	private Boolean fork;

	private Boolean hasDownloads;

	private Boolean hasPages;

	private Boolean archived;

	private Boolean disabled;

	private Boolean hasProjects;

	private Boolean allowSquashMerge;

	private Boolean allowMergeCommit;

	private Boolean allowRebaseMerge;

	private Boolean allowForking;

	private Boolean deleteBranchOnMerge;

	private Boolean isPrivate;

	private String visibility;

	private Integer forksCount;

	private Integer stargazersCount;

	private Integer watchersCount;

	private Integer size;

	private Integer openIssuesCount;

	private Integer subscribersCount;

	private LocalDateTime pushedAt;

	private String defaultBranch;

	private String language;

	private Long templateRepositoryId;

	private Long sourceId;

	private Long parentId;

	private Boolean isTemplate;

	private String url;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
