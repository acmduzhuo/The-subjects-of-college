/*
Navicat MySQL Data Transfer

Source Server         : QQDemo
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : qq

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-01 09:02:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for friends
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `id1` bigint(20) NOT NULL,
  `id2` bigint(20) NOT NULL
);

-- ----------------------------
-- Records of friends
-- ----------------------------
INSERT INTO `friends` VALUES ('9999', '5555');
INSERT INTO `friends` VALUES ('5555', '9999');
INSERT INTO `friends` VALUES ('4444', '8888');
INSERT INTO `friends` VALUES ('8888', '4444');
INSERT INTO `friends` VALUES ('7777', '8989');
INSERT INTO `friends` VALUES ('8989', '7777');
INSERT INTO `friends` VALUES ('1313', '6969');
INSERT INTO `friends` VALUES ('6969', '1313');
INSERT INTO `friends` VALUES ('123456', '5555');
INSERT INTO `friends` VALUES ('5555', '123456');
INSERT INTO `friends` VALUES ('827652549', '1111');
INSERT INTO `friends` VALUES ('1111', '827652549');
INSERT INTO `friends` VALUES ('8886', '8887');
INSERT INTO `friends` VALUES ('8887', '8886');
INSERT INTO `friends` VALUES ('827652549', '5555');
INSERT INTO `friends` VALUES ('5555', '827652549');
INSERT INTO `friends` VALUES ('9999', '123456');
INSERT INTO `friends` VALUES ('123456', '9999');
INSERT INTO `friends` VALUES ('789789', '123456');
INSERT INTO `friends` VALUES ('123456', '789789');
INSERT INTO `friends` VALUES ('123456', '1111');
INSERT INTO `friends` VALUES ('1111', '123456');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `motto` varchar(255) DEFAULT '' COMMENT '个性签名',
  `Nationality` varchar(255) DEFAULT '',
  `country` varchar(255) DEFAULT '',
  `province` varchar(255) DEFAULT '',
  `city` varchar(255) DEFAULT ''
);

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('827652549', '1', '苏一恒', '知人者智，自知者明。胜人者有力，自胜者强。', '回族', '中国', '河南省', '周口市');
INSERT INTO `user` VALUES ('10063222', '123456', '程佩', '谁有我胖子耳亮', '汉族', '中国', '河南省', '郑州市');
INSERT INTO `user` VALUES ('486454645', '123456', '范秉洋', '那胖子总欺负我', '', '', '', '');
INSERT INTO `user` VALUES ('123456', '123456', '汪健', '不diao造', '汉族', '中国', '河南省', '信阳市');
INSERT INTO `user` VALUES ('1111', '1111', '王烨', '哎，对！我骄傲！', '神族', '美国', '德克萨斯', '新内几');
INSERT INTO `user` VALUES ('2222', '2222', '李滢海', '我真想一巴掌抽死你~', '', '', '', '');
INSERT INTO `user` VALUES ('5555', '5555', '周乐善', '嗯~嗯~嗯~嗯~', '', '', '', '');
INSERT INTO `user` VALUES ('4444', '4444', '司庆磊', '我觉得吧~balabala……', '', '', '', '');
INSERT INTO `user` VALUES ('8888', '8888', '周润发', '', '', '', '', '');
INSERT INTO `user` VALUES ('9999', '9999', '张家辉', '大家好，我是渣渣辉', '', '', '', '');
INSERT INTO `user` VALUES ('789', '789', '周杰伦', '一首彩虹送给你', '', '', '', '');
INSERT INTO `user` VALUES ('6666', '6666', '小六子', '', '满族', '', '', '');
INSERT INTO `user` VALUES ('7777', '7777', '七哥', '', '', '', '', '');
INSERT INTO `user` VALUES ('8989', '8989', '九爷', '', '', '', '', '');
INSERT INTO `user` VALUES ('1313', '1313', '十三爷', '', '', '', '', '');
INSERT INTO `user` VALUES ('6969', '6969', '小吴', '', '', '', '', '');
INSERT INTO `user` VALUES ('7575', '7575', '小瓶子', '', '', '', '', '');
INSERT INTO `user` VALUES ('5252', '5252', '好人哥', '', '', '', '', '');
INSERT INTO `user` VALUES ('9527', '9527', '孔明', '等等', '等等', '等等', '等等', '等等');
INSERT INTO `user` VALUES ('456', '456', '周瑜', '', '', '', '', '');
INSERT INTO `user` VALUES ('7676', '7676', '张三', '', '', '', '', '');
INSERT INTO `user` VALUES ('9393', '9393', '王强', '', '', '', '', '');
INSERT INTO `user` VALUES ('8885', '8885', '8885', '', '', '', '', '');
INSERT INTO `user` VALUES ('8886', '8886', '张思', '', '', '', '', '');
INSERT INTO `user` VALUES ('8887', '8887', '李网', '', '', '', '', '');
INSERT INTO `user` VALUES ('9999', '9999', '酒', '', '', '', '', '');
INSERT INTO `user` VALUES ('789789', '789789', '尚涛', '', '', '', '', '');
