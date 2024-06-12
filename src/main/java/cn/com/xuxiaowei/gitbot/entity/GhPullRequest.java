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
@TableName("gh_pull_request")
public class GhPullRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String patchUrl;

	private String diffUrl;

	private String issueUrl;

	private LocalDateTime mergedAt;

	private String mergedBy;

	private Integer reviewComments;

	private Integer additions;

	private Integer commits;

	private String merged;

	private String maintainerCanModify;

	private String draft;

	private Boolean mergeable;

	private Integer deletions;

	private String mergeableState;

	private Integer changedFiles;

	private String mergeCommitSha;

	private String autoMerge;

	private String assignee;

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

	private String milestone;

	private String closedBy;

	private Boolean locked;

	private String url;

	private String nodeId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
