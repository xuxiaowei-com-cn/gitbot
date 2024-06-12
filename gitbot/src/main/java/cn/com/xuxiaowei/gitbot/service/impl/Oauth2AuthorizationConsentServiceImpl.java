package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.Oauth2AuthorizationConsent;
import cn.com.xuxiaowei.gitbot.mapper.Oauth2AuthorizationConsentMapper;
import cn.com.xuxiaowei.gitbot.service.IOauth2AuthorizationConsentService;
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
public class Oauth2AuthorizationConsentServiceImpl
		extends ServiceImpl<Oauth2AuthorizationConsentMapper, Oauth2AuthorizationConsent>
		implements IOauth2AuthorizationConsentService {

}
