package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.ScheduledToken;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-18
 */
public interface IScheduledTokenService extends IService<ScheduledToken> {

	List<ScheduledToken> listByHostNotGitHub();

	ScheduledToken getByHostGitHub();

}
