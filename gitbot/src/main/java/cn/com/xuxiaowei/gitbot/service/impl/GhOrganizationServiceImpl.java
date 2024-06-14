package cn.com.xuxiaowei.gitbot.service.impl;

import cn.com.xuxiaowei.gitbot.entity.GhOrganization;
import cn.com.xuxiaowei.gitbot.mapper.GhOrganizationMapper;
import cn.com.xuxiaowei.gitbot.service.IGhOrganizationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2024-06-14
 */
@Slf4j
@Service
public class GhOrganizationServiceImpl extends ServiceImpl<GhOrganizationMapper, GhOrganization>
		implements IGhOrganizationService {

	/**
	 * 需要授权：read:org
	 */
	@Override
	public List<GHOrganization> saveMyOrganizations(String oauthToken) throws IOException {

		int saved = 0;
		int updated = 0;
		Collection<GHOrganization> ghOrganizations;

		try {
			GitHubBuilder gitHubBuilder = new GitHubBuilder();

			gitHubBuilder.withOAuthToken(oauthToken);

			GitHub github = gitHubBuilder.build();

			// 需要授权 read:org
			Map<String, GHOrganization> myOrganizations = github.getMyOrganizations();
			ghOrganizations = myOrganizations.values();

			for (GHOrganization organization : ghOrganizations) {

				GhOrganization ghOrganization = new GhOrganization();
				ghOrganization.setId(organization.getId());
				ghOrganization.setUrl(organization.getUrl().toString());
				ghOrganization.setLogin(organization.getLogin());
				ghOrganization.setAvatarUrl(organization.getAvatarUrl());
				ghOrganization.setLocation(organization.getLocation());
				ghOrganization.setBlog(organization.getBlog());
				ghOrganization.setEmail(organization.getEmail());
				ghOrganization.setName(organization.getName());
				ghOrganization.setCompany(organization.getCompany());
				ghOrganization.setType(organization.getType());
				ghOrganization.setTwitterUsername(organization.getTwitterUsername());
				ghOrganization.setHtmlUrl(organization.getHtmlUrl().toString());
				ghOrganization.setFollowers(organization.getFollowersCount());
				ghOrganization.setFollowing(organization.getFollowingCount());
				ghOrganization.setPublicRepos(organization.getPublicRepoCount());
				ghOrganization.setPublicGists(organization.getPublicGistCount());
				ghOrganization.setSiteAdmin(organization.isSiteAdmin());
				ghOrganization.setNodeId(organization.getNodeId());
				// @formatter:off
				ghOrganization.setCreatedAt(organization.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				ghOrganization.setUpdatedAt(organization.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				// @formatter:on
				ghOrganization.setRootMyselfId(organization.getRoot().getMyself().getId());

				QueryWrapper<GhOrganization> queryWrapper = new QueryWrapper<GhOrganization>()
					//
					.eq("id", ghOrganization.getId());
				long count = count(queryWrapper);
				if (count == 0) {
					save(ghOrganization);
					saved++;
				}
				else {
					update(ghOrganization, queryWrapper);
					updated++;
				}
			}
		}
		finally {
			log.debug("saved: {}", saved);
			log.debug("updated: {}", updated);
		}

		return new ArrayList<>(ghOrganizations);
	}

}
