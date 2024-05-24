package cn.com.xuxiaowei.gitbot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Git 机器人 API", description = "", version = "v0.0.1"))
// @formatter:off
@SecuritySchemes(value = {
	@SecurityScheme(name = "oauth2_clientCredentials", type = SecuritySchemeType.OAUTH2, flows =
		@OAuthFlows(clientCredentials =
			@OAuthFlow(tokenUrl = "${springdoc.oAuthFlow.tokenUrl}", scopes = {
				@OAuthScope(name = "openid", description = "openid"),
				@OAuthScope(name = "profile", description = "profile"),
				@OAuthScope(name = "message.read", description = "message read"),
				@OAuthScope(name = "message.write", description = "message write")
			})
		)
	)
})
// @formatter:on
public class SpringdocConfig {

}
