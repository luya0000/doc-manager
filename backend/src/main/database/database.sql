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
  `name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `desc` varchar(256) DEFAULT NULL COMMENT '部门说明',
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `sys_depart` */

insert  into `sys_depart`(`id`,`name`,`desc`,`update_user`,`update_time`) values (1,'三总师','三总师',NULL,NULL),(2,'办公室','办公室',NULL,NULL),(3,'人力资源部','人力资源部',NULL,NULL),(4,'财务部','财务部',NULL,NULL),(5,'安全生产部','安全生产部',NULL,NULL),(6,'经营发展部','经营发展部',NULL,NULL);

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`parent_id`,`name`,`icon`,`url`,`type`,`order`,`status`,`update_user`,`update_time`) values (1,0,'系统管理','&#xe610','#',1,1,1,NULL,NULL),(2,1,'角色管理','&#xe604','#/view/role',1,2,1,NULL,NULL),(3,1,'部门管理','&#xe604','#/view/depart',1,3,1,NULL,NULL),(4,1,'菜单管理','&#xe610','#/view/menu',1,4,1,NULL,NULL),(5,1,'人员管理','&#xe604','#/view/user',1,5,1,NULL,NULL),(6,1,'密码修改','&#xe604','#/view/changepwd',1,6,1,NULL,NULL),(10,0,'文档管理','&#xe610','#',2,7,1,NULL,NULL),(11,10,'三总师','&#xe604','#/view/file_manage',2,8,1,NULL,NULL),(12,10,'办公室','&#xe604','#/view/file_manage',2,9,1,NULL,NULL),(13,10,'人力资源部','&#xe604','#/view/file_manage',2,10,1,NULL,NULL),(14,10,'财务部','&#xe604','#/view/file_manage',2,11,1,NULL,NULL),(15,10,'安全生产部','&#xe604','#/view/file_manage',2,12,1,NULL,'0000-00-00 00:00:00');

/*Table structure for table `sys_menu_depart` */

DROP TABLE IF EXISTS `sys_menu_depart`;

CREATE TABLE `sys_menu_depart` (
  `menu_id` int(11) NOT NULL COMMENT '角色id',
  `depart_id` int(11) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`menu_id`,`depart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu_depart` */

insert  into `sys_menu_depart`(`menu_id`,`depart_id`) values (11,1),(12,2),(13,3),(14,4),(15,5);

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL COMMENT '权限Id',
  `desc` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单Id',
  PRIMARY KEY (`id`),
  KEY `p_fk_1` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`desc`,`name`,`menu_id`) values (1,'用户管理的权限','用户管理',1),(2,'管理员管理的权限','管理员管理',2),(3,'用户统计的权限','用户统计',3),(4,'在线管理的权限','在线管理',4),(5,'在线情况的权限','在线情况',5),(6,'在线聊天的权限','在线聊天',6),(7,'系统管理的权限','系统管理',7),(8,'角色管理的权限','角色管理',8),(9,'权限管理的权限','权限管理',9),(10,'菜单管理的权限','菜单管理',10),(11,'平台资料的权限','平台资料',11);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`desc`,`update_time`,`update_user`) values (1,'超级管理员','超级管理员拥有所有权限','2018-09-14 23:59:00',NULL),(2,'部门管理员','用户管理权限','2018-09-14 23:59:02',NULL),(3,'部门成员','角色管理权限','2018-09-14 23:59:03',NULL);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(2,10),(2,11);

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `role_id` int(11) NOT NULL COMMENT '角色Id',
  `pm_id` int(11) NOT NULL COMMENT '权限Id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`role_id`,`pm_id`) values (2,1),(1,1),(1,2),(1,3),(1,4),(9,1),(1,7),(9,2),(2,2),(1,10),(1,9),(1,8),(2,3),(1,6),(1,5);

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

insert  into `sys_user`(`user_id`,`user_name`,`password`,`phone`,`sex`,`email`,`note`,`rank`,`question`,`answer`,`status`,`update_time`,`update_user`) values ('admin','admin','$2a$10$aalHYXvh1tcNIJoKARxtC.kUUWlKcUys.yJPeA1k/IBJQ1o1agLyq','1552323312','男','313222@foxmail.com','超级管理员','admin','2017-08-12','127.0.0.1',0,NULL,''),('sys','sys','$2a$10$aalHYXvh1tcNIJoKARxtC.kUUWlKcUys.yJPeA1k/IBJQ1o1agLyq','1552323312','男','313222@foxmail.com','系统管理员','sys','2017-08-25','127.0.0.1',0,NULL,''),('test','test','123','12332233212','保密','2312@qq.com','没有备注','user','2017-11-25','127.0.0.1',NULL,NULL,''),('user','user','adf8e0d0828bde6e90c2bab72e7a2a763d88a0de','1552323312','男','313222@foxmail.com','用户','user','2017-08-18','127.0.0.1',0,NULL,'');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` varchar(20) NOT NULL COMMENT '用户Id,联合主键',
  `role_id` int(11) NOT NULL COMMENT '角色Id，联合主键',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values ('admin',1),('admin',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
