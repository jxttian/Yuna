-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.35 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 yuna 的数据库结构
CREATE DATABASE IF NOT EXISTS `yuna` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `yuna`;

-- 导出  表 yuna.tb_business_system 结构
CREATE TABLE IF NOT EXISTS `tb_business_system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL COMMENT '编码，定位中的x坐标',
  `name` varchar(64) NOT NULL COMMENT '名称，显示用',
  `domain` varchar(64) NOT NULL DEFAULT '1' COMMENT '域名',
  `rank` int(11) NOT NULL DEFAULT '1' COMMENT '等级分值 0为不显示，分值越高越靠前',
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `modification_time` datetime DEFAULT NULL,
  `modifier` int(11) DEFAULT NULL,
  `memo` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 yuna.tb_permission 结构
CREATE TABLE IF NOT EXISTS `tb_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '上级权限ID',
  `sid` int(11) NOT NULL COMMENT '系统',
  `code` varchar(32) NOT NULL COMMENT '资源编码  定位中y坐标',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0:InnerPage 1:OuterPage 2:Button 3:Tag',
  `icon` varchar(64) DEFAULT 'fa fa-sun-o',
  `name` varchar(64) NOT NULL,
  `value` varchar(256) NOT NULL DEFAULT '',
  `regular` varchar(256) DEFAULT '' COMMENT '权限正则匹配',
  `rank` int(11) NOT NULL DEFAULT '1' COMMENT '等级分值 0为不显示，分值越高越靠前',
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `modification_time` datetime DEFAULT NULL,
  `modifier` int(11) DEFAULT NULL,
  `memo` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_code_code_uindex` (`sid`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 yuna.tb_role 结构
CREATE TABLE IF NOT EXISTS `tb_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `permissions` varchar(512) DEFAULT NULL COMMENT '角色拥有的权限，逗号分隔',
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `modification_time` datetime DEFAULT NULL,
  `modifier` int(11) DEFAULT NULL,
  `memo` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 yuna.tb_user 结构
CREATE TABLE IF NOT EXISTS `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL COMMENT '邮箱',
  `phone` varchar(20) NOT NULL COMMENT '电话号码',
  `password` varchar(32) NOT NULL COMMENT 'MD5后的密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `tags` varchar(64) DEFAULT NULL COMMENT '用户标签',
  `roles` varchar(256) DEFAULT NULL COMMENT '用户绑定的角色，逗号分隔',
  `flags` int(11) DEFAULT NULL COMMENT '二进制标签',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `modification_time` datetime DEFAULT NULL,
  `modifier` int(11) DEFAULT NULL,
  `memo` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


INSERT INTO `tb_business_system` (`code`, `name`, `domain`, `rank`, `creation_time`, `modification_time`) VALUES ('system', '系统管理', 'http://cms.yuna.com:8080', '999', '2017-04-11 11:23:50', '2017-04-11 11:23:52');

INSERT INTO `tb_permission` (`pid`, `sid`, `code`, `type`, `icon`, `name`, `value`, `regular`, `rank`, `enable`, `creation_time`, `creator`, `modification_time`, `modifier`, `memo`) VALUES (0, 3, 'business_system', 1, 'fa fa-gears', '业务系统', '/system/index.html', '/system/', 999, 1, '2017-04-11 11:27:54', NULL, '2017-04-19 11:04:42', 2, NULL);
INSERT INTO `tb_permission` (`pid`, `sid`, `code`, `type`, `icon`, `name`, `value`, `regular`, `rank`, `enable`, `creation_time`, `creator`, `modification_time`, `modifier`, `memo`) VALUES (0, 3, 'user', 1, 'fa fa-users', '用户管理', '/user/index.html', '/user/', 996, 1, '2017-04-11 15:23:04', NULL, '2017-04-19 11:05:09', 2, NULL);
INSERT INTO `tb_permission` (`pid`, `sid`, `code`, `type`, `icon`, `name`, `value`, `regular`, `rank`, `enable`, `creation_time`, `creator`, `modification_time`, `modifier`, `memo`) VALUES (0, 3, 'permission', 1, 'fa fa-user-secret', '权限管理', '/permission/index.html', '/permission/', 998, 1, '2017-04-11 15:23:04', NULL, '2017-04-19 11:04:53', 2, NULL);
INSERT INTO `tb_permission` (`pid`, `sid`, `code`, `type`, `icon`, `name`, `value`, `regular`, `rank`, `enable`, `creation_time`, `creator`, `modification_time`, `modifier`, `memo`) VALUES (0, 3, 'role', 1, 'fa fa-sitemap', '角色管理', '/role/index.html', '/role/', 997, 1, '2017-04-11 15:23:04', NULL, '2017-04-19 11:05:02', 2, NULL);
