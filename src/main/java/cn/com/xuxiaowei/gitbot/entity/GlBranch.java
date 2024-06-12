package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("gl_branch")
public class GlBranch implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId("project_id")
	private Long projectId;

	@TableId("host")
	private String host;

	@TableId("name")
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

	private String commitCreatedAt;

	private String commitId;

	private String commitMessage;

	private String commitParentIds;

	private Integer commitShortId;

	private String commitStats;

	private String commitStatus;

	private LocalDateTime commitTimestamp;

	private String commitTitle;

	private String commitUrl;

	private String commitWebUrl;

	private Integer commitProjectId;

	private String commitLastPipeline;

}
