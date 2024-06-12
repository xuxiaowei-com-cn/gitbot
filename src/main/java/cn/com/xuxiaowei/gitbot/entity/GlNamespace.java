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
 * @since 2024-06-12
 */
@Getter
@Setter
@TableName("gl_namespace")
public class GlNamespace implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@TableId("host")
	private String host;

	private String name;

	private String path;

	private String kind;

	private String fullPath;

	private String avatarUrl;

	private String webUrl;

}
