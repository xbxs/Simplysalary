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

 Date: 09/05/2020 10:33:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for salary
-- ----------------------------
DROP TABLE IF EXISTS `salary`;
CREATE TABLE `salary`  (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_rtime` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_term` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_shift` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_section` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_wage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`s_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of salary
-- ----------------------------
INSERT INTO `salary` VALUES (27, '18050691867', '2020-05-07 15:21', '2.5', '1', '114738980782081', '12');
INSERT INTO `salary` VALUES (28, '18050691867', '2020-05-07 15:21', '2', '1', '114738980782081', '12');
INSERT INTO `salary` VALUES (29, '18050691867', '2020-05-07 21:48', '4.5', '1', '114778039189505', '12');
INSERT INTO `salary` VALUES (30, '18050691867', '2020-05-07 21:48', '6', '2', '114778039189505', '12');
INSERT INTO `salary` VALUES (31, '15543511075', '2020-05-07 21:57', '3.5', '1', '114778039189505', '20');
INSERT INTO `salary` VALUES (32, '15543511075', '2020-05-07 21:57', '2', '1', '114778039189505', '20');
INSERT INTO `salary` VALUES (33, '18050691867', '2020-05-08 17:01', '5', '1', '114778039189505', '12');
INSERT INTO `salary` VALUES (34, '15543511075', '2020-05-08 17:01', '3', '1', '114778039189505', '20');
INSERT INTO `salary` VALUES (35, '15543511075', '2020-05-09 07:28', '4', '1', '114778039189505', '20');
INSERT INTO `salary` VALUES (36, '15543511075', '2020-05-09 07:29', '2', '1', '114778039189505', '20');
INSERT INTO `salary` VALUES (37, '15543511075', '2020-05-09 07:34', '2', '1', '114778039189505', '20');
INSERT INTO `salary` VALUES (38, '15543511075', '2020-05-09 07:34', '2', '1', '114778039189505', '20');

SET FOREIGN_KEY_CHECKS = 1;
