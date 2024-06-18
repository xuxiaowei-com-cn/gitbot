package cn.com.xuxiaowei.gitbot.utils;

/**
 * Redis Key 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class RedisKeyUtils {

	public static String lock(String prefix, String host, String token) {
		return prefix + "lock:" + host + ":" + token;
	}

	public static String hash(String prefix, String host, String id) {
		return prefix + "hash:" + host + ":" + id;
	}

}
