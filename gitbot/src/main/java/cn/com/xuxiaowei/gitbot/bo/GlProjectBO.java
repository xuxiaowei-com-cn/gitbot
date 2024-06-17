package cn.com.xuxiaowei.gitbot.bo;

import lombok.Data;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class GlProjectBO {

	private long current = 1;

	private long size = 10;

	private String host;

	private String name;

}
