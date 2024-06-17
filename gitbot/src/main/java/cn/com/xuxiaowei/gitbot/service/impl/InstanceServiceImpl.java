package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.mapper.InstanceMapper;
import cn.com.xuxiaowei.gitbot.service.InstanceService;
import cn.com.xuxiaowei.gitbot.vo.InstanceVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实例 接口 实现类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Service
public class InstanceServiceImpl implements InstanceService {

	@Resource
	private InstanceMapper instanceMapper;

	/**
	 * 实例列表
	 */
	@Override
	public List<InstanceVO> list() {
		return instanceMapper.list();
	}

}
