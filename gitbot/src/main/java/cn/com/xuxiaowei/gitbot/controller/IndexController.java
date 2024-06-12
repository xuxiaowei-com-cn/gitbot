package cn.com.xuxiaowei.gitbot.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Controller
public class IndexController {

	@GetMapping("/{path:^(?!favicon.ico)(?!index.html).+}")
	public String index(HttpServletRequest request, HttpServletResponse response, @PathVariable("path") String path) {

		return "forward:index.html";
	}

}
