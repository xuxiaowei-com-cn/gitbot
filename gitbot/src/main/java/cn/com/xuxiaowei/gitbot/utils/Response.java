package cn.com.xuxiaowei.gitbot.utils;

import cn.com.xuxiaowei.gitbot.constant.LogConstants;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * 响应数据父类
 * <p>
 * 支持链式调用
 *
 * @author xuxiaowei
 * @since 0.0.1
 * @param <T> 响应数据泛型
 */
@Data
@Accessors(chain = true)
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 是否成功
	 */
	private boolean success;

	/**
	 * HTTP 状态码
	 */
	private int httpStatus;

	/**
	 * 错误码
	 */
	private String errorCode;

	/**
	 * 响应消息
	 */
	private String message;

	/**
	 * 错误描述地址
	 */
	private String url;

	/**
	 * 请求 ID
	 */
	@Setter(AccessLevel.PRIVATE)
	private String requestId;

	/**
	 * 响应数据
	 */
	private T data;

	public Response() {
		this.requestId = requestId();
	}

	public Response(T data) {
		this.requestId = requestId();
		this.data = data;
	}

	public static <T> Response<T> ok() {
		return new Response<T>().setSuccess(true).setHttpStatus(HttpStatus.OK.value()).setRequestId(requestId());
	}

	public static <T> Response<T> ok(String message) {
		return new Response<T>().setSuccess(true)
			.setHttpStatus(HttpStatus.OK.value())
			.setMessage(message)
			.setRequestId(requestId());
	}

	public static <T> Response<T> error() {
		return new Response<T>().setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.setMessage("系统异常")
			.setRequestId(requestId());
	}

	public static <T> Response<T> error(String message) {
		return new Response<T>().setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.setMessage(message)
			.setRequestId(requestId());
	}

	public static <T> Response<T> error(String errorCode, String message) {
		return new Response<T>().setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.setErrorCode(errorCode)
			.setMessage(message)
			.setRequestId(requestId());
	}

	public static <T> Response<T> error(int code, String message) {
		return new Response<T>().setHttpStatus(code).setMessage(message).setRequestId(requestId());
	}

	public static <T> Response<T> error(int code, String errorCode, String message) {
		return new Response<T>().setHttpStatus(code)
			.setErrorCode(errorCode)
			.setMessage(message)
			.setRequestId(requestId());
	}

	private static String requestId() {
		String id = MDC.get(LogConstants.G_REQUEST_ID);
		if (id == null || id.isEmpty()) {
			id = UUID.randomUUID().toString();
		}
		return id;
	}

}
