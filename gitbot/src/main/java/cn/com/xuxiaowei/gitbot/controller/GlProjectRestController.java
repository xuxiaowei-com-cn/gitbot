package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.bo.GlBO;
import cn.com.xuxiaowei.gitbot.service.IGlProjectService;
import cn.com.xuxiaowei.gitbot.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
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
	 * 列出项目
	 * @param request 请求
	 * @param response 响应
	 * @param glBO GitLab 参数
	 * @return 返回 项目
	 */
	@Operation(summary = "列出项目", description = "官方文档：https://docs.gitlab.cn/jh/api/projects.html")
	@PostMapping("/list")
	public Response<List<Project>> list(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody GlBO glBO) throws GitLabApiException {
		List<Project> projects = glProjectService.listProjects(glBO);
		Response<List<Project>> ok = Response.ok();
		return ok.setData(projects);
	}

}
