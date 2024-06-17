package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.mapper.GlInstanceMapper;
import cn.com.xuxiaowei.gitbot.service.GlInstanceService;
import cn.com.xuxiaowei.gitbot.vo.GlInstanceVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GitLab 实例 接口 实现类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Service
public class GlInstanceServiceImpl implements GlInstanceService {

	@Resource
	private GlInstanceMapper glInstanceMapper;

	/**
	 * GitLab 实例列表
	 */
	@Override
	public List<GlInstanceVO> list() {
		return glInstanceMapper.list();
	}

}
