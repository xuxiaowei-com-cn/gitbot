package cn.com.xuxiaowei.gitbot.entity;

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
public class Groups implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String groupName;

}
