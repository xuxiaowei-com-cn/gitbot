package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlNamespace;
import cn.com.xuxiaowei.gitbot.mapper.GlNamespaceMapper;
import cn.com.xuxiaowei.gitbot.service.IGlNamespaceService;
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
public class GlNamespaceServiceImpl extends ServiceImpl<GlNamespaceMapper, GlNamespace> implements IGlNamespaceService {

	@Override
	public boolean saveOrUpdate(GlNamespace entity) {
		QueryWrapper<GlNamespace> queryWrapper = new QueryWrapper<GlNamespace>()
			//
			.eq("`host`", entity.getHost())
			//
			.eq("id", entity.getId());
		long count = count(queryWrapper);
		return count == 0 ? save(entity) : update(entity, queryWrapper);
	}

}
