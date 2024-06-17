package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.vo.InstanceVO;

import java.util.List;

/**
 * 实例 接口
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public interface InstanceService {

	/**
	 * 实例列表
	 */
	List<InstanceVO> list();

}
