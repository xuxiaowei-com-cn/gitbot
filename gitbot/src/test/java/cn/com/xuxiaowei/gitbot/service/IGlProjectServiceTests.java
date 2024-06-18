package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.GlProject;
import cn.com.xuxiaowei.gitbot.entity.ScheduledToken;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Visibility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.util.List;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class IGlProjectServiceTests {

	@Autowired
	private IGlProjectService glProjectService;

	@Autowired
	private IScheduledTokenService scheduledTokenService;

	@Test
	void saveOwnedProject() throws MalformedURLException, GitLabApiException {
		String gitbotGitlabEnable = System.getenv("GITBOT_GITLAB_ENABLE");
		log.info(gitbotGitlabEnable);
		if (Boolean.TRUE.toString().equals(gitbotGitlabEnable)) {
			String hostUrl = "https://jihulab.com";
			boolean ignoreCertificateErrors = true;
			String personalAccessToken = System.getenv("GITBOT_GITLAB_TOKEN");
			glProjectService.saveOwnedProject(hostUrl, ignoreCertificateErrors, personalAccessToken);
		}
		else {
			log.info("跳过 GitLab 测试");
		}
	}

	// @Test
	void privateProject() throws GitLabApiException {
		ScheduledToken scheduledToken = scheduledTokenService.lambdaQuery()
			.eq(ScheduledToken::getHost, "https://gitlab.xuxiaowei.com.cn")
			.one();
		List<GlProject> list = glProjectService.lambdaQuery()
			.eq(GlProject::getHost, "gitlab.xuxiaowei.com.cn")
			.likeRight(GlProject::getPathWithNamespace, "mirrors")
			.notIn(GlProject::getVisibility, Visibility.PRIVATE)
			.list();
		for (GlProject glProject : list) {
			log.info(glProject.getWebUrl());

			try (GitLabApi gitLabApi = new GitLabApi("https://gitlab.xuxiaowei.com.cn", scheduledToken.getToken())) {
				gitLabApi.setIgnoreCertificateErrors(true);
				Project project = gitLabApi.getProjectApi().getProject(glProject.getId());
				if (!project.getVisibility().equals(Visibility.PRIVATE)) {
					project.setVisibility(Visibility.PRIVATE);
					gitLabApi.getProjectApi().updateProject(project);
				}
			}
		}
	}

}