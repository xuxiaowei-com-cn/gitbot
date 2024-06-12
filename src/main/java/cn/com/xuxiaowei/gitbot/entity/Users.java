package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId("username")
	private String username;

	private String password;

	private Boolean enabled;

}
