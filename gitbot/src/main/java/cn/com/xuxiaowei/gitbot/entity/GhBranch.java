package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-14
 */
@Getter
@Setter
@TableName("gh_branch")
public class GhBranch implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@TableId("name")
	private String name;

	private String sha;

	private String url;

	private Boolean protection;

	private String protectionUrl;

}
