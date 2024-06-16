package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.GhPullRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.kohsuke.github.GHIssueState;

import java.io.IOException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
public interface IGhPullRequestService extends IService<GhPullRequest> {

	void savePullRequest(String oauthToken, Long projectId, GHIssueState issueState) throws IOException;

}
