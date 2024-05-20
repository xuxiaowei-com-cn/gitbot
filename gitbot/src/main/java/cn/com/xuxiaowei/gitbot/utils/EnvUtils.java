package cn.com.xuxiaowei.gitbot.utils;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
public class EnvUtils {

	public static String getenv(String name, String defaultValue) {
		String getenv = System.getenv(name);
		return getenv == null ? defaultValue : getenv;
	}

}
