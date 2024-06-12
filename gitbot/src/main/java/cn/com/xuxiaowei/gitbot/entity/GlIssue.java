package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Data
@TableName("gl_issue")
public class GlIssue implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String host;

	private Long projectId;

	private Long iid;

	private Boolean subscribed;

	private Long assigneeId;

	private Long authorId;

	private Boolean confidential;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private LocalDateTime closedAt;

	private Long closedBy;

	private String description;

	private LocalDateTime dueDate;

	private String actualId;

	private String externalId;

	private String labels;

	private Long milestoneId;

	private String state;

	private String title;

	private Integer userNotesCount;

	private String webUrl;

	private Integer weight;

	private Boolean discussionLocked;

	private Integer timeEstimate;

	private Integer totalTimeSpent;

	private Integer humanTimeEstimate;

	private Integer humanTotalTimeSpent;

	private Integer upvotes;

	private Integer downvotes;

	private Integer mergeRequestsCount;

	private Boolean hasTasks;

	private String taskStatus;

	private Long iterationId;

	private Integer taskCompletionStatusCount;

	private Integer taskCompletionStatusCompletedCount;

}
