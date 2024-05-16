package cn.com.xuxiaowei.gitbot.github;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.PagedIterable;

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

	@Test
	void getOrganization() throws IOException {
		String namespace = "xuxiaowei-cloud";

		GitHub github = GitHubBuilder.fromEnvironment().build();

		GHOrganization organization = github.getOrganization(namespace);

		assertEquals(namespace, organization.getLogin());
	}

	@Test
	void getOrganizationRepositories() throws IOException {
		String namespace = "xuxiaowei-cloud";

		GitHub github = GitHubBuilder.fromEnvironment().build();

		GHOrganization organization = github.getOrganization(namespace);

		assertEquals(namespace, organization.getLogin());

		PagedIterable<org.kohsuke.github.GHRepository> ghRepositories = organization.listRepositories();
		for (GHRepository ghRepository : ghRepositories) {
			log.info(ghRepository.getName());
		}
	}

	@Test
	void getUser() throws IOException {
		String namespace = "xuxiaowei-com-cn";

		GitHub github = GitHubBuilder.fromEnvironment().build();

		GHUser user = github.getUser(namespace);

		assertEquals(namespace, user.getLogin());
	}

	@Test
	void getUserRepositories() throws IOException {
		String namespace = "xuxiaowei-com-cn";

		GitHub github = GitHubBuilder.fromEnvironment().build();

		GHUser user = github.getUser(namespace);

		assertEquals(namespace, user.getLogin());

		PagedIterable<GHRepository> ghRepositories = user.listRepositories();
		for (GHRepository ghRepository : ghRepositories) {
			log.info(ghRepository.getName());
		}
	}

}
