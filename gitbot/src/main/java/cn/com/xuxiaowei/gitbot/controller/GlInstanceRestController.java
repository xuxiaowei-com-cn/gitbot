package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.service.GlInstanceService;
import cn.com.xuxiaowei.gitbot.utils.Response;
import cn.com.xuxiaowei.gitbot.vo.GlInstanceVO;
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
 * GitLab 实例
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Tag(name = "GitLab 实例")
@SecurityRequirement(name = "oauth2_clientCredentials")
@RestController
@RequestMapping("/gl/instance")
public class GlInstanceRestController {

	private GlInstanceService glInstanceService;

	@Autowired
	public void setGlInstanceService(GlInstanceService glInstanceService) {
		this.glInstanceService = glInstanceService;
	}

	/**
	 * GitLab 实例列表
	 */
	@Operation(summary = "GitLab 实例列表")
	@GetMapping
	public Response<List<GlInstanceVO>> list(HttpServletRequest request, HttpServletResponse response) {

		List<GlInstanceVO> list = glInstanceService.list();

		Response<List<GlInstanceVO>> ok = Response.ok();
		return ok.setData(list);
	}

}
