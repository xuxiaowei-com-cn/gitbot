package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.vo.InstanceVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 实例 接口 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class InstanceServiceTests {

	@Autowired
	private InstanceService instanceService;

	/**
	 * 实例列表
	 */
	@Test
	void list() {
		List<InstanceVO> list = instanceService.list();
		for (InstanceVO instanceVO : list) {
			log.info(String.valueOf(instanceVO));
		}
	}

}
