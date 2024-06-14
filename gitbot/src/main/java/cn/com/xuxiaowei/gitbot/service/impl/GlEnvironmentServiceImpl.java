package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlEnvironment;
import cn.com.xuxiaowei.gitbot.mapper.GlEnvironmentMapper;
import cn.com.xuxiaowei.gitbot.service.IGlEnvironmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Environment;
import org.gitlab4j.api.models.Project;
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
 * @since 2024-06-14
 */
@Slf4j
@Service
public class GlEnvironmentServiceImpl extends ServiceImpl<GlEnvironmentMapper, GlEnvironment>
		implements IGlEnvironmentService {

	@Override
	public void saveEnvironment(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken,
			Object projectIdOrPath) throws GitLabApiException, MalformedURLException {

		URL url = new URL(hostUrl);
		String host = url.getHost();

		int saved = 0;
		int updated = 0;

		try (GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken)) {
			gitLabApi.setIgnoreCertificateErrors(true);

			long projectId;
			if (projectIdOrPath instanceof Long) {
				projectId = (Long) projectIdOrPath;
			}
			else {
				Project project = gitLabApi.getProjectApi().getProject(projectIdOrPath);
				projectId = project.getId();
			}

			List<Environment> environments = gitLabApi.getEnvironmentsApi().getEnvironments(projectId);
			for (Environment environment : environments) {

				GlEnvironment glEnvironment = new GlEnvironment();
				glEnvironment.setId(environment.getId());
				glEnvironment.setHost(host);
				glEnvironment.setProjectId(projectId);
				glEnvironment.setName(environment.getName());
				glEnvironment.setSlug(environment.getSlug());
				glEnvironment.setExternalUrl(environment.getExternalUrl());
				glEnvironment.setState(environment.getExternalUrl());
				// Deployment lastDeployment = environment.getLastDeployment();
				// glEnvironment.setLastDeployment();

				QueryWrapper<GlEnvironment> queryWrapper = new QueryWrapper<GlEnvironment>()
					//
					.eq("id", glEnvironment.getId())
					//
					.eq("`host`", glEnvironment.getHost())
					//
					.eq("project_id", glEnvironment.getProjectId());
				long count = count(queryWrapper);
				if (count == 0) {
					save(glEnvironment);
					saved++;
				}
				else {
					update(glEnvironment, queryWrapper);
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
