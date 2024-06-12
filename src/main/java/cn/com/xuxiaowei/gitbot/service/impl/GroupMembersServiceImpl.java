package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GroupMembers;
import cn.com.xuxiaowei.gitbot.mapper.GroupMembersMapper;
import cn.com.xuxiaowei.gitbot.service.IGroupMembersService;
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
public class GroupMembersServiceImpl extends ServiceImpl<GroupMembersMapper, GroupMembers>
		implements IGroupMembersService {

}
