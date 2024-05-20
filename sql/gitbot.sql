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

 Date: 20/05/2024 15:22:32
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
-- Table structure for gl_namespace
-- ----------------------------
CREATE TABLE `gl_namespace`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `kind` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `full_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gl_project
-- ----------------------------
CREATE TABLE `gl_project`  (
  `id` bigint(20) NOT NULL,
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
  `is_public` bigint(1) NULL DEFAULT NULL,
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
