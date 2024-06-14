package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlVariable;
import cn.com.xuxiaowei.gitbot.mapper.GlVariableMapper;
import cn.com.xuxiaowei.gitbot.service.IGlVariableService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Variable;
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
public class GlVariableServiceImpl extends ServiceImpl<GlVariableMapper, GlVariable> implements IGlVariableService {

	@Override
	public void saveVariable(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken,
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

			List<Variable> variables = gitLabApi.getProjectApi().getVariables(projectIdOrPath);
			for (Variable variable : variables) {

				GlVariable glVariable = new GlVariable();
				glVariable.setHost(host);
				glVariable.setProjectId(projectId);
				glVariable.setKey(variable.getKey());
				glVariable.setEnvironmentScope(variable.getEnvironmentScope());
				glVariable.setValue(variable.getValue());
				glVariable.setVariableType(variable.getVariableType().toString());
				glVariable.setIsProtected(variable.getProtected());
				glVariable.setMasked(variable.getMasked());

				QueryWrapper<GlVariable> queryWrapper = new QueryWrapper<GlVariable>()
					//
					.eq("`host`", glVariable.getHost())
					//
					.eq("project_id", glVariable.getProjectId())
					//
					.eq("`key`", glVariable.getKey())
					//
					.eq("environment_scope", glVariable.getEnvironmentScope());
				long count = count(queryWrapper);
				if (count == 0) {
					save(glVariable);
					saved++;
				}
				else {
					update(glVariable, queryWrapper);
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
