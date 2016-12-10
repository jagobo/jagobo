/*
SQLyog Ultimate v11.26 (32 bit)
MySQL - 10.1.14-MariaDB : Database - case
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`case` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `case`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章ID，-2归档信息，-1留言信息，0关于',
  `content` longtext COMMENT '文章内容',
  `title` varchar(100) DEFAULT NULL COMMENT '文章标题',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `clicks` int(11) DEFAULT '0' COMMENT '文章阅读数',
  `tagsid` int(11) DEFAULT '0' COMMENT '所属标签ID',
  `picture` varchar(200) DEFAULT '' COMMENT '文章配图',
  `isdraft` int(2) DEFAULT '0' COMMENT '文章的状态，默认0正式文章，1草稿文章',
  `remark` varchar(500) DEFAULT '' COMMENT '文章的摘要',
  `userid` int(11) DEFAULT '0' COMMENT '用户ID',
  `type` int(2) DEFAULT '1' COMMENT '文章类型码：1原创，2转载，4翻译，8推荐，16置顶',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `article` */

/*Table structure for table `blacks` */

DROP TABLE IF EXISTS `blacks`;

CREATE TABLE `blacks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) DEFAULT '' COMMENT 'IP地址',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `blacks` */

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) DEFAULT '' COMMENT '图书名称',
  `price` decimal(20,2) DEFAULT '0.00' COMMENT '单价',
  `pic` varchar(200) DEFAULT NULL COMMENT '图片',
  `description` varchar(5000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`id`,`name`,`price`,`pic`,`description`) values (1,'Java lucene','20.00','e://media/pic/A图片库/java.jpg','Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。Java语言作为静态面向对象编程语言的代表，人工智能AI极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程[1]  。'),(4,'go','40.00','e://media/pic/A图片库/go.jpg','Go语言专门针对多处理器系统应用程序的编程进行了优化，使用Go编译的程序可以媲美C或C++代码的速度，而且更加安全、支持并行进程。'),(6,'go1','30.00','e://media/pic/A图片库/php.jpg','PHP（外文名:PHP: Hypertext Preprocessor，中文名：“超文本预处理器”）是一种通用开源脚本语言。语法吸收了C语言、Java和Perl的特点，利于学习，使用广泛，主要适用于Web开发领域。PHP 独特的语法混合了C、Java、Perl以及PHP自创的语法。它可以比CGI或者Perl更快速地执行动态网页。用PHP做出的动态页面与其他的编程语言相比，PHP是将程序嵌入到HTML（标准通用标记语言下的一个应用）文档中去执行，执行效率比完全生成HTML标记的CGI要高许多；PHP还可以执行编译后代码，编译可以达到加密和优化代码运行，使代码运行更快。'),(7,'11','30.00','e://media/pic/A图片库/php.jpg','go语言是很好的一门语言,可以用来做服务端编程 lucene'),(8,'go111','30.00','e://media/pic/A图片库/php.jpg','golang is a good computer language , can be used to do mall importance things 还有 java 的经典查询框架lucene solr');

/*Table structure for table `cm_case` */

DROP TABLE IF EXISTS `cm_case`;

CREATE TABLE `cm_case` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creater` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `type` int(11) DEFAULT NULL COMMENT '案件类别 1001刑事案件',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `pic` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='案件主表';

/*Data for the table `cm_case` */

insert  into `cm_case`(`id`,`creater`,`create_time`,`modifier`,`modify_time`,`version`,`name`,`type`,`description`,`pic`,`price`) values (1,NULL,'2016-10-29 23:57:09',NULL,NULL,0,'boa',1001,'boa的按键',NULL,NULL),(2,NULL,'2016-10-29 23:57:21',NULL,NULL,0,'张国',1001,'1111',NULL,NULL),(3,NULL,'2016-10-29 23:57:31',NULL,NULL,0,'立刻',1002,'12',NULL,NULL);

/*Table structure for table `document` */

DROP TABLE IF EXISTS `document`;

CREATE TABLE `document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creater` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `name` varchar(100) NOT NULL COMMENT '附件名称',
  `path` varchar(200) DEFAULT NULL COMMENT '文件本地存储路径',
  `type` int(11) DEFAULT NULL COMMENT '附件类型 1:mongdb,2:二进制,3:本地文本',
  `store_id` bigint(100) DEFAULT NULL COMMENT '存储id  (mongdb.id, document_bin.id, document_txt.id)',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

/*Data for the table `document` */

/*Table structure for table `document_bin` */

DROP TABLE IF EXISTS `document_bin`;

CREATE TABLE `document_bin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` blob COMMENT '二进制文件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件存储表\r\n';

/*Data for the table `document_bin` */

/*Table structure for table `friendly` */

DROP TABLE IF EXISTS `friendly`;

CREATE TABLE `friendly` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) DEFAULT '' COMMENT '友情链接',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `url` varchar(50) DEFAULT '' COMMENT '友链地址',
  `counts` int(11) DEFAULT '0' COMMENT '点击量',
  `orders` int(11) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `friendly` */

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `userid` int(11) DEFAULT NULL COMMENT '留言回复人ID',
  `content` varchar(500) DEFAULT NULL COMMENT '留言内容',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '留言时间',
  `pid` int(11) DEFAULT '0' COMMENT '父留言ID',
  `articleid` int(11) DEFAULT '0' COMMENT '文章ID，-2归档信息，-1留言信息，0关于',
  `isshow` int(2) DEFAULT '1' COMMENT '是否通过审核，默认1通过，0待审核',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `message` */

/*Table structure for table `tags` */

DROP TABLE IF EXISTS `tags`;

CREATE TABLE `tags` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL COMMENT '标签名',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `counts` int(10) unsigned DEFAULT '0' COMMENT '点击量',
  `orders` int(10) unsigned DEFAULT '0' COMMENT '排序',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=111 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `tags` */

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `test` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(50) DEFAULT '' COMMENT '用户名',
  `pwd` varchar(50) DEFAULT '' COMMENT '登录密码',
  `state` int(11) DEFAULT '0' COMMENT '用户状态，默认0不可用，1正常',
  `avatar` varchar(50) DEFAULT '' COMMENT '用户头像',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `userip` varchar(50) DEFAULT '' COMMENT '用户IP',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱地址',
  `website` varchar(50) DEFAULT '' COMMENT '网站地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
