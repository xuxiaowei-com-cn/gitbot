package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.Oauth2RegisteredClient;
import cn.com.xuxiaowei.gitbot.mapper.Oauth2RegisteredClientMapper;
import cn.com.xuxiaowei.gitbot.service.IOauth2RegisteredClientService;
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
public class Oauth2RegisteredClientServiceImpl extends ServiceImpl<Oauth2RegisteredClientMapper, Oauth2RegisteredClient>
		implements IOauth2RegisteredClientService {

}
