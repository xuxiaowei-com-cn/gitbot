package cn.com.xuxiaowei.gitbot.scheduled;

import cn.com.xuxiaowei.gitbot.constant.LogConstants;
import cn.com.xuxiaowei.gitbot.entity.ScheduledToken;
import cn.com.xuxiaowei.gitbot.service.IGlNamespaceService;
import cn.com.xuxiaowei.gitbot.service.IGlProjectService;
import cn.com.xuxiaowei.gitbot.service.IScheduledTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.gitlab4j.api.GitLabApiException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.List;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "gitbot.scheduled.enabled-gitlab", havingValue = "true")
public class GitLabScheduled {

	private IScheduledTokenService scheduledTokenService;

	private IGlNamespaceService glNamespaceService;

	private IGlProjectService glProjectService;

	@Autowired
	public void setScheduledTokenService(IScheduledTokenService scheduledTokenService) {
		this.scheduledTokenService = scheduledTokenService;
	}

	@Autowired
	public void setGlNamespaceService(IGlNamespaceService glNamespaceService) {
		this.glNamespaceService = glNamespaceService;
	}

	@Autowired
	public void setGlProjectService(IGlProjectService glProjectService) {
		this.glProjectService = glProjectService;
	}

	/**
	 * 注意：项目未异步方法执行功能时，定时器如果执行时间大于间隔时间，不会重复执行
	 * @throws MalformedURLException
	 * @throws GitLabApiException
	 */
	@Scheduled(fixedRate = 60_000 * 5)
	public void saveNamespace() throws MalformedURLException, GitLabApiException {
		String id = RandomStringUtils.randomAlphanumeric(8);
		MDC.put(LogConstants.G_REQUEST_ID, id);

		String className = getClass().getName();
		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		log.info("Start Class: {} Method: {}", className, methodName);

		List<ScheduledToken> scheduledTokenList = scheduledTokenService.listByHostNotGitHub();

		log.info("scheduledTokenList.size(): {}", scheduledTokenList.size());

		for (ScheduledToken scheduledToken : scheduledTokenList) {
			String hostUrl = scheduledToken.getHost();
			boolean ignoreCertificateErrors = scheduledToken.getIgnoreCertificateErrors();
			String personalAccessToken = scheduledToken.getToken();
			try {
				glNamespaceService.saveNamespace(hostUrl, ignoreCertificateErrors, personalAccessToken);
			}
			catch (GitLabApiException e) {
				log.error("GitLab: {} 异常：", hostUrl, e);
				throw e;
			}
			catch (MalformedURLException e) {
				log.error("实例 URL: {} 不正确：", hostUrl, e);
				throw e;
			}
		}

		log.info("End Class: {} Method: {}", className, methodName);

		MDC.remove(LogConstants.G_REQUEST_ID);
	}

	/**
	 * 注意：项目未异步方法执行功能时，定时器如果执行时间大于间隔时间，不会重复执行
	 * @throws MalformedURLException
	 * @throws GitLabApiException
	 */
	@Scheduled(fixedRate = 60_000 * 5)
	public void saveOwnedProject() throws MalformedURLException, GitLabApiException {
		String id = RandomStringUtils.randomAlphanumeric(8);
		MDC.put(LogConstants.G_REQUEST_ID, id);

		String className = getClass().getName();
		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		log.info("Start Class: {} Method: {}", className, methodName);

		List<ScheduledToken> scheduledTokenList = scheduledTokenService.listByHostNotGitHub();

		log.info("scheduledTokenList.size(): {}", scheduledTokenList.size());

		for (ScheduledToken scheduledToken : scheduledTokenList) {
			String hostUrl = scheduledToken.getHost();
			boolean ignoreCertificateErrors = scheduledToken.getIgnoreCertificateErrors();
			String personalAccessToken = scheduledToken.getToken();
			try {
				glProjectService.saveOwnedProject(hostUrl, ignoreCertificateErrors, personalAccessToken);
			}
			catch (GitLabApiException e) {
				log.error("GitLab: {} 异常：", hostUrl, e);
				throw e;
			}
			catch (MalformedURLException e) {
				log.error("实例 URL: {} 不正确：", hostUrl, e);
				throw e;
			}
		}

		log.info("End Class: {} Method: {}", className, methodName);

		MDC.remove(LogConstants.G_REQUEST_ID);
	}

}
