package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.bo.GlBO;
import cn.com.xuxiaowei.gitbot.entity.GlNamespace;
import cn.com.xuxiaowei.gitbot.mapper.GlNamespaceMapper;
import cn.com.xuxiaowei.gitbot.service.IGlNamespaceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Namespace;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Service
public class GlNamespaceServiceImpl extends ServiceImpl<GlNamespaceMapper, GlNamespace> implements IGlNamespaceService {

	@Override
	public boolean saveOrUpdate(GlNamespace entity) {
		QueryWrapper<GlNamespace> queryWrapper = new QueryWrapper<GlNamespace>()
			//
			.eq("`host`", entity.getHost())
			//
			.eq("id", entity.getId());
		long count = count(queryWrapper);
		return count == 0 ? save(entity) : update(entity, queryWrapper);
	}

	@Override
	public GlNamespace getByPrimaryKey(Long id, String host) {
		QueryWrapper<GlNamespace> queryWrapper = new QueryWrapper<GlNamespace>()
			//
			.eq("`host`", host)
			//
			.eq("id", id);
		return getOne(queryWrapper);
	}

	@Override
	public boolean updateBatch(List<GlNamespace> glNamespaceList) {
		for (GlNamespace glNamespace : glNamespaceList) {
			UpdateWrapper<GlNamespace> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("`host`", glNamespace.getHost());
			updateWrapper.eq("id", glNamespace.getId());
			update(glNamespace, updateWrapper);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Namespace> listNamespaces(GlBO glBO) throws GitLabApiException {
		List<Namespace> namespaces;
		try (GitLabApi gitLabApi = new GitLabApi(glBO.getHostUrl(), glBO.getPersonalAccessToken())) {
			gitLabApi.setIgnoreCertificateErrors(true);
			namespaces = gitLabApi.getNamespaceApi().getNamespaces();
		}
		catch (GitLabApiException e) {
			log.error("列出命名空间异常：", e);
			throw e;
		}

		URL url;
		String hostUrl = glBO.getHostUrl();
		try {
			url = new URL(hostUrl);
		}
		catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		String host = url.getHost();

		List<GlNamespace> glNamespaceSaveList = new ArrayList<>();
		List<GlNamespace> glNamespaceUpdateList = new ArrayList<>();
		for (Namespace namespace : namespaces) {

			Long id = namespace.getId();
			GlNamespace glNamespace = new GlNamespace();
			glNamespace.setId(id);
			glNamespace.setHost(host);
			glNamespace.setName(namespace.getName());
			glNamespace.setPath(namespace.getPath());
			glNamespace.setKind(namespace.getKind());
			glNamespace.setFullPath(namespace.getFullPath());
			glNamespace.setAvatarUrl(namespace.getAvatarUrl());
			glNamespace.setWebUrl(namespace.getWebUrl());

			GlNamespace byPrimaryKey = getByPrimaryKey(id, host);
			if (byPrimaryKey == null) {
				glNamespaceSaveList.add(glNamespace);
			}
			else {
				glNamespaceUpdateList.add(glNamespace);
			}
		}

		saveBatch(glNamespaceSaveList);
		updateBatch(glNamespaceUpdateList);

		return namespaces;
	}

}
