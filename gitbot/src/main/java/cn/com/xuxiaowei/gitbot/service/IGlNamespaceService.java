package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.GlNamespace;
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
public interface IGlNamespaceService extends IService<GlNamespace> {

	void saveNamespace(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken)
			throws GitLabApiException, MalformedURLException;

}
