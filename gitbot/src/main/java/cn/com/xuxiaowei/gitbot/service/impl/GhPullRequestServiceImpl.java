package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GhPullRequest;
import cn.com.xuxiaowei.gitbot.mapper.GhPullRequestMapper;
import cn.com.xuxiaowei.gitbot.service.IGhPullRequestService;
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
public class GhPullRequestServiceImpl extends ServiceImpl<GhPullRequestMapper, GhPullRequest>
		implements IGhPullRequestService {

}
