package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlCommit;
import cn.com.xuxiaowei.gitbot.mapper.GlCommitMapper;
import cn.com.xuxiaowei.gitbot.service.IGlCommitService;
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
public class GlCommitServiceImpl extends ServiceImpl<GlCommitMapper, GlCommit> implements IGlCommitService {

	@Override
	public boolean saveOrUpdate(GlCommit entity) {
		QueryWrapper<GlCommit> queryWrapper = new QueryWrapper<GlCommit>()
			//
			.eq("`host`", entity.getHost())
			//
			.eq("id", entity.getId());
		long count = count(queryWrapper);
		return count == 0 ? save(entity) : update(entity, queryWrapper);
	}

}
