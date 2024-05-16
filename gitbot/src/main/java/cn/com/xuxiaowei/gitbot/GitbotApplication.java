package cn.com.xuxiaowei.gitbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({ "cn.com.xuxiaowei.gitbot.mapper" })
public class GitbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitbotApplication.class, args);
	}

}
