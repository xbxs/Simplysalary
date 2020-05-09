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

 Date: 09/05/2020 10:33:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `s_id` int(8) NOT NULL AUTO_INCREMENT,
  `u_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_time` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_shift` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_term` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`s_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (26, '15543511075', '20200506', '2', '11:28-17:00');
INSERT INTO `schedule` VALUES (27, '18050691867', '20200507', '2', '11:28-17:00');
INSERT INTO `schedule` VALUES (28, '15543511075', '20200507', '3', '17:00-22:00');
INSERT INTO `schedule` VALUES (29, '15543511075', '20200508', '2', '11:28-17:00');
INSERT INTO `schedule` VALUES (30, '15543511075', '20200509', '1', '9:00-12:00');
INSERT INTO `schedule` VALUES (31, '15543511075', '20200509', '2', '11:28-17:00');

SET FOREIGN_KEY_CHECKS = 1;
