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
 * @since 2024-06-14
 */
@Data
@TableName("gl_environment")
public class GlEnvironment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String host;

	private Long projectId;

	private String name;

	private String slug;

	private String externalUrl;

	private String state;

	private String lastDeployment;

}
