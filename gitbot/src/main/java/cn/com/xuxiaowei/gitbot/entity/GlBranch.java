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
 * @since 2024-06-12
 */
@Data
@TableName("gl_branch")
public class GlBranch implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long projectId;

	private String host;

	private String name;

	private Boolean developersCanMerge;

	private Boolean developersCanPush;

	private Boolean merged;

	private Boolean isProtected;

	private Boolean isDefault;

	private Boolean canPush;

	private String webUrl;

	private String commitAuthor;

	private LocalDateTime commitAuthoredDate;

	private String commitAuthorEmail;

	private String commitAuthorName;

	private LocalDateTime commitCommittedDate;

	private String commitCommitterEmail;

	private String commitCommitterName;

	private LocalDateTime commitCreatedAt;

	private String commitId;

	private String commitMessage;

	private String commitParentIds;

	private String commitShortId;

	private String commitStats;

	private String commitStatus;

	private LocalDateTime commitTimestamp;

	private String commitTitle;

	private String commitUrl;

	private String commitWebUrl;

	private Long commitProjectId;

	private Long commitLastPipelineId;

	private Long commitLastPipelineIid;

}
