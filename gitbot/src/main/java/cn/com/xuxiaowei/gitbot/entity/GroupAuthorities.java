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
 * @since 2024-06-12
 */
@Data
@TableName("group_authorities")
public class GroupAuthorities implements Serializable {

	private static final long serialVersionUID = 1L;

	private String authority;

	private Integer groupId;

}
