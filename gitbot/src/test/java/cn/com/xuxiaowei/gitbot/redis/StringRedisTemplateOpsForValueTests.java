package cn.com.xuxiaowei.gitbot.redis;

import cn.com.xuxiaowei.gitbot.constant.RedisConstants;
import cn.com.xuxiaowei.gitbot.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link StringRedisTemplate} 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
public class StringRedisTemplateOpsForValueTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	void start() {
		// 产生一个随机 key
		String key = RandomStringUtils.randomAlphabetic(4);

		// 获取 Redis 中 key 的 value
		String value = stringRedisTemplate.opsForValue().get(key);
		// value 为空
		assertNull(value);

		// 产生一个随机数
		String uuid = UUID.randomUUID().toString();
		// 使用 key 将 随机数 保存到 Redis 中
		// 此处也可设置过期时间，如果不设置，则永不过期
		stringRedisTemplate.opsForValue().set(key, uuid);

		// 获取 Redis 中 key 的 value
		value = stringRedisTemplate.opsForValue().get(key);
		// Redis 中 key 的 value 与 随机数相同
		assertEquals(value, uuid);

		// 获取过期时间
		Long time = stringRedisTemplate.getExpire(key);
		// 未设置过期时间，值为 -1
		assertEquals(time, -1);

		// 定义过期时间
		long timeout = 80;
		TimeUnit unit = TimeUnit.SECONDS;

		// 设置过期时间
		Boolean expire = stringRedisTemplate.expire(key, timeout, unit);
		// 设置结果
		assertNotNull(expire);
		assertTrue(expire);

		// 获取 Redis 中 key 的过期时间
		time = stringRedisTemplate.getExpire(key);

		// 比较过期时间
		assertEquals(timeout, time);

		String redisVersion = RedisUtils.redisVersion(stringRedisTemplate);
		assertNotNull(redisVersion);

		int compare = StringUtils.compare(redisVersion, RedisConstants.GETEX_VERSION);
		if (compare < 0) {
			log.warn("警告：Redis 版本低于 {}，不支持 GETEX（getAndExpire）命令", RedisConstants.GETEX_VERSION);
		}
		else {
			// 定义过期时间
			timeout = 100;

			// 获取并设置新的过期时间
			// 此方法仅支持 Redis 6.2.0 及之后的版本，之前的版本会出现错误：
			// Caused by: io.lettuce.core.RedisCommandExecutionException: ERR unknown
			// command `GETEX`, with args beginning with: `RPex`, `EX`, `100`,
			String v = stringRedisTemplate.opsForValue().getAndExpire(key, timeout, unit);
			// Redis 中 key 的 value 与 随机数相同
			assertEquals(v, uuid);

			// 获取 Redis 中 key 的过期时间
			time = stringRedisTemplate.getExpire(key);

			// 比较过期时间
			assertEquals(timeout, time);
		}

		if (compare < 0) {
			log.warn("警告：Redis 版本低于 {}，不支持 GETEX（getAndPersist）命令", RedisConstants.GETEX_VERSION);
		}
		else {
			// 获取 Redis 中 key 的 value，并进行保持（删除与此 key 关联的任何 TTL）
			value = stringRedisTemplate.opsForValue().getAndPersist(key);
			// Redis 中 key 的 value 与 随机数相同
			assertEquals(value, uuid);
		}

		// 获取 Redis 中 key 的 value，并设置为新的值
		value = stringRedisTemplate.opsForValue().getAndSet(key, UUID.randomUUID().toString());
		// 获取 Redis 中 key 的 value 与 随机数相同
		assertEquals(value, uuid);
		// 再次获取 Redis 中 key 的 value 与 随机数不相同
		value = stringRedisTemplate.opsForValue().get(key);
		assertNotEquals(value, uuid);

		// 获取过期时间
		time = stringRedisTemplate.getExpire(key);
		// 未设置过期时间，值为 -1
		assertEquals(time, -1);

		// 删除 Redis 中的 key
		Boolean delete = stringRedisTemplate.delete(key);
		// 删除结果
		assertNotNull(delete);
		assertTrue(delete);

		// 获取 Redis 中 key 的 value
		value = stringRedisTemplate.opsForValue().get(key);
		// 结果为 null
		assertNull(value);

		// 自增 1
		String numKey = RandomStringUtils.randomAlphabetic(4);
		int i = 5;
		stringRedisTemplate.opsForValue().set(numKey, i + "");
		String numStr = stringRedisTemplate.opsForValue().get(numKey);
		assertNotNull(numStr);
		int i1 = Integer.parseInt(numStr);
		assertEquals(i1, i);
		Long i2 = stringRedisTemplate.opsForValue().increment(numKey);
		assertNotNull(i2);
		assertEquals(i2, i + 1);

		// 计算
		int i3 = 6;
		Long i4 = stringRedisTemplate.opsForValue().increment(numKey, i3);
		assertNotNull(i4);
		assertEquals(i4, i2 + i3);

		// 计算后的方法的返回值 和 重新查询 Redis 的值是相同的
		String string = stringRedisTemplate.opsForValue().get(numKey);
		assertNotNull(string);
		int i5 = Integer.parseInt(string);
		assertEquals(i4, i5);
	}

	@Test
	void getAndDelete() {
		String key = RandomStringUtils.randomAlphabetic(4);

		String uuid = UUID.randomUUID().toString();
		stringRedisTemplate.opsForValue().set(key, uuid);

		String redisVersion = RedisUtils.redisVersion(stringRedisTemplate);
		assertNotNull(redisVersion);
		int compare = StringUtils.compare(redisVersion, RedisConstants.GETDEL_VERSION);

		if (compare < 0) {
			log.warn("警告：Redis 版本低于 {}，不支持 GETDEL（getAndDelete） 命令", RedisConstants.GETDEL_VERSION);
		}
		else {
			// 获取 Redis 中 key 的 value，并删除 Redis 中的 key
			// Caused by: io.lettuce.core.RedisCommandExecutionException: ERR unknown
			// command `GETDEL`, with args beginning with: `QfMW`,
			String value = stringRedisTemplate.opsForValue().getAndDelete(key);

			assertEquals(value, uuid);

			// 获取 Redis 中 key 的 value
			value = stringRedisTemplate.opsForValue().get(key);
			// value 为空
			assertNull(value);
		}

	}

}
