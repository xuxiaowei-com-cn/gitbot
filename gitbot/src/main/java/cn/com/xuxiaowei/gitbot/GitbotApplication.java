package cn.com.xuxiaowei.gitbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 程序执行入口
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@EnableScheduling
@SpringBootApplication
public class GitbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitbotApplication.class, args);
	}

}
