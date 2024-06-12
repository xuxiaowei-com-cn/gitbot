package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.Oauth2Authorization;
import cn.com.xuxiaowei.gitbot.mapper.Oauth2AuthorizationMapper;
import cn.com.xuxiaowei.gitbot.service.IOauth2AuthorizationService;
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
public class Oauth2AuthorizationServiceImpl extends ServiceImpl<Oauth2AuthorizationMapper, Oauth2Authorization>
		implements IOauth2AuthorizationService {

}
