package cn.com.xuxiaowei.gitbot.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
public class DateUtils {

	public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String format(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN));
	}

	public static String format(LocalDateTime localDateTime, String pattern) {
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}

}
