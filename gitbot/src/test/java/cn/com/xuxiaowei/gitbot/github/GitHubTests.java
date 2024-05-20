package cn.com.xuxiaowei.gitbot.github;

import cn.com.xuxiaowei.gitbot.entity.GhPullRequest;
import cn.com.xuxiaowei.gitbot.entity.GhRepository;
import cn.com.xuxiaowei.gitbot.service.IGhPullRequestService;
import cn.com.xuxiaowei.gitbot.service.IGhRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.PagedIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class GitHubTests {

	@Autowired
	private IGhRepositoryService ghRepositoryService;

	@Autowired
	private IGhPullRequestService ghPullRequestService;

	@Test
	void fromEnvironment() throws IOException {
		String namespace = "xuxiaowei-com-cn";
		String projectName = "gitbot";

		GitHubBuilder gitHubBuilder = GitHubBuilder.fromEnvironment();
		// @formatter:off
		//Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
		//gitHubBuilder.withProxy(proxy);
		// @formatter:off
		GitHub github = gitHubBuilder.build();
		GHRepository ghRepository = github.getRepository(namespace + "/" + projectName);

		assertEquals(projectName, ghRepository.getName());

		saveOrUpdate(ghRepository);

		List<GhRepository> list = ghRepositoryService.list();
		for (GhRepository gh : list) {
			log.info(String.valueOf(gh));
		}
	}

	@Test
	void getOrganization() throws IOException {
		String namespace = "xuxiaowei-cloud";

		GitHubBuilder gitHubBuilder = GitHubBuilder.fromEnvironment();
		// @formatter:off
		//Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
		//gitHubBuilder.withProxy(proxy);
		// @formatter:off
		GitHub github = gitHubBuilder.build();

		GHOrganization ghOrganization = github.getOrganization(namespace);

		assertEquals(namespace, ghOrganization.getLogin());
	}

	@Test
	void getOrganizationRepositories() throws IOException {
		String namespace = "xuxiaowei-cloud";

		GitHubBuilder gitHubBuilder = GitHubBuilder.fromEnvironment();
		// @formatter:off
		//Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
		//gitHubBuilder.withProxy(proxy);
		// @formatter:off
		GitHub github = gitHubBuilder.build();

		GHOrganization ghOrganization = github.getOrganization(namespace);

		assertEquals(namespace, ghOrganization.getLogin());

		PagedIterable<GHRepository> ghRepositories = ghOrganization.listRepositories();
		for (GHRepository ghRepository : ghRepositories) {
			log.info(ghRepository.getName());

			saveOrUpdate(ghRepository);
		}

		List<GhRepository> list = ghRepositoryService.list();
		for (GhRepository gh : list) {
			log.info(String.valueOf(gh));
		}
	}

	@Test
	void getOrganizationRepositoriesPullRequests() throws IOException {
		String namespace = "xuxiaowei-cloud";

		GitHubBuilder gitHubBuilder = GitHubBuilder.fromEnvironment();
		// @formatter:off
		//Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
		//gitHubBuilder.withProxy(proxy);
		// @formatter:off
		GitHub github = gitHubBuilder.build();

		GHOrganization ghOrganization = github.getOrganization(namespace);

		assertEquals(namespace, ghOrganization.getLogin());

		PagedIterable<GHRepository> ghRepositories = ghOrganization.listRepositories();
		for (GHRepository ghRepository : ghRepositories) {
			log.info(ghRepository.getName());

			List<GHPullRequest> pullRequests = ghRepository.getPullRequests(GHIssueState.ALL);

			for (GHPullRequest pullRequest : pullRequests) {
				saveOrUpdate(pullRequest);
			}
		}
	}

	@Test
	void getUser() throws IOException {
		String namespace = "xuxiaowei-com-cn";

		GitHubBuilder gitHubBuilder = GitHubBuilder.fromEnvironment();
		// @formatter:off
		//Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
		//gitHubBuilder.withProxy(proxy);
		// @formatter:off
		GitHub github = gitHubBuilder.build();

		GHUser user = github.getUser(namespace);

		assertEquals(namespace, user.getLogin());

	}

	@Test
	void getUserRepositories() throws IOException {
		String namespace = "xuxiaowei-com-cn";

		GitHubBuilder gitHubBuilder = GitHubBuilder.fromEnvironment();
		// @formatter:off
		//Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
		//gitHubBuilder.withProxy(proxy);
		// @formatter:off
		GitHub github = gitHubBuilder.build();

		GHUser user = github.getUser(namespace);

		assertEquals(namespace, user.getLogin());

		PagedIterable<GHRepository> ghRepositories = user.listRepositories();
		for (GHRepository ghRepository : ghRepositories) {
			log.info(ghRepository.getName());

			saveOrUpdate(ghRepository);
		}

		List<GhRepository> list = ghRepositoryService.list();
		for (GhRepository gh : list) {
			log.info(String.valueOf(gh));
		}
	}

	void saveOrUpdate(GHRepository ghRepository) throws IOException {
		GhRepository repository = new GhRepository();
		repository.setId(ghRepository.getId());
		repository.setNodeId(ghRepository.getNodeId());
		repository.setDescription(ghRepository.getDescription());
		repository.setName(ghRepository.getName());
		repository.setFullName(ghRepository.getFullName());
		repository.setHtmlUrl(ghRepository.getHtmlUrl().toString());
		// repository.setLicense(ghRepository.getLicense() == null ? null :
		// ghRepository.getLicense().getName());
		repository.setGitUrl(ghRepository.getGitTransportUrl());
		repository.setSshUrl(ghRepository.getSshUrl());
		repository.setSvnUrl(ghRepository.getSvnUrl());
		repository.setMirrorUrl(ghRepository.getMirrorUrl());
		repository.setHasIssues(ghRepository.hasIssues());
		repository.setHasWiki(ghRepository.hasWiki());
		repository.setFork(ghRepository.isFork());
		repository.setHasDownloads(ghRepository.hasDownloads());
		repository.setHasPages(ghRepository.hasPages());
		repository.setArchived(ghRepository.isArchived());
		repository.setDisabled(ghRepository.isDisabled());
		repository.setHasProjects(ghRepository.hasProjects());
		repository.setAllowSquashMerge(ghRepository.isAllowSquashMerge());
		repository.setAllowMergeCommit(ghRepository.isAllowMergeCommit());
		repository.setAllowRebaseMerge(ghRepository.isAllowRebaseMerge());
		repository.setAllowForking(ghRepository.isAllowForking());
		repository.setDeleteBranchOnMerge(ghRepository.isDeleteBranchOnMerge());
		repository.setIsPrivate(ghRepository.isPrivate());
		repository.setVisibility(ghRepository.getVisibility().toString());
		repository.setForksCount(ghRepository.getForksCount());
		repository.setStargazersCount(ghRepository.getStargazersCount());
		repository.setWatchersCount(ghRepository.getWatchersCount());
		repository.setSize(ghRepository.getSize());
		repository.setOpenIssuesCount(ghRepository.getOpenIssueCount());
		repository.setSubscribersCount(ghRepository.getSubscribersCount());
		repository.setPushedAt(ghRepository.getPushedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		repository.setDefaultBranch(ghRepository.getDefaultBranch());
		repository.setLanguage(ghRepository.getLanguage());
		// @formatter:off
		repository.setTemplateRepositoryId(ghRepository.getTemplateRepository() == null ? null : ghRepository.getTemplateRepository().getId());
		// @formatter:on
		// repository.setSourceId(ghRepository.getSource() == null ? null :
		// ghRepository.getSource().getId());
		// repository.setParentId(ghRepository.getParent() == null ? null :
		// ghRepository.getParent().getId());
		repository.setIsTemplate(ghRepository.isTemplate());
		// try {
		// List<GHHook> hooks = ghRepository.getHooks();
		// }
		// catch (Exception e) {
		// log.error("获取 Hooks 异常：", e);
		// }

		repository.setUrl(ghRepository.getUrl().toString());
		// @formatter:off
		repository.setCreatedAt(ghRepository.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		repository.setUpdatedAt(ghRepository.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		// @formatter:on

		ghRepositoryService.saveOrUpdate(repository);
	}

	void saveOrUpdate(GHPullRequest ghPullRequest) throws IOException {
		GhPullRequest pullRequest = new GhPullRequest();
		pullRequest.setId(ghPullRequest.getId());
		pullRequest.setPatchUrl(ghPullRequest.getPatchUrl().toString());
		pullRequest.setDiffUrl(ghPullRequest.getDiffUrl().toString());
		pullRequest.setIssueUrl(ghPullRequest.getIssueUrl().toString());
		// @formatter:off
		pullRequest.setMergedAt(ghPullRequest.getMergedAt() == null ? null : ghPullRequest.getMergedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		// @formatter:on
		pullRequest.setMergedBy(ghPullRequest.getMergedBy() == null ? null : ghPullRequest.getMergedBy().getLogin());
		pullRequest.setReviewComments(ghPullRequest.getReviewComments());
		pullRequest.setAdditions(ghPullRequest.getAdditions());
		pullRequest.setCommits(ghPullRequest.getCommits());
		// pullRequest.setMerged();
		// pullRequest.setMaintainerCanModify();
		// pullRequest.setDraft();
		pullRequest.setMergeable(ghPullRequest.getMergeable());
		pullRequest.setDeletions(ghPullRequest.getDeletions());
		pullRequest.setMergeableState(ghPullRequest.getMergeableState());
		pullRequest.setChangedFiles(ghPullRequest.getChangedFiles());
		pullRequest.setMergeCommitSha(ghPullRequest.getMergeCommitSha());
		// pullRequest.setAutoMerge(ghPullRequest.getAutoMerge());
		pullRequest.setAssignee(ghPullRequest.getAssignee() == null ? null : ghPullRequest.getAssignee().getLogin());
		// pullRequest.setAssignees();
		pullRequest.setState(ghPullRequest.getState().toString());
		// @formatter:off
		pullRequest.setStateReason(ghPullRequest.getStateReason() == null ? null : ghPullRequest.getStateReason().toString());
		// @formatter:on
		pullRequest.setNumber(ghPullRequest.getNumber());
		// @formatter:off
		pullRequest.setClosedAt(ghPullRequest.getClosedAt() == null ? null : ghPullRequest.getClosedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		// @formatter:on
		// pullRequest.setComments();
		pullRequest.setBody(ghPullRequest.getBody());
		pullRequest.setTitle(ghPullRequest.getTitle());
		pullRequest.setHtmlUrl(ghPullRequest.getHtmlUrl().toString());
		// pullRequest.setPullRequest();
		// pullRequest.setMilestone();
		pullRequest.setClosedBy(ghPullRequest.getClosedBy() == null ? null : ghPullRequest.getClosedBy().getLogin());
		pullRequest.setLocked(ghPullRequest.isLocked());
		pullRequest.setUrl(ghPullRequest.getUrl().toString());
		pullRequest.setNodeId(ghPullRequest.getNodeId());
		// @formatter:off
		pullRequest.setCreatedAt(ghPullRequest.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		pullRequest.setUpdatedAt(ghPullRequest.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		// @formatter:on

		ghPullRequestService.saveOrUpdate(pullRequest);
	}

}
