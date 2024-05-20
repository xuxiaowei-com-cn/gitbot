package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Data
@TableName("gl_namespace")
public class GlNamespace implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String host;

	private String name;

	private String path;

	private String kind;

	private String fullPath;

	private String avatarUrl;

	private String webUrl;

}
