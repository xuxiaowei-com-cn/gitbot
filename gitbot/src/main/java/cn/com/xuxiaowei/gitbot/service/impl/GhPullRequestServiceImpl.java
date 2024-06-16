package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GhPullRequest;
import cn.com.xuxiaowei.gitbot.mapper.GhPullRequestMapper;
import cn.com.xuxiaowei.gitbot.service.IGhPullRequestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
@Slf4j
@Service
public class GhPullRequestServiceImpl extends ServiceImpl<GhPullRequestMapper, GhPullRequest>
		implements IGhPullRequestService {

	@Override
	public void savePullRequest(String oauthToken, Long projectId, GHIssueState issueState) throws IOException {

		int saved = 0;
		int updated = 0;

		try {
			GitHubBuilder gitHubBuilder = new GitHubBuilder();
			gitHubBuilder.withOAuthToken(oauthToken);
			GitHub github = gitHubBuilder.build();

			GHRepository repository = github.getRepositoryById(projectId);

			List<GHPullRequest> pullRequests = repository.getPullRequests(issueState);

			for (GHPullRequest pullRequest : pullRequests) {

				// @formatter:off
				GhPullRequest ghPullRequest = new GhPullRequest();
				ghPullRequest.setId(pullRequest.getId());
				ghPullRequest.setProjectId(projectId);
				ghPullRequest.setPatchUrl(pullRequest.getPatchUrl().toString());
				ghPullRequest.setDiffUrl(pullRequest.getDiffUrl().toString());
				ghPullRequest.setIssueUrl(pullRequest.getIssueUrl().toString());
				ghPullRequest.setMergedAt(pullRequest.getMergedAt() == null ? null : pullRequest.getMergedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				ghPullRequest.setMergedBy(pullRequest.getMergedBy() == null ? null : pullRequest.getMergedBy().getId());
				ghPullRequest.setReviewComments(pullRequest.getReviewComments());
				ghPullRequest.setAdditions(pullRequest.getAdditions());
				ghPullRequest.setCommits(pullRequest.getCommits());
				ghPullRequest.setMerged(pullRequest.isMerged());
				// ghPullRequest.setMaintainerCanModify();
				// ghPullRequest.setDraft();
				ghPullRequest.setMergeable(pullRequest.getMergeable());
				ghPullRequest.setDeletions(pullRequest.getDeletions());
				ghPullRequest.setMergeableState(pullRequest.getMergeableState());
				ghPullRequest.setChangedFiles(pullRequest.getChangedFiles());
				ghPullRequest.setMergeCommitSha(pullRequest.getMergeCommitSha());
				// ghPullRequest.setAutoMerge();
				ghPullRequest.setAssignee(pullRequest.getAssignee() == null ? null : pullRequest.getAssignee().getId());
				// ghPullRequest.setAssignees();
				ghPullRequest.setState(pullRequest.getState().toString());
				ghPullRequest.setStateReason(pullRequest.getStateReason() == null ? null : pullRequest.getStateReason().toString());
				ghPullRequest.setNumber(pullRequest.getNumber());
				ghPullRequest.setClosedAt(pullRequest.getClosedAt() == null ? null : pullRequest.getClosedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				// ghPullRequest.setComments();
				ghPullRequest.setBody(pullRequest.getBody());
				ghPullRequest.setTitle(pullRequest.getTitle());
				ghPullRequest.setHtmlUrl(pullRequest.getHtmlUrl().toString());
				ghPullRequest.setPullRequest(pullRequest.getPullRequest() == null ? null : pullRequest.getPullRequest().getUrl().toString());
				ghPullRequest.setMilestone(pullRequest.getMilestone() == null ? null : pullRequest.getMilestone().getId());
				ghPullRequest.setClosedBy(pullRequest.getClosedBy() == null ? null : pullRequest.getClosedBy().getId());
				// ghPullRequest.setLocked();
				ghPullRequest.setUrl(pullRequest.getUrl().toString());
				ghPullRequest.setNodeId(pullRequest.getNodeId());
				ghPullRequest.setCreatedAt(pullRequest.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				ghPullRequest.setUpdatedAt(pullRequest.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				// @formatter:on

				QueryWrapper<GhPullRequest> queryWrapper = new QueryWrapper<GhPullRequest>()
					//
					.eq("id", ghPullRequest.getId());
				long count = count(queryWrapper);
				if (count == 0) {
					save(ghPullRequest);
					saved++;
				}
				else {
					update(ghPullRequest, queryWrapper);
					updated++;
				}
			}
		}
		finally {
			log.debug("saved: {}", saved);
			log.debug("updated: {}", updated);
		}
	}

}
