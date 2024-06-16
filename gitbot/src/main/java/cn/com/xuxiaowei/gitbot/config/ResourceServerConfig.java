package cn.com.xuxiaowei.gitbot.config;

import cn.com.xuxiaowei.gitbot.handler.SuccessHandler;
import cn.com.xuxiaowei.gitbot.properties.GitbotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.security.interfaces.RSAPublicKey;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class ResourceServerConfig {

	private GitbotProperties gitbotProperties;

	private SuccessHandler successHandler;

	@Autowired
	public void setGitbotProperties(GitbotProperties gitbotProperties) {
		this.gitbotProperties = gitbotProperties;
	}

	@Autowired
	public void setSuccessHandler(SuccessHandler successHandler) {
		this.successHandler = successHandler;
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcOperations jdbcOperations) {
		return new JdbcRegisteredClientRepository(jdbcOperations);
	}

	@Bean
	public OAuth2AuthorizationService authorizationService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
	}

	@Bean
	public OAuth2AuthorizationConsentService authorizationConsentService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
	}

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(customizer -> {

			customizer.requestMatchers("/error").permitAll();

			customizer.requestMatchers("/favicon.ico").permitAll();

			customizer.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();

			// 其他地址：需要授权访问，此配置要放在最后一行
			customizer.anyRequest().authenticated();

		});

		http.formLogin(customizer -> {
			customizer.successHandler(successHandler);
		});

		http.cors(customizer -> {

			CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowedOrigins(gitbotProperties.getAllowedOrigins());
			configuration.setAllowedMethods(gitbotProperties.getAllowedMethods());
			configuration.setAllowedHeaders(gitbotProperties.getAllowedHeaders());
			configuration.setAllowCredentials(gitbotProperties.getAllowCredentials());
			configuration.setMaxAge(gitbotProperties.getMaxAge());

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration(gitbotProperties.getPattern(), configuration);

			customizer.configurationSource(source);
		});

		http.oauth2ResourceServer(customizer -> {
			customizer.jwt(jwtCustomizer -> {
				RSAPublicKey rsaPublicKey = gitbotProperties.rsaPublicKey();
				NimbusJwtDecoder.PublicKeyJwtDecoderBuilder publicKeyJwtDecoderBuilder = NimbusJwtDecoder
					.withPublicKey(rsaPublicKey);
				NimbusJwtDecoder nimbusJwtDecoder = publicKeyJwtDecoderBuilder.build();
				jwtCustomizer.decoder(nimbusJwtDecoder);
			});
		});

		return http.build();
	}

	@Bean
	public JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}

}
