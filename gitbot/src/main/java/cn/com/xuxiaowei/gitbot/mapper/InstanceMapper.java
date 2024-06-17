package cn.com.xuxiaowei.gitbot.mapper;

import cn.com.xuxiaowei.gitbot.vo.InstanceVO;

import java.util.List;

/**
 * 实例 接口
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public interface InstanceMapper {

	/**
	 * 实例列表
	 */
	List<InstanceVO> list();

}
