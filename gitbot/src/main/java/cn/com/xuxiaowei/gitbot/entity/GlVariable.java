package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("gl_variable")
public class GlVariable implements Serializable {

	private static final long serialVersionUID = 1L;

	private String host;

	private Long projectId;

	@TableField("`key`")
	private String key;

	private String environmentScope;

	private String value;

	private String variableType;

	private Boolean isProtected;

	private Boolean masked;

}
