package cn.com.xuxiaowei.gitbot.github;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class GitHubTests {

	@Test
	void fromEnvironment() throws IOException {
		String namespace = "xuxiaowei-com-cn";
		String projectName = "gitbot";

		GitHub github = GitHubBuilder.fromEnvironment().build();
		GHRepository repository = github.getRepository(namespace + "/" + projectName);

		assertEquals(projectName, repository.getName());
	}

}
