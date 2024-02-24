/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50739
 Source Host           : localhost:3306
 Source Schema         : bootdemo

 Target Server Type    : MySQL
 Target Server Version : 50739
 File Encoding         : 65001

 Date: 23/02/2024 15:08:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `book_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '书籍名称',
  `book_count` int(11) NULL DEFAULT NULL COMMENT '书籍剩余量',
  `book_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '书名',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 1, '离散数学');
INSERT INTO `book` VALUES (2, 2, '计算机网络');
INSERT INTO `book` VALUES (3, 1, '单片机原理');
INSERT INTO `book` VALUES (4, 1, '假如给我三天光明');
INSERT INTO `book` VALUES (5, 2, '原神元素反应');
INSERT INTO `book` VALUES (6, 1, '论十大关系');
INSERT INTO `book` VALUES (7, 2, '论十大关系');
INSERT INTO `book` VALUES (8, 1, 'Mysql高级');
INSERT INTO `book` VALUES (9, 1, '读书笔记');
INSERT INTO `book` VALUES (10, 1, '数字电路设计');
INSERT INTO `book` VALUES (12, 0, '活着');
INSERT INTO `book` VALUES (13, 1, '大学生未来职业规划与');
INSERT INTO `book` VALUES (14, 1, '西游记');
INSERT INTO `book` VALUES (15, 1, 'Java入门到入土');

