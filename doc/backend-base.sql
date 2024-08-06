/*
 Navicat Premium Data Transfer

 Source Server         : MYSQL80
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : localhost:3306
 Source Schema         : backend-base

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 06/08/2024 16:53:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('user', '751TTkQR+6Gx+wkT9iR8rg==', 'RbKl0WaoB04huhlbCSR89g==', '2024-08-03 11:42:31');
INSERT INTO `persistent_logins` VALUES ('user', '94OmMUDF/ImMaNFCaEk/gg==', 'AYEHTz+ThKIDCADmLfvDyg==', '2024-08-05 20:06:38');
INSERT INTO `persistent_logins` VALUES ('user', 'b+Y1a9ojSk/WUKEY/kW7sA==', 'EYqmyOPceZZ938NKZ9AAVw==', '2024-08-05 20:08:18');
INSERT INTO `persistent_logins` VALUES ('user', 'TRBU4i+s5OxRgUG/IV0nCg==', 'KDVicdhyHgMfjgnUMEvLdQ==', '2024-08-05 20:07:04');
INSERT INTO `persistent_logins` VALUES ('user', 'Y7jD3Pt9fQ6JFtyJVUCtJw==', 'UCB4k3HUnpqrPGyijTK2Ow==', '2024-08-04 16:37:53');

-- ----------------------------
-- Table structure for tb_book
-- ----------------------------
DROP TABLE IF EXISTS `tb_book`;
CREATE TABLE `tb_book`  (
  `book_id` int NOT NULL AUTO_INCREMENT COMMENT '书籍名称',
  `book_count` int NULL DEFAULT NULL COMMENT '书籍剩余量',
  `book_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '书名',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_book
-- ----------------------------
INSERT INTO `tb_book` VALUES (1, 1, '离散数学');
INSERT INTO `tb_book` VALUES (2, 2, '计算机网络');
INSERT INTO `tb_book` VALUES (3, 1, '单片机原理');
INSERT INTO `tb_book` VALUES (4, 1, '假如给我三天光明');
INSERT INTO `tb_book` VALUES (5, 2, '原神元素反应');
INSERT INTO `tb_book` VALUES (6, 1, '论十大关系');
INSERT INTO `tb_book` VALUES (7, 2, '论十大关系');
INSERT INTO `tb_book` VALUES (8, 1, 'Mysql高级');
INSERT INTO `tb_book` VALUES (9, 1, '读书笔记');
INSERT INTO `tb_book` VALUES (10, 1, '数字电路设计');
INSERT INTO `tb_book` VALUES (12, 0, '活着');
INSERT INTO `tb_book` VALUES (13, 1, '大学生未来职业规划与');
INSERT INTO `tb_book` VALUES (14, 1, '西游记');
INSERT INTO `tb_book` VALUES (15, 1, 'Java入门到入土');

-- ----------------------------
-- Table structure for tb_book_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_book_user`;
CREATE TABLE `tb_book_user`  (
  `username` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学生姓名',
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '学生工号',
  `borrow_count` int NULL DEFAULT NULL COMMENT '剩余可借阅数量',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '图书馆用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_book_user
-- ----------------------------
INSERT INTO `tb_book_user` VALUES ('冯十一', 1, 2);
INSERT INTO `tb_book_user` VALUES ('陈十二', 2, 1);
INSERT INTO `tb_book_user` VALUES ('宋十三', 3, 2);
INSERT INTO `tb_book_user` VALUES ('杨十四', 4, 1);
INSERT INTO `tb_book_user` VALUES ('金十五', 5, 2);
INSERT INTO `tb_book_user` VALUES ('谢十六', 6, 2);
INSERT INTO `tb_book_user` VALUES ('魏十七', 7, 2);
INSERT INTO `tb_book_user` VALUES ('春江花', 8, 3);

-- ----------------------------
-- Table structure for tb_borrow
-- ----------------------------
DROP TABLE IF EXISTS `tb_borrow`;
CREATE TABLE `tb_borrow`  (
  `borrow_id` int NOT NULL AUTO_INCREMENT COMMENT '借阅id',
  `book_id` tinyint NULL DEFAULT NULL COMMENT '书籍id',
  `id` int NULL DEFAULT NULL COMMENT '借阅人工号',
  PRIMARY KEY (`borrow_id`) USING BTREE,
  INDEX `tb_borrow_tb_book_user_user_id_fk`(`id`) USING BTREE,
  CONSTRAINT `tb_borrow_tb_book_user_user_id_fk` FOREIGN KEY (`id`) REFERENCES `tb_book_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '借阅表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_borrow
-- ----------------------------
INSERT INTO `tb_borrow` VALUES (1, 3, 4);
INSERT INTO `tb_borrow` VALUES (2, 4, 2);
INSERT INTO `tb_borrow` VALUES (6, 1, 1);
INSERT INTO `tb_borrow` VALUES (7, 4, 8);
INSERT INTO `tb_borrow` VALUES (8, 11, 8);
INSERT INTO `tb_borrow` VALUES (9, 4, 8);
INSERT INTO `tb_borrow` VALUES (14, 2, 8);
INSERT INTO `tb_borrow` VALUES (16, 1, 1);

-- ----------------------------
-- Table structure for tb_sys
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys`;
CREATE TABLE `tb_sys`  (
  `id` int NULL DEFAULT NULL COMMENT '序号',
  `config_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '配置名',
  `config_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '配置键',
  `config_value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '配置内容',
  `config_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '配置类型'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '导入默认系统配置项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys
-- ----------------------------
INSERT INTO `tb_sys` VALUES (1, 'QQ邮箱号', 'spring.mail.username', '484510171@qq.com', '1');
INSERT INTO `tb_sys` VALUES (2, 'QQ邮箱授权码', 'spring.mail.password', '', '1');
INSERT INTO `tb_sys` VALUES (3, '邮箱验证码模板', 'user.code.format', '【poem】%s为本次验证的验证码，请在', '1');

-- ----------------------------
-- Table structure for tb_undo_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_undo_log`;
CREATE TABLE `tb_undo_log`  (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE,
  INDEX `ix_log_created`(`log_created`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `user_status` smallint NULL DEFAULT NULL COMMENT '[0:否,1:是]',
  `gender` smallint NULL DEFAULT NULL COMMENT '[0:保密,1:男,2:女]',
  `open_id` int NULL DEFAULT NULL COMMENT 'openId',
  `avatar` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `admire` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '赞赏',
  `subscribe` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '订阅',
  `introduction` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '简介',
  `user_type` smallint NULL DEFAULT NULL COMMENT '用户类型[0:admin,1:管理员,2:普通用户]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最终修改人',
  `deleted` smallint NULL DEFAULT NULL COMMENT '是否启用：[0:未删除,1：已删除]',
  `phone_number` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1898180612 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('admin', '$2a$10$HxUWDO2ISTbVg6YnFcj4Te0NYvDDMPlEDTEtF5K8dtHWFspQB/XKW\n', 456, 'admin', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES ('user', '$2a$10$6drVMMDiXjaWWp9PZ2Vh3etDBKXWuUCAWSP9pRxuVNWKq5dpshJkW', 1898180611, 'amixrip@163.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
