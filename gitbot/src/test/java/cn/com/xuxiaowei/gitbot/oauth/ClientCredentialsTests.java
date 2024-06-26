package cn.com.xuxiaowei.gitbot.oauth;

import cn.com.xuxiaowei.gitbot.GitbotApplicationTests;
import cn.com.xuxiaowei.gitbot.constant.OAuth2Constants;
import cn.com.xuxiaowei.gitbot.utils.Base64Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * OAuth 2.1 凭证式 自动化 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientCredentialsTests {

	@LocalServerPort
	private int serverPort;

	@Test
	void start() throws JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.setBasicAuth(GitbotApplicationTests.CLIENT_ID, GitbotApplicationTests.CLIENT_SECRET);
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		// @formatter:off
		requestBody.put(OAuth2ParameterNames.GRANT_TYPE, Collections.singletonList(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue()));
		requestBody.put(OAuth2ParameterNames.SCOPE, Collections.singletonList("openid profile message.read message.write"));
		// @formatter:on
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

		Map map = restTemplate.postForObject(String.format("http://127.0.0.1:%d/oauth2/token", serverPort), httpEntity,
				Map.class);

		assertNotNull(map);

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

		log.info("token:\n{}", objectWriter.writeValueAsString(map));

		assertNotNull(map.get(OAuth2ParameterNames.ACCESS_TOKEN));
		assertNotNull(map.get(OAuth2ParameterNames.SCOPE));
		assertNotNull(map.get(OAuth2ParameterNames.TOKEN_TYPE));
		assertNotNull(map.get(OAuth2ParameterNames.EXPIRES_IN));

		String accessToken = map.get(OAuth2ParameterNames.ACCESS_TOKEN).toString();

		String[] split = accessToken.split("\\.");
		assertEquals(split.length, 3);

		String payloadEncode = split[1];

		String payloadDecode = Base64Utils.decodeStr(payloadEncode);

		Map payload = objectMapper.readValue(payloadDecode, Map.class);

		log.info("payload:\n{}", objectWriter.writeValueAsString(payload));

		assertNotNull(payload.get(OAuth2TokenIntrospectionClaimNames.SUB));
		assertNotNull(payload.get(OAuth2TokenIntrospectionClaimNames.AUD));
		assertNotNull(payload.get(OAuth2TokenIntrospectionClaimNames.NBF));
		assertNotNull(payload.get(OAuth2TokenIntrospectionClaimNames.SCOPE));
		assertNotNull(payload.get(OAuth2TokenIntrospectionClaimNames.ISS));
		assertNotNull(payload.get(OAuth2TokenIntrospectionClaimNames.EXP));
		assertNotNull(payload.get(OAuth2TokenIntrospectionClaimNames.IAT));
		assertNotNull(payload.get(OAuth2Constants.AUTHORITIES_CLAIM_NAME));
		// @formatter:off
		assertEquals(payload.get(OAuth2Constants.AUTHORITIES_CLAIM_NAME), payload.get(OAuth2TokenIntrospectionClaimNames.SCOPE));
		// @formatter:on

		// 凭证式模式：
		// sub：代表用户名，由于凭证式是自己给自己授权，所以 sub 和 aud 相同，都是 客户ID
		// aud：代表客户ID
		assertEquals(payload.get(OAuth2TokenIntrospectionClaimNames.SUB), GitbotApplicationTests.CLIENT_ID);
		assertEquals(payload.get(OAuth2TokenIntrospectionClaimNames.AUD), GitbotApplicationTests.CLIENT_ID);

	}

}
