package cn.com.xuxiaowei.gitbot.entity;

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
public class Authorities implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String authority;

}
