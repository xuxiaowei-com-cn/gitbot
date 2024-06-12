package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.GlProject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gitlab4j.api.GitLabApiException;

import java.net.MalformedURLException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
public interface IGlProjectService extends IService<GlProject> {

	void saveOwnedProject(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken)
			throws GitLabApiException, MalformedURLException;

}
