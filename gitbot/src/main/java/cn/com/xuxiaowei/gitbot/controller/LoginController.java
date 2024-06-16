package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import cn.com.xuxiaowei.gitbot.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Tag(name = "登录页面", description = "登录相关页面")
@SecurityRequirement(name = "oauth2_clientCredentials")
@Controller
@RequestMapping("/login")
public class LoginController {

	private RegisteredClientRepository registeredClientRepository;

	private StringRedisTemplate stringRedisTemplate;

	private GitbotProperties gitbotProperties;

	@Autowired
	public void setRegisteredClientRepository(RegisteredClientRepository registeredClientRepository) {
		this.registeredClientRepository = registeredClientRepository;
	}

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Autowired
	public void setGitbotProperties(GitbotProperties gitbotProperties) {
		this.gitbotProperties = gitbotProperties;
	}

	/**
	 * @param request 请求
	 * @param response 响应
	 * @param code 授权码
	 * @param state 状态码
	 */
	@SneakyThrows
	@GetMapping("/code")
	@Operation(summary = "接收授权码", description = "使用授权码获取 Token")
	public void code(HttpServletRequest request, HttpServletResponse response, String code, String state) {

		String clientId = gitbotProperties.getAuthorizeClientId();
		String clientSecret = gitbotProperties.getAuthorizeClientSecret();
		String redirectUri = gitbotProperties.getAuthorizeRedirectUri();
		String tokenUrl = gitbotProperties.getTokenUrl();
		String successUrl = gitbotProperties.getAuthorizeSuccessUrl();
		String ticketPrefix = gitbotProperties.getTicketPrefix();

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.setBasicAuth(clientId, clientSecret);
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.put(OAuth2ParameterNames.CODE, Collections.singletonList(code));
		requestBody.put(OAuth2ParameterNames.GRANT_TYPE,
				Collections.singletonList(AuthorizationGrantType.AUTHORIZATION_CODE.getValue()));
		requestBody.put(OAuth2ParameterNames.REDIRECT_URI, Collections.singletonList(redirectUri));
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

		Map map = restTemplate.postForObject(tokenUrl, httpEntity, Map.class);

		String ticket = RandomStringUtils.randomAlphanumeric(32);
		String redisTicket = ticketPrefix + ticket;

		RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);

		TokenSettings tokenSettings = registeredClient.getTokenSettings();
		Duration accessTokenTimeToLive = tokenSettings.getAccessTokenTimeToLive();
		Duration refreshTokenTimeToLive = tokenSettings.getRefreshTokenTimeToLive();

		Duration duration = Stream.of(accessTokenTimeToLive, refreshTokenTimeToLive)
			.max(Duration::compareTo)
			.orElse(Duration.ZERO);

		LocalDateTime now = LocalDateTime.now();
		int expiresIn = Integer.parseInt(map.get(OAuth2ParameterNames.EXPIRES_IN).toString());
		LocalDateTime expiresInLocalDateTime = now.plusSeconds(expiresIn);
		map.put(OAuth2ParameterNames.EXPIRES_IN, DateUtils.format(expiresInLocalDateTime));

		stringRedisTemplate.opsForHash().putAll(redisTicket, map);
		stringRedisTemplate.expire(redisTicket, duration);

		response.sendRedirect(String.format("%s?ticket=%s", successUrl, ticket));
	}

}
