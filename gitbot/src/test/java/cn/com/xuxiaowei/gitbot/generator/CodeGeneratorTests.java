package cn.com.xuxiaowei.gitbot.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class CodeGeneratorTests {

	public static void main(String[] args) {
		// interactiveGenerator();
		// staticGenerator();
	}

	// @Test
	void staticGenerator() {

		String url = "jdbc:mysql://127.0.0.1:3306/gitbot?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8";
		String username = "root";
		String password = "xuxiaowei.com.cn";

		// @formatter:off
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> builder
                        .author("xuxiaowei")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("cn.com.xuxiaowei.gitbot")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
		// @formatter:on
	}

	static void interactiveGenerator() {

		String url = "jdbc:mysql://127.0.0.1:3306/gitbot?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8";
		String username = "root";
		String password = "xuxiaowei.com.cn";

		// @formatter:off
		FastAutoGenerator.create(url, username, password)
				// 全局配置
				.globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")))
				// 包配置
				// .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
				.packageConfig((scanner, builder) -> builder.parent(Paths.get(System.getProperty("user.dir")) + "/src/main/java"))
				// 策略配置
				.strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
						.entityBuilder()
						.enableLombok()
						.addTableFills(
//								new Column("create_time", FieldFill.INSERT)
						)
						.build())
				// 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.templateEngine(new FreemarkerTemplateEngine())
				.execute();
		// @formatter:on
	}

	// 处理 all 情况
	protected static List<String> getTables(String tables) {
		return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
	}

}