-- ----------------------------
-- Table structure for book_user
-- ----------------------------
DROP TABLE IF EXISTS `book_user`;
CREATE TABLE `book_user`  (
  `username` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学生姓名',
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生工号',
  `borrow_count` int(11) NULL DEFAULT NULL COMMENT '剩余可借阅数量',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '图书馆用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_user
-- ----------------------------
INSERT INTO `book_user` VALUES ('冯十一', 1, 2);
INSERT INTO `book_user` VALUES ('陈十二', 2, 1);
INSERT INTO `book_user` VALUES ('宋十三', 3, 2);
INSERT INTO `book_user` VALUES ('杨十四', 4, 1);
INSERT INTO `book_user` VALUES ('金十五', 5, 2);
INSERT INTO `book_user` VALUES ('谢十六', 6, 2);
INSERT INTO `book_user` VALUES ('魏十七', 7, 2);
INSERT INTO `book_user` VALUES ('春江花', 8, 3);

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `borrow_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '借阅id',
  `book_id` tinyint(4) NULL DEFAULT NULL COMMENT '书籍id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '借阅人工号',
  PRIMARY KEY (`borrow_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '借阅表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (1, 3, 4);
INSERT INTO `borrow` VALUES (2, 4, 2);
INSERT INTO `borrow` VALUES (6, 1, 1);
INSERT INTO `borrow` VALUES (7, 4, 8);
INSERT INTO `borrow` VALUES (8, 11, 8);
INSERT INTO `borrow` VALUES (9, 4, 8);
INSERT INTO `borrow` VALUES (14, 2, 8);
INSERT INTO `borrow` VALUES (16, 1, 1);

-- ----------------------------
-- Table structure for pray_message
-- ----------------------------
DROP TABLE IF EXISTS `pray_message`;
CREATE TABLE `pray_message`  (
  `administrator_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `administrator_id` int(11) NOT NULL,
  `contents` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `publish_time` datetime(0) NULL DEFAULT NULL,
  `expire_time` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '公告通知' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pray_message
-- ----------------------------
INSERT INTO `pray_message` VALUES ('米哈游', 1, '初期版本，BUG较多', '2024-01-10 00:46:25', '2023-09-12 00:46:27');
INSERT INTO `pray_message` VALUES ('米哈游', 2, '原神启动，取决于你的热爱程度', '2023-09-10 00:46:23', '2023-09-10 00:46:32');
INSERT INTO `pray_message` VALUES ('米哈游', 3, '你知道吗，枫丹更新了！！！', '2023-09-10 00:46:26', '2023-09-10 00:46:34');

-- ----------------------------
-- Table structure for resident
-- ----------------------------
DROP TABLE IF EXISTS `resident`;
CREATE TABLE `resident`  (
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `resident_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `resident_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `resident_star-level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `resident_count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `resident_bottom` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `resident_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resident
-- ----------------------------
INSERT INTO `resident` VALUES ('2021-07-20 15:36:44', '魔导绪论', '武器', '3', '1', '1', '1626764760000028921');
INSERT INTO `resident` VALUES ('2021-07-20 15:37:04', '飞天御剑', '武器', '3', '2', '2', '1626764760000029100');
INSERT INTO `resident` VALUES ('2021-07-20 15:37:32', '飞天御剑', '武器', '3', '3', '3', '1626764760000029437');
INSERT INTO `resident` VALUES ('2021-07-23 11:40:07', '冷刃', '武器', '3', '4', '4', '1627009560000117401');
INSERT INTO `resident` VALUES ('2021-08-24 09:40:25', '铁影阔剑', '武器', '3', '34', '34', '1629767160000047168');
INSERT INTO `resident` VALUES ('2021-10-19 00:03:24', '神射手之誓', '武器', '3', '60', '15', '1634573160000006201');
INSERT INTO `resident` VALUES ('2021-12-12 09:48:17', '冷刃', '武器', '3', '61', '16', '1639271160000132834');
INSERT INTO `resident` VALUES ('2021-12-12 09:48:21', '魔导绪论', '武器', '3', '62', '17', '1639271160000133934');
INSERT INTO `resident` VALUES ('2021-12-14 19:12:23', '讨龙英杰谭', '武器', '3', '63', '18', '1639479960000205234');
INSERT INTO `resident` VALUES ('2021-12-15 12:50:21', '铁影阔剑', '武器', '3', '64', '19', '1639541160000129334');
INSERT INTO `resident` VALUES ('2021-12-17 08:08:58', '冷刃', '武器', '3', '65', '20', '1639699560000005634');
INSERT INTO `resident` VALUES ('2021-12-17 20:26:34', '流浪乐章', '武器', '4', '66', '21', '1639742760000160234');
INSERT INTO `resident` VALUES ('2021-12-23 17:38:20', '钟剑', '武器', '4', '67', '22', '1640250360000025934');
INSERT INTO `resident` VALUES ('2021-12-25 00:20:24', '祭礼剑', '武器', '4', '68', '23', '1640361960000074634');
INSERT INTO `resident` VALUES ('2021-12-25 00:20:34', '祭礼残章', '武器', '4', '69', '24', '1640361960000075734');
INSERT INTO `resident` VALUES ('2021-12-26 13:18:37', '冷刃', '武器', '3', '70', '25', '1640495160000029834');
INSERT INTO `resident` VALUES ('2021-12-28 20:20:06', '黎明神剑', '武器', '3', '71', '26', '1640693160000021034');
INSERT INTO `resident` VALUES ('2021-12-31 08:27:58', '讨龙英杰谭', '武器', '3', '72', '27', '1640909160000011234');
INSERT INTO `resident` VALUES ('2022-01-05 11:40:17', '翡玉法球', '武器', '3', '73', '28', '1641351960005237934');
INSERT INTO `resident` VALUES ('2022-01-07 13:35:19', '冷刃', '武器', '3', '74', '29', '1641531960000150534');
INSERT INTO `resident` VALUES ('2022-01-07 20:36:21', '弹弓', '武器', '3', '75', '30', '1641557160000414434');
INSERT INTO `resident` VALUES ('2022-01-07 20:36:28', '以理服人', '武器', '3', '76', '31', '1641557160000415734');


-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `role_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `role_star_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `role_count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `role_bottom` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('2022-08-15 23:07:31', '铁影阔剑', '武器', '3', '401', '25', '1660575960000014234');
INSERT INTO `role` VALUES ('2022-08-16 22:15:57', '沐浴龙血的剑', '武器', '3', '402', '26', '1660658760000048234');
INSERT INTO `role` VALUES ('2022-08-16 22:16:07', '班尼特', '角色', '4', '403', '27', '1660658760000048534');
INSERT INTO `role` VALUES ('2022-08-16 23:00:06', '以理服人', '武器', '3', '404', '28', '1660662360000000334');
INSERT INTO `role` VALUES ('2022-08-18 20:31:52', '以理服人', '武器', '3', '405', '29', '1660824360000075534');
INSERT INTO `role` VALUES ('2022-08-18 21:23:25', '魔导绪论', '武器', '3', '406', '30', '1660827960000045834');
INSERT INTO `role` VALUES ('2022-08-18 21:23:55', '黑缨枪', '武器', '3', '407', '31', '1660827960000048634');
INSERT INTO `role` VALUES ('2022-08-22 16:58:21', '魔导绪论', '武器', '3', '408', '32', '1661155560000103134');
INSERT INTO `role` VALUES ('2022-08-24 10:50:09', '翡玉法球', '武器', '3', '409', '33', '1661306760001510834');
INSERT INTO `role` VALUES ('2022-08-24 10:50:21', '冷刃', '武器', '3', '410', '34', '1661306760001526634');
INSERT INTO `role` VALUES ('2022-08-15 23:07:31', '铁影阔剑', '武器', '3', '401', '25', '1660575960000014234');
INSERT INTO `role` VALUES ('2022-08-16 22:15:57', '沐浴龙血的剑', '武器', '3', '402', '26', '1660658760000048234');
INSERT INTO `role` VALUES ('2022-08-16 22:16:07', '班尼特', '角色', '4', '403', '27', '1660658760000048534');
INSERT INTO `role` VALUES ('2022-08-16 23:00:06', '以理服人', '武器', '3', '404', '28', '1660662360000000334');
INSERT INTO `role` VALUES ('2022-08-18 20:31:52', '以理服人', '武器', '3', '405', '29', '1660824360000075534');
INSERT INTO `role` VALUES ('2022-08-18 21:23:25', '魔导绪论', '武器', '3', '406', '30', '1660827960000045834');
INSERT INTO `role` VALUES ('2022-08-18 21:23:55', '黑缨枪', '武器', '3', '407', '31', '1660827960000048634');
INSERT INTO `role` VALUES ('2022-08-22 16:58:21', '魔导绪论', '武器', '3', '408', '32', '1661155560000103134');
INSERT INTO `role` VALUES ('2022-08-24 10:50:09', '翡玉法球', '武器', '3', '409', '33', '1661306760001510834');
INSERT INTO `role` VALUES ('2022-08-24 10:50:21', '冷刃', '武器', '3', '410', '34', '1661306760001526634');
INSERT INTO `role` VALUES ('2022-08-15 23:07:31', '铁影阔剑', '武器', '3', '401', '25', '1660575960000014234');
INSERT INTO `role` VALUES ('2022-08-16 22:15:57', '沐浴龙血的剑', '武器', '3', '402', '26', '1660658760000048234');
INSERT INTO `role` VALUES ('2022-08-16 22:16:07', '班尼特', '角色', '4', '403', '27', '1660658760000048534');
INSERT INTO `role` VALUES ('2022-08-16 23:00:06', '以理服人', '武器', '3', '404', '28', '1660662360000000334');
INSERT INTO `role` VALUES ('2022-08-18 20:31:52', '以理服人', '武器', '3', '405', '29', '1660824360000075534');
INSERT INTO `role` VALUES ('2022-08-18 21:23:25', '魔导绪论', '武器', '3', '406', '30', '1660827960000045834');
INSERT INTO `role` VALUES ('2022-08-18 21:23:55', '黑缨枪', '武器', '3', '407', '31', '1660827960000048634');
INSERT INTO `role` VALUES ('2022-08-22 16:58:21', '魔导绪论', '武器', '3', '408', '32', '1661155560000103134');
INSERT INTO `role` VALUES ('2022-08-24 10:50:09', '翡玉法球', '武器', '3', '409', '33', '1661306760001510834');
INSERT INTO `role` VALUES ('2022-08-24 10:50:21', '冷刃', '武器', '3', '410', '34', '1661306760001526634');
INSERT INTO `role` VALUES ('2022-08-15 23:07:31', '铁影阔剑', '武器', '3', '401', '25', '1660575960000014234');
INSERT INTO `role` VALUES ('2022-08-16 22:15:57', '沐浴龙血的剑', '武器', '3', '402', '26', '1660658760000048234');
INSERT INTO `role` VALUES ('2022-08-16 22:16:07', '班尼特', '角色', '4', '403', '27', '1660658760000048534');
INSERT INTO `role` VALUES ('2022-08-16 23:00:06', '以理服人', '武器', '3', '404', '28', '1660662360000000334');
INSERT INTO `role` VALUES ('2022-08-18 20:31:52', '以理服人', '武器', '3', '405', '29', '1660824360000075534');
INSERT INTO `role` VALUES ('2022-08-18 21:23:25', '魔导绪论', '武器', '3', '406', '30', '1660827960000045834');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE,
  INDEX `ix_log_created`(`log_created`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1898180612 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin', '$2a$10$HxUWDO2ISTbVg6YnFcj4Te0NYvDDMPlEDTEtF5K8dtHWFspQB/XKW\n', 456, 'admin', 1);
INSERT INTO `user` VALUES ('user', '$2a$10$6drVMMDiXjaWWp9PZ2Vh3etDBKXWuUCAWSP9pRxuVNWKq5dpshJkW', 1898180611, 'amixrip@163.com', 1);

-- ----------------------------
-- Table structure for weapon
-- ----------------------------
DROP TABLE IF EXISTS `weapon`;
CREATE TABLE `weapon`  (
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `weapon_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `weapon_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `weapon_start_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `weapon_count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `weapon_bottom` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `weapon_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of weapon
-- ----------------------------
INSERT INTO `weapon` VALUES ('2021-12-10 08:02:43', '苍古自由之誓', '武器', '5', '1', '1', '1639094760000001534');
INSERT INTO `weapon` VALUES ('2021-12-10 08:05:00', '暗巷的酒与诗', '武器', '4', '2', '1', '1639094760000003334');
INSERT INTO `weapon` VALUES ('2021-12-10 08:05:07', '铁影阔剑', '武器', '3', '3', '2', '1639094760000003534');
INSERT INTO `weapon` VALUES ('2021-12-11 07:47:17', '弹弓', '武器', '3', '4', '3', '1639177560000070134');
INSERT INTO `weapon` VALUES ('2021-12-11 07:47:22', '鸦羽弓', '武器', '3', '5', '4', '1639177560000070334');
INSERT INTO `weapon` VALUES ('2021-12-11 07:47:28', '沐浴龙血的剑', '武器', '3', '6', '5', '1639177560000070434');
INSERT INTO `weapon` VALUES ('2021-12-11 07:47:40', '讨龙英杰谭', '武器', '3', '7', '6', '1639177560000070734');
INSERT INTO `weapon` VALUES ('2021-12-12 09:35:03', '讨龙英杰谭', '武器', '3', '8', '7', '1639271160000102734');
INSERT INTO `weapon` VALUES ('2021-12-12 09:35:08', '翡玉法球', '武器', '3', '9', '8', '1639271160000102934');
INSERT INTO `weapon` VALUES ('2021-12-12 09:48:56', '诺艾尔', '角色', '4', '10', '9', '1639271160000136434');
INSERT INTO `weapon` VALUES ('2021-12-13 08:33:40', '神射手之誓', '武器', '3', '11', '10', '1639353960000064534');
INSERT INTO `weapon` VALUES ('2021-12-13 08:33:45', '讨龙英杰谭', '武器', '3', '12', '11', '1639353960000064734');
INSERT INTO `weapon` VALUES ('2021-12-13 08:52:46', '松籁响起之时', '武器', '5', '13', '12', '1639353960000100034');
INSERT INTO `weapon` VALUES ('2021-12-23 17:39:20', '铁影阔剑', '武器', '3', '14', '1', '1640250360000026134');
INSERT INTO `weapon` VALUES ('2021-12-24 15:22:58', '沐浴龙血的剑', '武器', '3', '15', '2', '1640329560000025334');
INSERT INTO `weapon` VALUES ('2021-12-25 00:16:12', '黑缨枪', '武器', '3', '16', '3', '1640361960000058234');
INSERT INTO `weapon` VALUES ('2021-12-25 00:23:35', '黎明神剑', '武器', '3', '17', '4', '1640361960000077634');
INSERT INTO `weapon` VALUES ('2021-12-26 12:38:51', '神射手之誓', '武器', '3', '18', '5', '1640491560000082634');
INSERT INTO `weapon` VALUES ('2021-12-26 16:21:44', '祭礼残章', '武器', '4', '19', '6', '1640505960000042234');
INSERT INTO `weapon` VALUES ('2022-01-31 07:54:35', '以理服人', '武器', '3', '20', '7', '1643583960000255034');
INSERT INTO `weapon` VALUES ('2022-02-26 09:09:12', '鸦羽弓', '武器', '3', '21', '8', '1645837560000016734');
INSERT INTO `weapon` VALUES ('2022-03-01 13:53:20', '黑缨枪', '武器', '3', '22', '9', '1646111160000212234');
INSERT INTO `weapon` VALUES ('2022-03-01 14:05:11', '沐浴龙血的剑', '武器', '3', '23', '10', '1646114760000009634');
INSERT INTO `weapon` VALUES ('2022-03-02 07:37:03', '铁影阔剑', '武器', '3', '24', '11', '1646175960000018334');
INSERT INTO `weapon` VALUES ('2022-03-02 07:37:17', '鸦羽弓', '武器', '3', '25', '12', '1646175960000018434');


SET FOREIGN_KEY_CHECKS = 1;
