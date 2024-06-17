package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.bo.GlProjectBO;
import cn.com.xuxiaowei.gitbot.bo.SaveProjectBO;
import cn.com.xuxiaowei.gitbot.entity.GlProject;
import cn.com.xuxiaowei.gitbot.service.IGlProjectService;
import cn.com.xuxiaowei.gitbot.utils.Response;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

/**
 * <p>
 * GitLab 项目 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Slf4j
@Tag(name = "GitLab 项目")
@SecurityRequirement(name = "oauth2_clientCredentials")
@RestController
@RequestMapping("/gl/project")
public class GlProjectRestController {

	private IGlProjectService glProjectService;

	@Autowired
	public void setGlProjectService(IGlProjectService glProjectService) {
		this.glProjectService = glProjectService;
	}

	/**
	 * GitLab 项目列表
	 * @param glProjectBO 项目列表参数
	 */
	@Operation(summary = "GitLab 项目列表")
	@GetMapping
	public Response<Page<GlProject>> list(HttpServletRequest request, HttpServletResponse response,
			GlProjectBO glProjectBO) {

		Page<GlProject> page = glProjectService.pageByGlProjectBO(glProjectBO);

		Response<Page<GlProject>> ok = Response.ok();
		return ok.setData(page);
	}

	/**
	 * 保存项目
	 * @param request 请求
	 * @param response 响应
	 * @param saveProjectBO 保存项目 参数
	 * @return 返回 保存结果
	 */
	@Operation(summary = "保存项目", description = "官方文档：https://docs.gitlab.cn/jh/api/projects.html")
	@PostMapping
	public Response<?> save(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody SaveProjectBO saveProjectBO) throws GitLabApiException, MalformedURLException {
		glProjectService.saveOwnedProject(saveProjectBO.getHostUrl(), saveProjectBO.isIgnoreCertificateErrors(),
				saveProjectBO.getPersonalAccessToken());
		return Response.ok();
	}

}
