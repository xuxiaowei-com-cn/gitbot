package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Data
@TableName("gh_pull_request")
public class GhPullRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long projectId;

	private String patchUrl;

	private String diffUrl;

	private String issueUrl;

	private LocalDateTime mergedAt;

	private Long mergedBy;

	private Integer reviewComments;

	private Integer additions;

	private Integer commits;

	private Boolean merged;

	private String maintainerCanModify;

	private String draft;

	private Boolean mergeable;

	private Integer deletions;

	private String mergeableState;

	private Integer changedFiles;

	private String mergeCommitSha;

	private String autoMerge;

	private Long assignee;

	private String assignees;

	private String state;

	private String stateReason;

	private Integer number;

	private LocalDateTime closedAt;

	private String comments;

	private String body;

	private String title;

	private String htmlUrl;

	private String pullRequest;

	private Long milestone;

	private Long closedBy;

	private Boolean locked;

	private String url;

	private String nodeId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
