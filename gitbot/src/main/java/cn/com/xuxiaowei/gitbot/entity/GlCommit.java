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
@TableName("gl_commit")
public class GlCommit implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String host;

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
