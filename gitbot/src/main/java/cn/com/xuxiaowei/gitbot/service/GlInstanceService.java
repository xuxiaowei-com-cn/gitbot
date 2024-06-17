package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.vo.GlInstanceVO;

import java.util.List;

/**
 * GitLab 实例 接口
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public interface GlInstanceService {

	/**
	 * GitLab 实例列表
	 */
	List<GlInstanceVO> list();

}
