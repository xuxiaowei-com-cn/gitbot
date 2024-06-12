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
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private Boolean enabled;

}
