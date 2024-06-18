package cn.com.xuxiaowei.gitbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-18
 */
@Data
@TableName("scheduled_token")
public class ScheduledToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId("host")
	private String host;

	private Boolean ignoreCertificateErrors;

	private String token;

}
