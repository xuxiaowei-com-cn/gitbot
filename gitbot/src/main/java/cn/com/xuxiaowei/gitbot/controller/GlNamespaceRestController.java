package cn.com.xuxiaowei.gitbot.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
