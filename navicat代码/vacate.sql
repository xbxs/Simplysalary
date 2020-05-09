/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : simplysalary

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 09/05/2020 10:34:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vacate
-- ----------------------------
DROP TABLE IF EXISTS `vacate`;
CREATE TABLE `vacate`  (
  `v_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_rtime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_term` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_shift` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_section` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_reason` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `v_tuser` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_etime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v_btime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`v_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vacate
-- ----------------------------
INSERT INTO `vacate` VALUES (16, '15543511075', '2020-05-08 08:30', '1.0', '1', '114778039189505', 'hhh ', '15643084346', '3', '2020-05-08 09:30', '5', '2020-05-08 08:29');
INSERT INTO `vacate` VALUES (17, '15543511075', '2020-05-09 07:27', '49.0', '1', '114778039189505', '有事', '15643084346', '3', '2020-05-11 08:27', '1', '2020-05-09 07:27');
INSERT INTO `vacate` VALUES (18, '15543511075', '2020-05-09 07:32', '3.0', '1', '114778039189505', '有事', '15643084346', '2', '2020-05-09 10:31', '5', '2020-05-09 07:31');
INSERT INTO `vacate` VALUES (19, '15543511075', '2020-05-09 07:33', '3.0', '1', '114778039189505', '有事', '15643084346', '3', '2020-05-09 10:33', '1', '2020-05-09 07:33');

SET FOREIGN_KEY_CHECKS = 1;
