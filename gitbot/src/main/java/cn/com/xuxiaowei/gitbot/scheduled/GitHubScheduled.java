package cn.com.xuxiaowei.gitbot.scheduled;

import cn.com.xuxiaowei.gitbot.constant.LogConstants;
import cn.com.xuxiaowei.gitbot.entity.ScheduledToken;
import cn.com.xuxiaowei.gitbot.service.IGhRepositoryService;
import cn.com.xuxiaowei.gitbot.service.IScheduledTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.kohsuke.github.GHIssueState;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "gitbot.scheduled.enabled-github", havingValue = "true")
public class GitHubScheduled {

	private IScheduledTokenService scheduledTokenService;

	private IGhRepositoryService ghRepositoryService;

	@Autowired
	public void setScheduledTokenService(IScheduledTokenService scheduledTokenService) {
		this.scheduledTokenService = scheduledTokenService;
	}

	@Autowired
	public void setGhRepositoryService(IGhRepositoryService ghRepositoryService) {
		this.ghRepositoryService = ghRepositoryService;
	}

	/**
	 * 注意：项目未异步方法执行功能时，定时器如果执行时间大于间隔时间，不会重复执行
	 * @throws IOException
	 */
	@Scheduled(fixedRate = 60_000 * 5)
	public void saveMyselfRepository() throws IOException {
		String id = RandomStringUtils.randomAlphanumeric(8);
		MDC.put(LogConstants.G_REQUEST_ID, id);

		String className = getClass().getName();
		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		log.info("Start Class: {} Method: {}", className, methodName);

		ScheduledToken scheduledToken = scheduledTokenService.getByHostGitHub();
		if (scheduledToken == null) {
			log.error("github token is null");
			return;
		}

		String hostUrl = scheduledToken.getHost();
		boolean ignoreCertificateErrors = scheduledToken.getIgnoreCertificateErrors();
		String oauthToken = scheduledToken.getToken();
		try {
			boolean saveBranch = true;
			boolean savePullRequest = true;
			GHIssueState issueState = GHIssueState.ALL;
			ghRepositoryService.saveMyselfRepository(oauthToken, saveBranch, savePullRequest, issueState);
		}
		catch (IOException e) {
			log.error("GitHub: {} 异常：", hostUrl, e);
			throw e;
		}

		log.info("End Class: {} Method: {}", className, methodName);

		MDC.remove(LogConstants.G_REQUEST_ID);
	}

}
