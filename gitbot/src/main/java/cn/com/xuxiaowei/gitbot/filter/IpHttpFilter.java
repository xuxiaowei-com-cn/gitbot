package cn.com.xuxiaowei.gitbot.filter;

import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class IpHttpFilter extends HttpFilter {

	private static final List<String> INTERNAL = Arrays.asList("192.168.0.0/16", "172.16.0.0/12", "10.0.0.0/8",
			"127.0.0.1");

	private RedisTemplate<String, List<String>> redisTemplate;

	private GitbotProperties gitbotProperties;

	@Autowired
	public void setRedisTemplate(RedisTemplate<String, List<String>> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Autowired
	public void setGitbotProperties(GitbotProperties gitbotProperties) {
		this.gitbotProperties = gitbotProperties;
	}

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String remoteHost = req.getRemoteHost();

		log.info("用户 remoteHost: {}", remoteHost);

		String allowIpRedisKey = gitbotProperties.getAllowIpRedisKey();

		boolean allow;

		if (StringUtils.hasText(allowIpRedisKey)) {
			List<String> allowIps = redisTemplate.opsForValue().get(allowIpRedisKey);

			if (allowIps == null || allowIps.isEmpty()) {
				allow = allowIpIsNull(allowIpRedisKey, remoteHost);
			}
			else {
				allow = allow(allowIps, remoteHost);
			}
		}
		else {
			allow = allowIpIsNull(allowIpRedisKey, remoteHost);
		}

		if (!allow) {
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		super.doFilter(req, res, chain);
	}

	private boolean allowIpIsNull(String allowIpRedisKey, String remoteHost) {
		log.error("未配置：允许 IP 访问的 Redis Key/数据");
		log.warn("仅限内网访问");

		redisTemplate.opsForValue().set(allowIpRedisKey, INTERNAL);

		return allow(INTERNAL, remoteHost);
	}

	private boolean allow(List<String> allowIps, String remoteHost) {
		for (String allowIp : allowIps) {
			IpAddressMatcher matcher = new IpAddressMatcher(allowIp);
			if (matcher.matches(remoteHost)) {
				return true;
			}
		}
		return false;
	}

}
