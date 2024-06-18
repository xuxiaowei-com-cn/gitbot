package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.constant.LogConstants;
import cn.com.xuxiaowei.gitbot.constant.RedisKeyConstants;
import cn.com.xuxiaowei.gitbot.entity.GlNamespace;
import cn.com.xuxiaowei.gitbot.mapper.GlNamespaceMapper;
import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import cn.com.xuxiaowei.gitbot.service.IGlNamespaceService;
import cn.com.xuxiaowei.gitbot.utils.DateUtils;
import cn.com.xuxiaowei.gitbot.utils.RedisKeyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Namespace;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

	private GitbotProperties gitbotProperties;

	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	public void setGitbotProperties(GitbotProperties gitbotProperties) {
		this.gitbotProperties = gitbotProperties;
	}

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 保存 GitLab 命名空间
	 * @param hostUrl
	 * @param ignoreCertificateErrors
	 * @param personalAccessToken
	 * @throws GitLabApiException
	 * @throws MalformedURLException
	 */
	@Override
	public void saveNamespace(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken)
			throws GitLabApiException, MalformedURLException {

		URL url = new URL(hostUrl);
		String host = url.getHost();

		String id = MDC.get(LogConstants.G_REQUEST_ID);
		if (!StringUtils.hasText(id)) {
			id = UUID.randomUUID().toString();
		}

		Long dataTimeout = gitbotProperties.getDataTimeout();
		TimeUnit dataUnit = gitbotProperties.getDataUnit();
		String saveNamespaceRedisKeyPrefix = gitbotProperties.getSaveNamespaceRedisKeyPrefix();
		String saveNamespaceRedisHashKey = RedisKeyUtils.hash(saveNamespaceRedisKeyPrefix, host, id);

		// @formatter:off
		stringRedisTemplate.opsForHash().put(saveNamespaceRedisHashKey, RedisKeyConstants.START_AT, DateUtils.format(LocalDateTime.now()));
		// @formatter:on

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
					// @formatter:off
					stringRedisTemplate.opsForHash().increment(saveNamespaceRedisHashKey, RedisKeyConstants.SAVE, 1);
					// @formatter:on
				}
				else {
					update(glNamespace, queryWrapper);
					// @formatter:off
					stringRedisTemplate.opsForHash().increment(saveNamespaceRedisHashKey, RedisKeyConstants.UPDATE, 1);
					// @formatter:on
				}
			}

		}
		catch (GitLabApiException e) {
			log.error("保存 GitLab 命名空间，调用接口时异常：", e);
			String stackTrace = ExceptionUtils.getStackTrace(e);
			// @formatter:off
			stringRedisTemplate.opsForHash().put(saveNamespaceRedisHashKey, RedisKeyConstants.EXCEPTION_AT, DateUtils.format(LocalDateTime.now()));
			stringRedisTemplate.opsForHash().put(saveNamespaceRedisHashKey, RedisKeyConstants.EXCEPTION_STACK_TRACE, stackTrace);
			// @formatter:on
			throw e;
		}
		catch (Exception e) {
			log.error("保存 GitLab 命名空间 时异常：", e);
			String stackTrace = ExceptionUtils.getStackTrace(e);
			// @formatter:off
			stringRedisTemplate.opsForHash().put(saveNamespaceRedisHashKey, RedisKeyConstants.EXCEPTION_AT, DateUtils.format(LocalDateTime.now()));
			stringRedisTemplate.opsForHash().put(saveNamespaceRedisHashKey, RedisKeyConstants.EXCEPTION_STACK_TRACE, stackTrace);
			// @formatter:on
			throw e;
		}
		finally {
			stringRedisTemplate.expire(saveNamespaceRedisHashKey, dataTimeout, dataUnit);

			// @formatter:off
			log.debug("saved: {}", stringRedisTemplate.opsForHash().get(saveNamespaceRedisHashKey, RedisKeyConstants.SAVE));
			log.debug("updated: {}", stringRedisTemplate.opsForHash().get(saveNamespaceRedisHashKey, RedisKeyConstants.UPDATE));
			// @formatter:on
		}

		// @formatter:off
		stringRedisTemplate.opsForHash().put(saveNamespaceRedisHashKey, RedisKeyConstants.END_AT, DateUtils.format(LocalDateTime.now()));
		// @formatter:on
	}

}
