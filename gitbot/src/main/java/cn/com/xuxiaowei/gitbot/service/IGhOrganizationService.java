package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.GhOrganization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-14
 */
public interface IGhOrganizationService extends IService<GhOrganization> {

	/**
	 * 需要授权：read:org
	 */
	void saveMyOrganizations(String oauthToken) throws IOException;

}
