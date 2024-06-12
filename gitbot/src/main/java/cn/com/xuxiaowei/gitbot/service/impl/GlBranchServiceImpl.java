package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlBranch;
import cn.com.xuxiaowei.gitbot.mapper.GlBranchMapper;
import cn.com.xuxiaowei.gitbot.service.IGlBranchService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Service
public class GlBranchServiceImpl extends ServiceImpl<GlBranchMapper, GlBranch> implements IGlBranchService {

	@Override
	public boolean saveOrUpdate(GlBranch entity) {
		QueryWrapper<GlBranch> queryWrapper = new QueryWrapper<GlBranch>()
			//
			.eq("project_id", entity.getProjectId())
			//
			.eq("`host`", entity.getHost())
			//
			.eq("name", entity.getName());
		long count = count(queryWrapper);
		return count == 0 ? save(entity) : update(entity, queryWrapper);
	}

}
