package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.GhRepository;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-16
 */
public interface IGhRepositoryService extends IService<GhRepository> {

	/**
	 * 需要授权：read:org
	 */
	void saveRepository(String oauthToken) throws IOException;

}
