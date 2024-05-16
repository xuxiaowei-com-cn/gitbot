package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GhRepository;
import cn.com.xuxiaowei.gitbot.mapper.GhRepositoryMapper;
import cn.com.xuxiaowei.gitbot.service.IGhRepositoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-16
 */
@Service
public class GhRepositoryServiceImpl extends ServiceImpl<GhRepositoryMapper, GhRepository>
		implements IGhRepositoryService {

}
