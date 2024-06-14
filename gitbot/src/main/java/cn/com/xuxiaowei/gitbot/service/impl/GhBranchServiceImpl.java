package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GhBranch;
import cn.com.xuxiaowei.gitbot.mapper.GhBranchMapper;
import cn.com.xuxiaowei.gitbot.service.IGhBranchService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-14
 */
@Service
public class GhBranchServiceImpl extends ServiceImpl<GhBranchMapper, GhBranch> implements IGhBranchService {

	@Override
	public boolean saveOrUpdate(GhBranch ghBranch) {
		QueryWrapper<GhBranch> queryWrapper = new QueryWrapper<GhBranch>()
			//
			.eq("id", ghBranch.getId())
			//
			.eq("name", ghBranch.getName());
		long count = count(queryWrapper);
		if (count == 0) {
			return save(ghBranch);
		}
		else {
			return update(ghBranch, queryWrapper);
		}
	}

}
