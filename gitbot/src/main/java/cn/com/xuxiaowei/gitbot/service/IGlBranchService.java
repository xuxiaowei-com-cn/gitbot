package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.GlBranch;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gitlab4j.api.GitLabApiException;

import java.net.MalformedURLException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
public interface IGlBranchService extends IService<GlBranch> {

	void saveOwnedBranch(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken,
			Object projectIdOrPath) throws GitLabApiException, MalformedURLException;

}
