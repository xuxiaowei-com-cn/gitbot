package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlIssue;
import cn.com.xuxiaowei.gitbot.mapper.GlIssueMapper;
import cn.com.xuxiaowei.gitbot.service.IGlIssueService;
import cn.com.xuxiaowei.gitbot.service.IGlNoteService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
@Slf4j
@Service
public class GlIssueServiceImpl extends ServiceImpl<GlIssueMapper, GlIssue> implements IGlIssueService {

	private IGlNoteService glNoteService;

	@Autowired
	public void setGlNoteService(IGlNoteService glNoteService) {
		this.glNoteService = glNoteService;
	}

	@Override
	public boolean saveOrUpdate(GlIssue entity) {
		QueryWrapper<GlIssue> queryWrapper = new QueryWrapper<GlIssue>()
			//
			.eq("id", entity.getId())
			//
			.eq("`host`", entity.getHost())
			//
			.eq("project_id", entity.getProjectId());
		long count = count(queryWrapper);
		return count == 0 ? save(entity) : update(entity, queryWrapper);
	}

	@Override
	public void saveIssue(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken)
			throws GitLabApiException, MalformedURLException {

		URL url = new URL(hostUrl);
		String host = url.getHost();

		int saved = 0;
		int updated = 0;

		try (GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken)) {
			gitLabApi.setIgnoreCertificateErrors(true);
			List<Issue> issues = gitLabApi.getIssuesApi().getIssues();
			for (Issue issue : issues) {

				// @formatter:off
				GlIssue glIssue = new GlIssue();
				glIssue.setId(issue.getId());
				glIssue.setIid(issue.getIid());
				glIssue.setHost(host);
				glIssue.setProjectId(issue.getProjectId());
				glIssue.setSubscribed(issue.getSubscribed());
				glIssue.setAssigneeId(issue.getAssignee() == null ? null : issue.getAssignee().getId());
				// List<Assignee> assignees = issue.getAssignees();
				glIssue.setAuthorId(issue.getAuthor().getId());
				glIssue.setConfidential(issue.getConfidential());
				glIssue.setCreatedAt(issue.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glIssue.setUpdatedAt(issue.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glIssue.setClosedAt(issue.getClosedAt() == null ? null : issue.getClosedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glIssue.setClosedBy(issue.getClosedBy() == null ? null : issue.getClosedBy().getId());
				glIssue.setDescription(issue.getDescription());
				glIssue.setDueDate(issue.getDueDate() == null ? null : issue.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glIssue.setActualId(issue.getActualId().asText());
				glIssue.setExternalId(issue.getExternalId());
				glIssue.setLabels(Joiner.on(",").join(issue.getLabels()));
				glIssue.setMilestoneId(issue.getMilestone() == null ? null : issue.getMilestone().getId());
				glIssue.setState(issue.getState().toString());
				glIssue.setTitle(issue.getTitle());
				glIssue.setUserNotesCount(issue.getUserNotesCount());
				glIssue.setWebUrl(issue.getWebUrl());
				glIssue.setWeight(issue.getWeight());
				glIssue.setDiscussionLocked(issue.getDiscussionLocked());
				glIssue.setTimeEstimate(issue.getTimeStats().getTimeEstimate());
				glIssue.setTotalTimeSpent(issue.getTimeStats().getTotalTimeSpent());
				glIssue.setHumanTimeEstimate(issue.getTimeStats().getHumanTimeEstimate() == null ? null : issue.getTimeStats().getHumanTimeEstimate().getSeconds());
				glIssue.setHumanTotalTimeSpent(issue.getTimeStats().getHumanTotalTimeSpent() == null ? null : issue.getTimeStats().getHumanTotalTimeSpent().getSeconds());
				glIssue.setUpvotes(issue.getUpvotes());
				glIssue.setDownvotes(issue.getDownvotes());
				glIssue.setMergeRequestsCount(issue.getMergeRequestsCount());
				glIssue.setHasTasks(issue.getHasTasks());
				glIssue.setTaskStatus(issue.getTaskStatus());
				glIssue.setIterationId(issue.getIteration() == null ? null : issue.getIteration().getId());
				glIssue.setTaskCompletionStatusCount(issue.getTaskCompletionStatus().getCount());
				glIssue.setTaskCompletionStatusCount(issue.getTaskCompletionStatus().getCompletedCount());
				// @formatter:on

				QueryWrapper<GlIssue> queryWrapper = new QueryWrapper<GlIssue>()
					//
					.eq("id", glIssue.getId())
					//
					.eq("`host`", glIssue.getHost())
					//
					.eq("project_id", glIssue.getProjectId());
				long count = count(queryWrapper);
				if (count == 0) {
					save(glIssue);
					saved++;
				}
				else {
					update(glIssue, queryWrapper);
					updated++;
				}

				glNoteService.saveNote(hostUrl, ignoreCertificateErrors, personalAccessToken, issue.getProjectId(),
						issue.getIid());
			}
		}
		finally {
			log.debug("saved: {}", saved);
			log.debug("updated: {}", updated);
		}
	}

}
