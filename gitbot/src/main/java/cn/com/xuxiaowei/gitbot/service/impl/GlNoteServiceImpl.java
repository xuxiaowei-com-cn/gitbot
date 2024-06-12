package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GlNote;
import cn.com.xuxiaowei.gitbot.mapper.GlNoteMapper;
import cn.com.xuxiaowei.gitbot.service.IGlNoteService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Issue;
import org.gitlab4j.api.models.Note;
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
public class GlNoteServiceImpl extends ServiceImpl<GlNoteMapper, GlNote> implements IGlNoteService {

	@Override
	public void saveNote(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken,
			Object projectIdOrPath, Long issueIid) throws GitLabApiException, MalformedURLException {

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

			Issue issue = gitLabApi.getIssuesApi().getIssue(projectIdOrPath, issueIid);
			Long issueId = issue.getId();

			List<Note> issueNotes = gitLabApi.getNotesApi().getIssueNotes(projectIdOrPath, issueIid);
			for (Note note : issueNotes) {

				// @formatter:off
				GlNote glNote = new GlNote();
				glNote.setId(note.getId());
				glNote.setHost(host);
				glNote.setProjectId(projectId);
				glNote.setIssueId(issueId);
				glNote.setIssueIid(issueIid);
				glNote.setAttachment(note.getAttachment());
				glNote.setAuthorId(note.getAuthor().getId());
				glNote.setBody(note.getBody());
				glNote.setCreatedAt(note.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glNote.setDownvote(note.getDownvote());
				glNote.setExpiresAt(note.getExpiresAt() == null ? null : note.getExpiresAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glNote.setFileName(note.getFileName());
				glNote.setNoteableId(note.getNoteableId());
				glNote.setNoteableType(note.getNoteableType());
				glNote.setNoteableIid(note.getNoteableIid());
				glNote.setSystem(note.getSystem());
				glNote.setTitle(note.getTitle());
				glNote.setUpdatedAt(note.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glNote.setUpvote(note.getUpvote());
				glNote.setResolved(note.getResolved());
				glNote.setResolvable(note.getResolvable());
				glNote.setResolvedBy(note.getResolvedBy() == null ? null : note.getResolvedBy().getId());
				glNote.setResolvedAt(note.getResolvedAt() == null ? null : note.getResolvedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				glNote.setInternal(note.getInternal());
				glNote.setType(note.getType() == null ? null : note.getType().toValue());
				// Position position = note.getPosition();
				// @formatter:on

				QueryWrapper<GlNote> queryWrapper = new QueryWrapper<GlNote>()
					//
					.eq("id", glNote.getId())
					//
					.eq("`host`", glNote.getHost())
					//
					.eq("project_id", glNote.getProjectId());
				long count = count(queryWrapper);
				if (count == 0) {
					save(glNote);
					saved++;
				}
				else {
					update(glNote, queryWrapper);
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
