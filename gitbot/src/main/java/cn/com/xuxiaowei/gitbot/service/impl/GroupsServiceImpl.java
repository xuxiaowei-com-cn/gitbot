package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.Groups;
import cn.com.xuxiaowei.gitbot.mapper.GroupsMapper;
import cn.com.xuxiaowei.gitbot.service.IGroupsService;
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
public class GroupsServiceImpl extends ServiceImpl<GroupsMapper, Groups> implements IGroupsService {

}
