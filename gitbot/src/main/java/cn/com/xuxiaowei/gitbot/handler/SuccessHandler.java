package cn.com.xuxiaowei.gitbot.handler;

import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.UUID;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

	private GitbotProperties gitbotProperties;

	@Autowired
	public void setGitbotProperties(GitbotProperties gitbotProperties) {
		this.gitbotProperties = gitbotProperties;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String authorizeClientId = gitbotProperties.getAuthorizeClientId();
		String authorizeScope = gitbotProperties.getAuthorizeScope();

		StringBuffer requestUrl = request.getRequestURL();

		UriComponentsBuilder requestUriBuilder = UriComponentsBuilder.fromUriString(requestUrl.toString());
		UriComponentsBuilder redirectUriBuilder = UriComponentsBuilder.fromUriString(requestUrl.toString());
		String redirectUri = redirectUriBuilder.replacePath("/login/code").build().toString();

		String state = UUID.randomUUID().toString();

		String authorizeUrl = requestUriBuilder.replacePath("/oauth2/authorize")
			.queryParam("client_id", authorizeClientId)
			.queryParam("redirect_uri", redirectUri)
			.queryParam("response_type", "code")
			.queryParam("scope", authorizeScope)
			.queryParam("state", state)
			.build()
			.toString();

		response.sendRedirect(authorizeUrl);
	}

}
