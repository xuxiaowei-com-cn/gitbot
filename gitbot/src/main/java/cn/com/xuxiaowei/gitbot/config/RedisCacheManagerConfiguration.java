package cn.com.xuxiaowei.gitbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 缓存管理器 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class RedisCacheManagerConfiguration {

	@Bean
	public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

		RedisTemplate<String, ?> template = new RedisTemplate<>();

		template.setConnectionFactory(redisConnectionFactory);

		Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(jackson2JsonRedisSerializer);

		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(jackson2JsonRedisSerializer);

		template.afterPropertiesSet();

		return template;
	}

}
