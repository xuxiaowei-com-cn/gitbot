package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlNamespace;
import cn.com.xuxiaowei.gitbot.mapper.GlNamespaceMapper;
import cn.com.xuxiaowei.gitbot.service.IGlNamespaceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Namespace;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Slf4j
@Service
public class GlNamespaceServiceImpl extends ServiceImpl<GlNamespaceMapper, GlNamespace> implements IGlNamespaceService {

	@Override
	public void saveNamespace(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken)
			throws GitLabApiException, MalformedURLException {

		URL url = new URL(hostUrl);
		String host = url.getHost();

		int saved = 0;
		int updated = 0;

		try (GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken)) {
			gitLabApi.setIgnoreCertificateErrors(true);
			List<Namespace> namespaces = gitLabApi.getNamespaceApi().getNamespaces();
			for (Namespace namespace : namespaces) {

				GlNamespace glNamespace = new GlNamespace();
				glNamespace.setId(namespace.getId());
				glNamespace.setHost(host);
				glNamespace.setName(namespace.getName());
				glNamespace.setPath(namespace.getPath());
				glNamespace.setKind(namespace.getKind());
				glNamespace.setFullPath(namespace.getFullPath());
				glNamespace.setAvatarUrl(namespace.getAvatarUrl());
				glNamespace.setWebUrl(namespace.getWebUrl());

				QueryWrapper<GlNamespace> queryWrapper = new QueryWrapper<GlNamespace>()
					//
					.eq("id", glNamespace.getId())
					//
					.eq("`host`", glNamespace.getHost());
				long count = count(queryWrapper);
				if (count == 0) {
					save(glNamespace);
					saved++;
				}
				else {
					update(glNamespace, queryWrapper);
					updated++;
				}
			}

		}
		finally {
			log.debug("saved: {}", saved);
			log.debug("updated: {}", updated);
		}
	}

}
