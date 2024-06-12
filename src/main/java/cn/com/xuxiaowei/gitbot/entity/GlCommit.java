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
@TableName("gl_commit")
public class GlCommit implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	@TableId("host")
	private String host;

	@TableId("branch_name")
	private String branchName;

	private LocalDateTime authoredDate;

	private String authorEmail;

	private String authorName;

	private LocalDateTime committedDate;

	private String committerEmail;

	private String committerName;

	private LocalDateTime createdAt;

	private String message;

	private String shortId;

	private String status;

	private LocalDateTime timestamp;

	private String title;

	private String url;

	private String webUrl;

	private Long projectId;

	private Long lastPipelineId;

	private Long lastPipelineIid;

}
