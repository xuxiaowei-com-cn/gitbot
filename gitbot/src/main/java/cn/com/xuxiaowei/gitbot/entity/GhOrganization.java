package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2024-06-14
 */
@Data
@TableName("gh_organization")
public class GhOrganization implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;

	private String url;

	private String login;

	private String avatarUrl;

	private String location;

	private String blog;

	private String email;

	private String name;

	private String company;

	private String type;

	private String twitterUsername;

	private String htmlUrl;

	private Integer followers;

	private Integer following;

	private Integer publicRepos;

	private Integer publicGists;

	private Boolean siteAdmin;

	private String nodeId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Long rootMyselfId;

}
