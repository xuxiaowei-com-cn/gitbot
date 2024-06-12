package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.entity.GlNote;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gitlab4j.api.GitLabApiException;

import java.net.MalformedURLException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-12
 */
public interface IGlNoteService extends IService<GlNote> {

	void saveNote(String hostUrl, boolean ignoreCertificateErrors, String personalAccessToken, Object projectIdOrPath,
			Long issueIid) throws GitLabApiException, MalformedURLException;

}
