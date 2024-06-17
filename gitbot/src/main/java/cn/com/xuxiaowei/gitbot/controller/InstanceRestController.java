package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.service.InstanceService;
import cn.com.xuxiaowei.gitbot.utils.Response;
import cn.com.xuxiaowei.gitbot.vo.InstanceVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实例
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Tag(name = "实例")
@SecurityRequirement(name = "oauth2_clientCredentials")
@RestController
@RequestMapping("/instance")
public class InstanceRestController {

	private InstanceService instanceService;

	@Autowired
	public void setInstanceService(InstanceService instanceService) {
		this.instanceService = instanceService;
	}

	/**
	 * 实例列表
	 */
	@Operation(summary = "实例列表")
	@GetMapping
	public Response<List<InstanceVO>> list(HttpServletRequest request, HttpServletResponse response) {

		List<InstanceVO> list = instanceService.list();

		Response<List<InstanceVO>> ok = Response.ok();
		return ok.setData(list);
	}

	// 查询 GET
	// 添加 POST
	// 修改 PUT 更新资源的全部内容
	// 修改 PATCH 更新资源的部分内容
	// 删除 DELETE

}
