package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GroupAuthorities;
import cn.com.xuxiaowei.gitbot.mapper.GroupAuthoritiesMapper;
import cn.com.xuxiaowei.gitbot.service.IGroupAuthoritiesService;
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
public class GroupAuthoritiesServiceImpl extends ServiceImpl<GroupAuthoritiesMapper, GroupAuthorities>
		implements IGroupAuthoritiesService {

}
