package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.ScheduledToken;
import cn.com.xuxiaowei.gitbot.mapper.ScheduledTokenMapper;
import cn.com.xuxiaowei.gitbot.service.IScheduledTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-18
 */
@Service
public class ScheduledTokenServiceImpl extends ServiceImpl<ScheduledTokenMapper, ScheduledToken>
		implements IScheduledTokenService {

	@Override
	public List<ScheduledToken> listByHostNotGitHub() {
		return lambdaQuery().notLike(ScheduledToken::getHost, "%github.com%").list();
	}

	@Override
	public ScheduledToken getByHostGitHub() {
		return lambdaQuery().like(ScheduledToken::getHost, "%github.com%").one();
	}

}
