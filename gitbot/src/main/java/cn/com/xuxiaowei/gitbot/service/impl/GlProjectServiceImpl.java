package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlProject;
import cn.com.xuxiaowei.gitbot.mapper.GlProjectMapper;
import cn.com.xuxiaowei.gitbot.service.IGlProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Service
public class GlProjectServiceImpl extends ServiceImpl<GlProjectMapper, GlProject> implements IGlProjectService {

	@Override
	public boolean saveOrUpdate(GlProject entity) {
		QueryWrapper<GlProject> queryWrapper = new QueryWrapper<GlProject>()
			//
			.eq("`host`", entity.getHost())
			//
			.eq("id", entity.getId());
		long count = count(queryWrapper);
		return count == 0 ? save(entity) : update(entity, queryWrapper);
	}

}
