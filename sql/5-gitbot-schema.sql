/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : gitbot

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 12/06/2024 20:41:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gh_pull_request
-- ----------------------------
CREATE TABLE `gh_pull_request`  (
  `id` bigint(20) NOT NULL,
  `patch_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `diff_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `issue_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `merged_at` datetime(0) NULL DEFAULT NULL,
  `merged_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `review_comments` int(11) NULL DEFAULT NULL,
  `additions` int(11) NULL DEFAULT NULL,
  `commits` int(11) NULL DEFAULT NULL,
  `merged` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `maintainer_can_modify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `draft` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mergeable` tinyint(1) NULL DEFAULT NULL,
  `deletions` int(11) NULL DEFAULT NULL,
  `mergeable_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `changed_files` int(11) NULL DEFAULT NULL,
  `merge_commit_sha` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `auto_merge` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `assignee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `assignees` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `state_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `number` int(11) NULL DEFAULT NULL,
  `closed_at` datetime(0) NULL DEFAULT NULL,
  `comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `html_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pull_request` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `milestone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `closed_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `locked` tinyint(1) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `node_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gh_repository
-- ----------------------------
CREATE TABLE `gh_repository`  (
  `id` bigint(20) NOT NULL,
  `node_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `html_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `git_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ssh_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `svn_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mirror_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `has_issues` tinyint(1) NULL DEFAULT NULL,
  `has_wiki` tinyint(1) NULL DEFAULT NULL,
  `fork` tinyint(1) NULL DEFAULT NULL,
  `has_downloads` tinyint(1) NULL DEFAULT NULL,
  `has_pages` tinyint(1) NULL DEFAULT NULL,
  `archived` tinyint(1) NULL DEFAULT NULL,
  `disabled` tinyint(1) NULL DEFAULT NULL,
  `has_projects` tinyint(1) NULL DEFAULT NULL,
  `allow_squash_merge` tinyint(1) NULL DEFAULT NULL,
  `allow_merge_commit` tinyint(1) NULL DEFAULT NULL,
  `allow_rebase_merge` tinyint(1) NULL DEFAULT NULL,
  `allow_forking` tinyint(1) NULL DEFAULT NULL,
  `delete_branch_on_merge` tinyint(1) NULL DEFAULT NULL,
  `is_private` tinyint(1) NULL DEFAULT NULL,
  `visibility` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `forks_count` int(11) NULL DEFAULT NULL,
  `stargazers_count` int(11) NULL DEFAULT NULL,
  `watchers_count` int(11) NULL DEFAULT NULL,
  `size` int(11) NULL DEFAULT NULL,
  `open_issues_count` int(11) NULL DEFAULT NULL,
  `subscribers_count` int(11) NULL DEFAULT NULL,
  `pushed_at` datetime(0) NULL DEFAULT NULL,
  `default_branch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `template_repository_id` bigint(20) NULL DEFAULT NULL,
  `source_id` bigint(20) NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `is_template` tinyint(1) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gl_branch
-- ----------------------------
CREATE TABLE `gl_branch`  (
  `project_id` bigint(20) NOT NULL,
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `developers_can_merge` tinyint(1) NULL DEFAULT NULL,
  `developers_can_push` tinyint(1) NULL DEFAULT NULL,
  `merged` tinyint(1) NULL DEFAULT NULL,
  `is_protected` tinyint(1) NULL DEFAULT NULL,
  `is_default` tinyint(1) NULL DEFAULT NULL,
  `can_push` tinyint(1) NULL DEFAULT NULL,
  `web_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_authored_date` datetime(0) NULL DEFAULT NULL,
  `commit_author_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_author_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_committed_date` datetime(0) NULL DEFAULT NULL,
  `commit_committer_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_committer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_created_at` datetime(0) NULL DEFAULT NULL,
  `commit_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `commit_parent_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_short_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_stats` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_timestamp` datetime(0) NULL DEFAULT NULL,
  `commit_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_web_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `commit_project_id` bigint(20) NULL DEFAULT NULL,
  `commit_last_pipeline_id` bigint(20) NULL DEFAULT NULL,
  `commit_last_pipeline_iid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`project_id`, `host`, `name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gl_commit
-- ----------------------------
CREATE TABLE `gl_commit`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `branch_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `authored_date` datetime(0) NULL DEFAULT NULL,
  `author_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `committed_date` datetime(0) NULL DEFAULT NULL,
  `committer_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `committer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `short_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `timestamp` datetime(0) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `project_id` bigint(20) NULL DEFAULT NULL,
  `last_pipeline_id` bigint(20) NULL DEFAULT NULL,
  `last_pipeline_iid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `host`, `branch_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gl_issue
-- ----------------------------
CREATE TABLE `gl_issue`  (
  `id` bigint(20) NOT NULL,
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `iid` bigint(20) NOT NULL,
  `subscribed` tinyint(1) NULL DEFAULT NULL,
  `assignee_id` bigint(20) NULL DEFAULT NULL,
  `author_id` bigint(20) NULL DEFAULT NULL,
  `confidential` tinyint(1) NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `closed_at` datetime(0) NULL DEFAULT NULL,
  `closed_by` bigint(20) NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `due_date` datetime(0) NULL DEFAULT NULL,
  `actual_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `external_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `labels` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `milestone_id` bigint(20) NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_notes_count` int(11) NULL DEFAULT NULL,
  `web_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `weight` int(11) NULL DEFAULT NULL,
  `discussion_locked` tinyint(1) NULL DEFAULT NULL,
  `time_estimate` int(11) NULL DEFAULT NULL,
  `total_time_spent` int(11) NULL DEFAULT NULL,
  `human_time_estimate` int(0) NULL DEFAULT NULL,
  `human_total_time_spent` int(0) NULL DEFAULT NULL,
  `upvotes` int(11) NULL DEFAULT NULL,
  `downvotes` int(11) NULL DEFAULT NULL,
  `merge_requests_count` int(11) NULL DEFAULT NULL,
  `has_tasks` tinyint(1) NULL DEFAULT NULL,
  `task_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `iteration_id` bigint(20) NULL DEFAULT NULL,
  `task_completion_status_count` int(11) NULL DEFAULT NULL,
  `task_completion_status_completed_count` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `host`, `project_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gl_namespace
-- ----------------------------
CREATE TABLE `gl_namespace`  (
  `id` bigint(20) NOT NULL,
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `kind` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `full_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `host`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gl_note
-- ----------------------------
CREATE TABLE `gl_note`  (
  `id` bigint(20) NOT NULL,
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `issue_id` bigint(20) NOT NULL,
  `issue_iid` bigint(20) NOT NULL,
  `attachment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author_id` bigint(20) NULL DEFAULT NULL,
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `downvote` tinyint(1) NULL DEFAULT NULL,
  `expires_at` datetime(0) NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `noteable_id` bigint(20) NULL DEFAULT NULL,
  `noteable_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `noteable_iid` bigint(20) NULL DEFAULT NULL,
  `system` tinyint(1) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `upvote` tinyint(1) NULL DEFAULT NULL,
  `resolved` tinyint(1) NULL DEFAULT NULL,
  `resolvable` tinyint(1) NULL DEFAULT NULL,
  `resolved_by` bigint(20) NULL DEFAULT NULL,
  `resolved_at` datetime(0) NULL DEFAULT NULL,
  `internal` tinyint(1) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `host`, `project_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gl_project
-- ----------------------------
CREATE TABLE `gl_project`  (
  `id` bigint(20) NOT NULL,
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `approvals_before_merge` int(11) NULL DEFAULT NULL,
  `archived` tinyint(1) NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `container_registry_enabled` tinyint(1) NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `creator_id` bigint(20) NULL DEFAULT NULL,
  `default_branch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `forks_count` int(11) NULL DEFAULT NULL,
  `forked_from_project_id` bigint(20) NULL DEFAULT NULL,
  `http_url_to_repo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_public` tinyint(1) NULL DEFAULT NULL,
  `issues_enabled` tinyint(1) NULL DEFAULT NULL,
  `jobs_enabled` tinyint(1) NULL DEFAULT NULL,
  `last_activity_at` datetime(0) NULL DEFAULT NULL,
  `lfs_enabled` tinyint(1) NULL DEFAULT NULL,
  `merge_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `merge_requests_enabled` tinyint(1) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `namespace_id` bigint(20) NULL DEFAULT NULL,
  `name_with_namespace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `only_allow_merge_if_pipeline_succeeds` tinyint(1) NULL DEFAULT NULL,
  `only_allow_merge_if_all_discussions_are_resolved` tinyint(1) NULL DEFAULT NULL,
  `open_issues_count` int(11) NULL DEFAULT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path_with_namespace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permissions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `public_jobs` tinyint(1) NULL DEFAULT NULL,
  `repository_storage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `request_access_enabled` tinyint(1) NULL DEFAULT NULL,
  `runners_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `shared_runners_enabled` tinyint(1) NULL DEFAULT NULL,
  `shared_with_groups` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `snippets_enabled` tinyint(1) NULL DEFAULT NULL,
  `ssh_url_to_repo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `star_count` int(11) NULL DEFAULT NULL,
  `tag_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `topics` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `visibility_level` int(11) NULL DEFAULT NULL,
  `visibility` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `wall_enabled` tinyint(1) NULL DEFAULT NULL,
  `web_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `wiki_enabled` tinyint(1) NULL DEFAULT NULL,
  `printing_merge_request_link_enabled` tinyint(1) NULL DEFAULT NULL,
  `resolve_outdated_diff_discussions` tinyint(1) NULL DEFAULT NULL,
  `statistics` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `initialize_with_readme` tinyint(1) NULL DEFAULT NULL,
  `packages_enabled` tinyint(1) NULL DEFAULT NULL,
  `empty_repo` tinyint(1) NULL DEFAULT NULL,
  `license_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `custom_attributes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `build_coverage_regex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `build_git_strategy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `readme_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `can_create_merge_request_in` tinyint(1) NULL DEFAULT NULL,
  `import_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ci_default_git_depth` int(11) NULL DEFAULT NULL,
  `ci_forward_deployment_enabled` tinyint(1) NULL DEFAULT NULL,
  `ci_config_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remove_source_branch_after_merge` tinyint(1) NULL DEFAULT NULL,
  `auto_devops_enabled` tinyint(1) NULL DEFAULT NULL,
  `auto_devops_deploy_strategy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoclose_referenced_issues` tinyint(1) NULL DEFAULT NULL,
  `emails_disabled` tinyint(1) NULL DEFAULT NULL,
  `suggestion_commit_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `squash_option` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `marked_for_deletion_on` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `host`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
