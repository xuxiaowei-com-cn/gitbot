package cn.com.xuxiaowei.gitbot.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GitbotRuntimeException extends RuntimeException {

	/**
	 * 错误代码
	 */
	private String code;

	/**
	 * 参考
	 */
	private List<String> references;

	public GitbotRuntimeException(String message) {
		super(message);
		this.code = "500";
	}

	public GitbotRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.code = "500";
	}

	public GitbotRuntimeException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

}
