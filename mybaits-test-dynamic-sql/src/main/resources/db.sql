
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `nick_name` varchar(64) DEFAULT NULL,
  `signature` varchar(512) DEFAULT NULL,
  `email` varchar(256) NOT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(4) NOT NULL DEFAULT '0',
  `expire_time` date NOT NULL DEFAULT '9999-12-01',
  `create_time` datetime NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` datetime NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_username` (`username`),
  KEY `idx_username` (`username`),
  KEY `idx_email` (`email`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', 'administrator', '管理员', 'admin@test.com', '010-03838232', '3bb04e43e6f8b6775d3fb125b3aa02f6', 'gT1jO9zW5oY7', 0, '9999-12-01', '2019-12-23 15:31:38', NULL, NULL, '2019-12-23 15:31:46', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `create_time` date NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` date NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_role_name` (`role_name`) USING BTREE,
  KEY `idx_role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'admin', '管理员角色', '2019-12-23', NULL, NULL, '2019-12-23', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(64) NOT NULL,
  `permission_value` varchar(64) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `create_time` date NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` date NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_permission_name` (`permission_name`),
  KEY `idx_permission_name` (`permission_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, 'queryArticle', 'article:query', NULL, '2019-12-23', NULL, NULL, '2019-12-23', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (2, 'deleteArticle', 'article:delete', NULL, '2019-12-23', NULL, NULL, '2019-12-23', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (3, 'updateArticle', 'article:update', NULL, '2019-12-23', NULL, NULL, '2019-12-23', NULL, NULL, 0);
COMMIT;


-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) NOT NULL,
  `permission_id` int(20) NOT NULL,
  `create_time` date NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` date NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_role_id_permission_id` (`role_id`,`permission_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (1, 1, 1, '2019-12-23', NULL, NULL, '2019-12-23', NULL, NULL, 0);
INSERT INTO `sys_role_permission` VALUES (2, 1, 2, '2019-12-23', NULL, NULL, '2019-12-23', NULL, NULL, 0);
INSERT INTO `sys_role_permission` VALUES (3, 1, 3, '2019-12-23', NULL, NULL, '2019-12-23', NULL, NULL, 0);
COMMIT;


-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `create_time` date NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` date NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `un_user_id_role_id` (`role_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '2019-12-23', NULL, NULL, '2019-12-23', NULL, NULL, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;