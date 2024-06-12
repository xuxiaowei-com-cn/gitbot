package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("gl_note")
public class GlNote implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String host;

	private Long projectId;

	private Long issueId;

	private Long issueIid;

	private String attachment;

	private Long authorId;

	private String body;

	private LocalDateTime createdAt;

	private Boolean downvote;

	private LocalDateTime expiresAt;

	private String fileName;

	private Long noteableId;

	private String noteableType;

	private Long noteableIid;

	@TableField("`system`")
	private Boolean system;

	private String title;

	private LocalDateTime updatedAt;

	private Boolean upvote;

	private Boolean resolved;

	private Boolean resolvable;

	private Long resolvedBy;

	private LocalDateTime resolvedAt;

	private Boolean internal;

	private String type;

	private String position;

}
