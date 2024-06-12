package cn.com.xuxiaowei.gitbot.entity;

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
@TableName("group_authorities")
public class GroupAuthorities implements Serializable {

	private static final long serialVersionUID = 1L;

	private String authority;

	private Integer groupId;

}
