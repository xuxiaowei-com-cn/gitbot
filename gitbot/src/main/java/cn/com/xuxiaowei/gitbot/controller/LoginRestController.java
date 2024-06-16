package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import cn.com.xuxiaowei.gitbot.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Tag(name = "登录接口", description = "登录相关接口")
@SecurityRequirement(name = "oauth2_clientCredentials")
@RestController
@RequestMapping("/login")
public class LoginRestController {

	private StringRedisTemplate stringRedisTemplate;

	private GitbotProperties gitbotProperties;

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
	 */
	@PostMapping("/success")
	@Operation(summary = "登录成功", description = "返回 OAuth 2.1 请求授权地址")
	public Response<String> success(HttpServletRequest request, HttpServletResponse response) {
		String authorizeUrl = "/oauth2/authorize";
		String clientId = gitbotProperties.getAuthorizeClientId();
		String redirectUri = gitbotProperties.getAuthorizeRedirectUri();
		String scope = gitbotProperties.getAuthorizeScope();
		String state = RandomStringUtils.randomAlphanumeric(32);

		String url = UriComponentsBuilder.newInstance()
			.path(authorizeUrl)
			.queryParam(OAuth2ParameterNames.CLIENT_ID, clientId)
			.queryParam(OAuth2ParameterNames.REDIRECT_URI, redirectUri)
			.queryParam(OAuth2ParameterNames.RESPONSE_TYPE, OAuth2AuthorizationResponseType.CODE.getValue())
			.queryParam(OAuth2ParameterNames.SCOPE, scope)
			.queryParam(OAuth2ParameterNames.STATE, state)
			.toUriString();

		Response<String> ok = Response.ok("登录成功");
		ok.setData(url);
		return ok;
	}

	/**
	 * @param request 请求
	 * @param response 响应
	 * @param ticket 票据
	 */
	@GetMapping("/ticket")
	@Operation(summary = "兑换票据", description = "使用票据兑换Token，一个票据只能使用一次，后期考虑加密此接口")
	public Response<String> ticket(HttpServletRequest request, HttpServletResponse response, String ticket) {

		String ticketPrefix = gitbotProperties.getTicketPrefix();
		String redisTicket = ticketPrefix + ticket;

		// 赎回次数
		Long redemption = stringRedisTemplate.opsForHash().increment(redisTicket, "redemption", 1);

		if (redemption == 1) {
			// Map<Object, Object> entries =
			// stringRedisTemplate.opsForHash().entries(redisTicket);
			Object accessToken = stringRedisTemplate.opsForHash().get(redisTicket, OAuth2ParameterNames.ACCESS_TOKEN);

			if (accessToken == null) {
				return Response.error("非法兑换票据，该行为已被记录");
			}

			Response<String> ok = Response.ok("票据兑换成功");
			ok.setData(accessToken.toString());
			return ok;
		}
		else {
			return Response.error("非法兑换票据，该行为已被记录");
		}
	}

	/**
	 * @param request 请求
	 * @param response 响应
	 */
	@PostMapping("/failure")
	@Operation(summary = "登录失败", description = "")
	public Response<String> failure(HttpServletRequest request, HttpServletResponse response) {
		return Response.error("登录失败");
	}

}
