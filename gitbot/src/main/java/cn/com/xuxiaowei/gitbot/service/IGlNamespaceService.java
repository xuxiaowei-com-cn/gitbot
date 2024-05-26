package cn.com.xuxiaowei.gitbot.service;

import cn.com.xuxiaowei.gitbot.bo.GlBO;
import cn.com.xuxiaowei.gitbot.entity.GlNamespace;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Namespace;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-05-20
 */
public interface IGlNamespaceService extends IService<GlNamespace> {

	GlNamespace getByPrimaryKey(Long id, String host);

	boolean updateBatch(List<GlNamespace> glNamespaceList);

	List<Namespace> listNamespaces(GlBO glBO) throws GitLabApiException;

}
