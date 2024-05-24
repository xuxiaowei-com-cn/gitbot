package cn.com.xuxiaowei.gitbot.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Git 机器人 运行时父异常
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GitbotRuntimeException extends RuntimeException {

	/**
	 * HTTP 状态码
	 */
	private int httpStatus;

	/**
	 * 错误码
	 */
	private String errorCode;

	/**
	 * 参考
	 */
	private List<String> references;

	public GitbotRuntimeException(String message) {
		super(message);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

	public GitbotRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

	public GitbotRuntimeException(int httpStatus, String message, Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
	}

}
