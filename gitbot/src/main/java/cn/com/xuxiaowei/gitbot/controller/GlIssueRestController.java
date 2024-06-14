package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.bo.SaveIssueBO;
import cn.com.xuxiaowei.gitbot.service.IGlIssueService;
import cn.com.xuxiaowei.gitbot.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Slf4j
@Tag(name = "GitLab 议题")
@SecurityRequirement(name = "oauth2_clientCredentials")
@RestController
@RequestMapping("/gl/issue")
public class GlIssueRestController {

	private IGlIssueService glIssueService;

	@Autowired
	public void setGlIssueService(IGlIssueService glIssueService) {
		this.glIssueService = glIssueService;
	}

	/**
	 * 保存议题
	 * @param request 请求
	 * @param response 响应
	 * @param saveIssueBO 保存议题 参数
	 * @return 返回 保存结果
	 */
	@Operation(summary = "保存议题", description = "官方文档：https://docs.gitlab.cn/jh/api/issues.html")
	@PostMapping("/save")
	public Response<?> save(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody SaveIssueBO saveIssueBO) throws GitLabApiException, MalformedURLException {
		glIssueService.saveIssue(saveIssueBO.getHostUrl(), saveIssueBO.isIgnoreCertificateErrors(),
				saveIssueBO.getPersonalAccessToken());
		return Response.ok();
	}

}
