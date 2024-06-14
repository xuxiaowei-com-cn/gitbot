package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlBranch;
import cn.com.xuxiaowei.gitbot.mapper.GlBranchMapper;
import cn.com.xuxiaowei.gitbot.service.IGlBranchService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
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
public class GlBranchServiceImpl extends ServiceImpl<GlBranchMapper, GlBranch> implements IGlBranchService {

	@Override
	public void saveOwnedBranch(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken,
			Object projectIdOrPath) throws GitLabApiException, MalformedURLException {

		URL url = new URL(hostUrl);
		String host = url.getHost();

		int saved = 0;
		int updated = 0;

		try (GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken)) {
			gitLabApi.setIgnoreCertificateErrors(true);

			long projectId;
			if (projectIdOrPath instanceof Long) {
				projectId = (Long) projectIdOrPath;
			}
			else {
				Project project = gitLabApi.getProjectApi().getProject(projectIdOrPath);
				projectId = project.getId();
			}

			List<Branch> branches;
			try {
				branches = gitLabApi.getRepositoryApi().getBranches(projectIdOrPath);
			}
			catch (GitLabApiException e) {
				int httpStatus = e.getHttpStatus();
				if (httpStatus == 404) {
					log.error("分支不存在：", e);
				}
				else {
					log.error("获取分支异常：", e);
				}
				return;
			}
			for (Branch branch : branches) {

				// @formatter:off
				GlBranch glBranch = new GlBranch();
				glBranch.setProjectId(projectId);
				glBranch.setHost(host);
				glBranch.setName(branch.getName());
				glBranch.setDevelopersCanMerge(branch.getDevelopersCanMerge());
				glBranch.setDevelopersCanPush(branch.getDevelopersCanPush());
				glBranch.setMerged(branch.getMerged());
				glBranch.setIsProtected(branch.getProtected());
				glBranch.setIsDefault(branch.getDefault());
				glBranch.setCanPush(branch.getCanPush());
				glBranch.setWebUrl(branch.getWebUrl());

				Commit commit = branch.getCommit();

				// glBranch.setCommitAuthor(commit.getAuthor());
				glBranch.setCommitAuthoredDate(commit.getAuthoredDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glBranch.setCommitAuthorEmail(commit.getAuthorEmail());
				glBranch.setCommitAuthorName(commit.getAuthorName());
				glBranch.setCommitCommittedDate(commit.getCommittedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glBranch.setCommitCommitterEmail(commit.getCommitterEmail());
				glBranch.setCommitCommitterName(commit.getCommitterName());
				glBranch.setCommitCreatedAt(commit.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glBranch.setCommitId(commit.getId());
				glBranch.setCommitMessage(commit.getMessage());
				glBranch.setCommitParentIds(commit.getParentIds() == null ? null : Joiner.on(",").join(commit.getParentIds()));
				glBranch.setCommitShortId(commit.getShortId());
				// glBranch.setCommitStats(commit.getStats());
				glBranch.setCommitStatus(commit.getStatus());
				glBranch.setCommitTimestamp(commit.getTimestamp() == null ? null : commit.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glBranch.setCommitTitle(commit.getTitle());
				glBranch.setCommitUrl(commit.getUrl());
				glBranch.setCommitWebUrl(commit.getWebUrl());
				glBranch.setCommitProjectId(commit.getProjectId());
				glBranch.setCommitLastPipelineId(commit.getLastPipeline() == null ? null : commit.getLastPipeline().getId());
				glBranch.setCommitLastPipelineIid(commit.getLastPipeline() == null ? null : commit.getLastPipeline().getIid());
				// @formatter:on

				QueryWrapper<GlBranch> queryWrapper = new QueryWrapper<GlBranch>()
					//
					.eq("project_id", glBranch.getProjectId())
					//
					.eq("`host`", glBranch.getHost())
					//
					.eq("name", glBranch.getName());
				long count = count(queryWrapper);
				if (count == 0) {
					save(glBranch);
					saved++;
				}
				else {
					update(glBranch, queryWrapper);
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
