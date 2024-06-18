package cn.com.xuxiaowei.gitbot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class TableTests {

	@Autowired
	private GlInstanceService glInstanceService;

	@Autowired
	private IAuthoritiesService authoritiesService;

	@Autowired
	private IGhBranchService ghBranchService;

	@Autowired
	private IGhOrganizationService ghOrganizationService;

	@Autowired
	private IGhPullRequestService ghPullRequestService;

	@Autowired
	private IGhRepositoryService ghRepositoryService;

	@Autowired
	private IGlBranchService glBranchService;

	@Autowired
	private IGlCommitService glCommitService;

	@Autowired
	private IGlEnvironmentService glEnvironmentService;

	@Autowired
	private IGlIssueService glIssueService;

	@Autowired
	private IGlNamespaceService glNamespaceService;

	@Autowired
	private IGlNoteService glNoteService;

	@Autowired
	private IGlProjectService glProjectService;

	@Autowired
	private IGlVariableService glVariableService;

	@Autowired
	private IGroupAuthoritiesService groupAuthoritiesService;

	@Autowired
	private IGroupMembersService groupMembersService;

	@Autowired
	private IGroupsService groupsService;

	@Autowired
	private InstanceService instanceService;

	@Autowired
	private IOauth2AuthorizationConsentService oauth2AuthorizationConsentService;

	@Autowired
	private IOauth2AuthorizationService oauth2AuthorizationService;

	@Autowired
	private IOauth2RegisteredClientService oauth2RegisteredClientService;

	@Autowired
	private IScheduledTokenService scheduledTokenService;

	@Autowired
	private IUsersService usersService;

	@Test
	void list() {
		glInstanceService.list();
		authoritiesService.list(new Page<>(1, 1));
		ghBranchService.list(new Page<>(1, 1));
		ghOrganizationService.list(new Page<>(1, 1));
		ghPullRequestService.list(new Page<>(1, 1));
		ghRepositoryService.list(new Page<>(1, 1));
		glBranchService.list(new Page<>(1, 1));
		glCommitService.list(new Page<>(1, 1));
		glEnvironmentService.list(new Page<>(1, 1));
		glIssueService.list(new Page<>(1, 1));
		glNamespaceService.list(new Page<>(1, 1));
		glNoteService.list(new Page<>(1, 1));
		glProjectService.list(new Page<>(1, 1));
		glVariableService.list(new Page<>(1, 1));
		groupAuthoritiesService.list(new Page<>(1, 1));
		groupMembersService.list(new Page<>(1, 1));
		groupsService.list(new Page<>(1, 1));
		instanceService.list();
		oauth2AuthorizationConsentService.list(new Page<>(1, 1));
		oauth2AuthorizationService.list(new Page<>(1, 1));
		oauth2RegisteredClientService.list(new Page<>(1, 1));
		scheduledTokenService.list(new Page<>(1, 1));
		usersService.list(new Page<>(1, 1));
	}

}
