package cn.com.xuxiaowei.gitbot.controller;

import cn.com.xuxiaowei.gitbot.bo.SaveOrganizationBO;
import cn.com.xuxiaowei.gitbot.service.IGhOrganizationService;
import cn.com.xuxiaowei.gitbot.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-14
 */
@RestController
@RequestMapping("/gh/organization")
public class GhOrganizationRestController {

	private IGhOrganizationService ghOrganizationService;

	@Autowired
	public void setGhOrganizationService(IGhOrganizationService ghOrganizationService) {
		this.ghOrganizationService = ghOrganizationService;
	}

	/**
	 * 保存组织
	 * @param request 请求
	 * @param response 响应
	 * @param saveOrganizationBO 保存组织 参数
	 * @return 返回 保存结果
	 */
	@Operation(summary = "保存组织")
	@PostMapping("/save")
	public Response<?> save(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody SaveOrganizationBO saveOrganizationBO) throws GitLabApiException, IOException {
		ghOrganizationService.saveMyOrganizations(saveOrganizationBO.getOauthToken());
		return Response.ok();
	}

}
