package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.bo.GlBO;
import cn.com.xuxiaowei.gitbot.entity.GlProject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
public interface IGlProjectService extends IService<GlProject> {

	GlProject getByPrimaryKey(Long id, String host);

	boolean updateBatch(List<GlProject> glProjectList);

	List<Project> listProjects(GlBO glBO) throws GitLabApiException;

}
