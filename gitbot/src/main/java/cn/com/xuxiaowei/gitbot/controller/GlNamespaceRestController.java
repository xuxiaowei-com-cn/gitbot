package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.bo.GlBO;
import cn.com.xuxiaowei.gitbot.service.IGlNamespaceService;
import cn.com.xuxiaowei.gitbot.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * GitLab 命名空间 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Slf4j
@Tag(name = "GitLab 命名空间")
@SecurityRequirement(name = "oauth2_clientCredentials")
@RestController
@RequestMapping("/gl/namespace")
public class GlNamespaceRestController {

	private IGlNamespaceService glNamespaceService;

	@Autowired
	public void setGlNamespaceService(IGlNamespaceService glNamespaceService) {
		this.glNamespaceService = glNamespaceService;
	}

	/**
	 * 列出命名空间
	 * @param request 请求
	 * @param response 响应
	 * @param glBO GitLab 参数
	 * @return 返回 命名空间
	 */
	@Operation(summary = "列出命名空间", description = "官方文档：https://docs.gitlab.cn/jh/api/namespaces.html")
	@PostMapping("/list")
	public Response<List<Namespace>> list(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody GlBO glBO) throws GitLabApiException {
		List<Namespace> namespaces = glNamespaceService.listNamespaces(glBO);
		Response<List<Namespace>> ok = Response.ok();
		return ok.setData(namespaces);
	}

}