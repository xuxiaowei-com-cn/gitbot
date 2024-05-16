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

 Date: 16/05/2024 15:14:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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

SET FOREIGN_KEY_CHECKS = 1;
