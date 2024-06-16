package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.bo.SaveMyOrganizationRepositoryBO;
import cn.com.xuxiaowei.gitbot.bo.SaveMyselfRepositoryBO;
import cn.com.xuxiaowei.gitbot.service.IGhRepositoryService;
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

import java.io.IOException;

/**
 * <p>
 * GitHub 仓库 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Slf4j
@Tag(name = "GitHub 仓库")
@SecurityRequirement(name = "oauth2_clientCredentials")
@RestController
@RequestMapping("/gh/repository")
public class GhRepositoryRestController {

	private IGhRepositoryService ghRepositoryService;

	@Autowired
	public void setGhRepositoryService(IGhRepositoryService ghRepositoryService) {
		this.ghRepositoryService = ghRepositoryService;
	}

	/**
	 * 保存组织仓库
	 * @param request 请求
	 * @param response 响应
	 * @param saveRepositoryBO 保存组织仓库 参数
	 * @return 返回 保存结果
	 */
	@Operation(summary = "保存组织仓库")
	@PostMapping("/save-my-organization-repository")
	public Response<?> saveMyOrganizationRepository(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody SaveMyOrganizationRepositoryBO saveRepositoryBO) throws IOException {
		ghRepositoryService.saveMyOrganizationRepository(saveRepositoryBO.getOauthToken());
		return Response.ok();
	}

	/**
	 * 保存自己的仓库
	 * @param request 请求
	 * @param response 响应
	 * @param saveRepositoryBO 保存自己的仓库 参数
	 * @return 返回 保存结果
	 */
	@Operation(summary = "保存自己的仓库")
	@PostMapping("/save-my-repository")
	public Response<?> saveMyselfRepository(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody SaveMyselfRepositoryBO saveRepositoryBO) throws GitLabApiException, IOException {
		ghRepositoryService.saveMyselfRepository(saveRepositoryBO.getOauthToken());
		return Response.ok();
	}

}
