/*
 Navicat Premium Data Transfer

 Source Server         : im
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost
 Source Database       : im

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 07/01/2019 00:04:46 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `city`
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `province_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `friend_id` int(11) NOT NULL COMMENT '朋友的ID',
  `user_id` int(11) NOT NULL COMMENT ' 自己的ID',
  `name` varchar(255) DEFAULT NULL COMMENT '备注昵称 ',
  `friend_type_id` int(11) DEFAULT NULL COMMENT '好友类型',
  `friend_group_id` int(11) DEFAULT NULL COMMENT '所属分组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `friend_group`
-- ----------------------------
DROP TABLE IF EXISTS `friend_group`;
CREATE TABLE `friend_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '分组名字',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `friend_type`
-- ----------------------------
DROP TABLE IF EXISTS `friend_type`;
CREATE TABLE `friend_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `friendship_policy`
-- ----------------------------
DROP TABLE IF EXISTS `friendship_policy`;
CREATE TABLE `friendship_policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `friendship_policy` varchar(255) DEFAULT NULL COMMENT ' 好友添加方式    ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `group_message`
-- ----------------------------
DROP TABLE IF EXISTS `group_message`;
CREATE TABLE `group_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `from_user_id` int(11) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `group_message_to_user`
-- ----------------------------
DROP TABLE IF EXISTS `group_message_to_user`;
CREATE TABLE `group_message_to_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `group_message_id` int(11) DEFAULT NULL,
  `sate` bit(1) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `group_message_user_to_user`
-- ----------------------------
DROP TABLE IF EXISTS `group_message_user_to_user`;
CREATE TABLE `group_message_user_to_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(11) DEFAULT NULL,
  `from_user_name` varchar(255) DEFAULT NULL,
  `to_user_id` int(11) DEFAULT NULL,
  `content` text,
  `state` bit(1) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `user_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `group_to_user`
-- ----------------------------
DROP TABLE IF EXISTS `group_to_user`;
CREATE TABLE `group_to_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `to_user_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `group_user_nick` varchar(255) DEFAULT NULL COMMENT '群内用户昵称 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_message` text COMMENT '消息内容',
  `status` bit(1) DEFAULT NULL COMMENT '接收状态',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `message_type_id` int(11) DEFAULT NULL COMMENT '消息类型ID',
  `from_user_id` int(11) DEFAULT NULL,
  `to_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `message_type`
-- ----------------------------
DROP TABLE IF EXISTS `message_type`;
CREATE TABLE `message_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `nation`
-- ----------------------------
DROP TABLE IF EXISTS `nation`;
CREATE TABLE `nation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `province`
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `nation_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键、自增',
  `login_name` varchar(20) NOT NULL COMMENT '登陆账号',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `pass_word` varchar(20) NOT NULL COMMENT '密码',
  `signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `sex` bit(1) NOT NULL COMMENT '性别',
  `birthday` datetime NOT NULL COMMENT '生日',
  `telephone` varchar(11) NOT NULL COMMENT '电话',
  `name` varchar(20) NOT NULL COMMENT '真实姓名',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `intro` varchar(255) DEFAULT NULL COMMENT '简介',
  `head_portrait` varchar(255) DEFAULT NULL COMMENT '头像',
  `zodiac` char(2) NOT NULL COMMENT '生肖    ',
  `age` int(11) NOT NULL COMMENT '年龄    ',
  `constellation` char(6) DEFAULT NULL COMMENT '星座    ',
  `blood_type` varchar(10) DEFAULT NULL COMMENT '血型    ',
  `school_tag` varchar(20) DEFAULT NULL COMMENT '毕业学校',
  `vocation` varchar(30) DEFAULT NULL COMMENT '职业',
  `nation_id` int(11) DEFAULT NULL COMMENT '国家ID',
  `province_id` int(11) DEFAULT NULL COMMENT '省份ID',
  `city_id` int(11) DEFAULT NULL COMMENT '城市ID',
  `user_state_id` int(11) DEFAULT NULL COMMENT '用户状态ID',
  `friendship_policy_id` int(11) DEFAULT NULL COMMENT '好友策略ID',
  `friend_policy_question` varchar(255) DEFAULT NULL,
  `friend_policy_answer` varchar(255) DEFAULT NULL COMMENT ' 好友策略答案 ',
  `friend_policy_password` varchar(255) DEFAULT NULL COMMENT ' 好友策略密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_group`
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '群名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间 ',
  `admin_id` int(11) DEFAULT NULL COMMENT ' 群主ID',
  `icon` varchar(255) DEFAULT NULL COMMENT '  群图标',
  `notice` varchar(255) DEFAULT NULL COMMENT '群公告 ',
  `intro` varchar(255) DEFAULT NULL COMMENT '群简介  ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_state`
-- ----------------------------
DROP TABLE IF EXISTS `user_state`;
CREATE TABLE `user_state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL COMMENT '状态名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
