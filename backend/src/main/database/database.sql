/*
SQLyog 企业版 - MySQL GUI v7.14 
MySQL - 5.6.40 : Database - jeeplatform
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`jeeplatform` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `jeeplatform`;

/*Table structure for table `sys_depart` */

DROP TABLE IF EXISTS `sys_depart`;

CREATE TABLE `sys_depart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `code` varchar(32) NOT NULL COMMENT '关联文件存放路径',
  `menu_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '部门名称',
  `note` varchar(256) DEFAULT NULL COMMENT '部门说明',
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `NewIndex1` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `sys_depart` */

insert  into `sys_depart`(`id`,`code`,`menu_id`,`name`,`note`,`update_user`,`update_time`) values (1,'SYSTEM',1,'系统部门','系统部门','admin','2018-09-27 14:52:04');

/*Table structure for table `sys_file` */

DROP TABLE IF EXISTS `sys_file`;

CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) NOT NULL COMMENT '文件名',
  `dep_id` int(11) NOT NULL COMMENT '部门id',
  `path` varchar(1024) NOT NULL COMMENT '文件位置',
  `owner` varchar(20) NOT NULL COMMENT 'userId',
  `type` varchar(11) NOT NULL COMMENT '文件类型',
  `size` float NOT NULL COMMENT '文件大小',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_file` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单Id',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级Id',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(30) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(100) DEFAULT NULL COMMENT '菜单链接',
  `type` int(4) DEFAULT NULL COMMENT '菜单类型',
  `order` int(4) DEFAULT NULL COMMENT '菜单排序',
  `status` int(4) DEFAULT NULL COMMENT '菜单状态',
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `NewIndex1` (`name`,`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`parent_id`,`name`,`icon`,`url`,`type`,`order`,`status`,`update_user`,`update_time`) values (1,0,'系统管理','&#xe610','#',1,1,1,NULL,'2018-09-22 15:01:47'),(2,1,'角色管理','&#xe604','#/view/role',1,2,1,NULL,'2018-09-22 15:01:47'),(3,1,'部门管理','&#xe604','#/view/depart',1,3,1,NULL,'2018-09-22 15:01:47'),(4,1,'人员管理','&#xe604','#/view/user',1,5,1,NULL,'2018-09-22 15:01:47'),(10,0,'文档管理','&#xe610','#',2,1,1,NULL,'2018-09-22 15:01:47');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL COMMENT '权限Id',
  `note` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `name` varchar(100) NOT NULL COMMENT '权限名称',
  `perm_type` int(11) DEFAULT NULL COMMENT '0:普通人员独有权限，5：普通管理员独有的权限，9：系统管理员独有权限',
  PRIMARY KEY (`id`),
  KEY `p_fk_1` (`perm_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`note`,`name`,`perm_type`) values (1,'查询文件列表','SEARCH_LIST',0),(2,'上传文件权限','UP_FILE',0),(3,'下载文件权限','DOWN_FILE',0),(4,'删除文件权限','DEL_FILE',0),(5,'普通人员管理权限','DEPART_ADMIN',5),(6,'人员管理的权限','USER_MANAGE',9),(7,'角色管理的权限','ROLE_MANAGE',9),(8,'部门管理的权限','DEPART_MANAGE',9),(10,'菜单管理的权限','MENU_MANAGE',9);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `depart_id` int(11) DEFAULT NULL COMMENT '角色属于部门',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `note` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`depart_id`,`name`,`note`,`update_time`,`update_user`) values (1,1,'超级管理员','超级管理员拥有所有权限','2018-09-28 14:20:12','admin');

/*Table structure for table `sys_role_depart_drop` */

DROP TABLE IF EXISTS `sys_role_depart_drop`;

CREATE TABLE `sys_role_depart_drop` (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `depart_id` int(11) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`role_id`,`depart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_depart_drop` */

insert  into `sys_role_depart_drop`(`role_id`,`depart_id`) values (1,1);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  UNIQUE KEY `role_menu_id` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,1),(1,2),(1,3),(1,4),(1,10);

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `role_id` int(11) NOT NULL COMMENT '角色Id',
  `pm_id` int(11) NOT NULL COMMENT '权限Id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`role_id`,`pm_id`) values (1,1),(1,2),(1,3),(1,4);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` varchar(20) NOT NULL COMMENT '用户Id',
  `user_name` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `sex` varchar(6) DEFAULT NULL COMMENT '性别',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  `rank` varchar(10) DEFAULT NULL COMMENT '账号等级',
  `question` varchar(512) DEFAULT NULL COMMENT '提示问题',
  `answer` varchar(512) DEFAULT NULL COMMENT '提示答案',
  `status` int(1) DEFAULT '0' COMMENT '状态',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '最后修改时间',
  `update_user` varchar(20) NOT NULL COMMENT '最后修改人',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`user_name`,`password`,`phone`,`sex`,`email`,`note`,`rank`,`question`,`answer`,`status`,`update_time`,`update_user`) values ('admin','admin','$2a$10$aalHYXvh1tcNIJoKARxtC.kUUWlKcUys.yJPeA1k/IBJQ1o1agLyq','1552323312','1','313222@foxmail.com','超级管理员','admin','2017-08-12','127.0.0.1',0,'2018-09-22 15:01:47','');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` varchar(20) NOT NULL COMMENT '用户Id,联合主键',
  `role_id` int(11) NOT NULL COMMENT '角色Id，联合主键',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values ('admin',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
