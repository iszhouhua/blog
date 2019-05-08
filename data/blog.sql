/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-01-23 22:39:41
*/

SET NAMES 'utf8';

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog_article
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) NOT NULL COMMENT '文章详情页链接',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '文章标题',
  `description` varchar(500) DEFAULT NULL COMMENT '文章描述',
  `image` varchar(200) DEFAULT NULL COMMENT '文章的预览图片',
  `content` longtext CHARACTER SET utf8mb4 COMMENT '文章内容',
  `content_md` longtext CHARACTER SET utf8mb4 COMMENT ' Markdown格式的文章内容',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_top` tinyint(4) DEFAULT '0' COMMENT '文章是否置顶  0：否  1：是',
  `is_comment` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否开启评论 0：关闭 1：开启',
  `is_original` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否为原创文章 0：转载 1：原创',
  `source_url` varchar(200) DEFAULT NULL COMMENT '原文链接，转载文章才需填写',
  `visits` int(11) NOT NULL DEFAULT '0' COMMENT '访问量',
  `status` tinyint(4) NOT NULL COMMENT '状态 0：草稿 1：已发布 2：回收站 3：自定义文章',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_url` (`url`) COMMENT '文章链接唯一索引',
  UNIQUE KEY `uk_title` (`title`) USING HASH COMMENT '文章标题唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of blog_article
-- ----------------------------
INSERT INTO `blog_article` VALUES ('1', 'hello-world', 'hello world!', '你好，世界', null, '<h1><a id=\"Hello_World_0\"></a>Hello World</h1>\n<p>你好，世界。<br />\n欢迎使用本博客系统。<br />\n写博客需要坚持，一起努力加油吧！</p>\n', '# Hello World\n你好，世界。\n欢迎使用本博客系统。\n写博客需要坚持，一起努力加油吧！', '1', '2019-01-01 00:00:00', '2018-12-30 15:37:31', '0', '1', '1', null, '0', '1');
INSERT INTO `blog_article` VALUES ('2', 'about', '关于我', '关于我', null, '<p style=\"text-align: center;\">\r\n	<span style=\"font-size: 14pt;\">欢迎来到<strong>我的个人博客</strong></span>\r\n</p>\r\n<div class=\"alert alert-danger\">我是一个好人。<br>我真的是一个好人哦！\r\n</div>\r\n<div class=\"alert alert-success\">这是个自定义文章示例界面<br>内容完全自定义<br>嘿嘿嘿~</div>\r\n<div class=\"alert alert-danger\">博客项目在<a href=\"https://github.com/iszhouhua/blog\">这里https://github.com/iszhouhua/blog</a><br>参考的博客主题在<a href=\"https://github.com/ZEROKISEKI/hexo-theme-gal\">这里https://github.com/ZEROKISEKI/hexo-theme-gal</a><br>参考网站在<a href=\"https://www.mmgal.com/\">这里https://www.mmgal.com/</a></div>', '<p style=\"text-align: center;\">\r\n	<span style=\"font-size: 14pt;\">欢迎来到<strong>我的个人博客</strong></span>\r\n</p>\r\n<div class=\"alert alert-danger\">我是一个好人。<br>我真的是一个好人哦！\r\n</div>\r\n<div class=\"alert alert-success\">这是个自定义文章示例界面<br>内容完全自定义<br>嘿嘿嘿~</div>\r\n<div class=\"alert alert-danger\">博客项目在<a href=\"https://github.com/iszhouhua/blog\">这里https://github.com/iszhouhua/blog</a><br>参考的博客主题在<a href=\"https://github.com/ZEROKISEKI/hexo-theme-gal\">这里https://github.com/ZEROKISEKI/hexo-theme-gal</a><br>参考网站在<a href=\"https://www.mmgal.com/\">这里https://www.mmgal.com/</a></div>', null, '2018-12-25 11:03:43', '2018-12-25 11:03:43', '0', '1', '1', null, '0', '3');

-- ----------------------------
-- Table structure for blog_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_tag`;
CREATE TABLE `blog_article_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章与标签的对应关系';

-- ----------------------------
-- Records of blog_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '分类名',
  `url` varchar(50) DEFAULT NULL COMMENT '分类链接',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_url` (`url`) COMMENT '分类链接唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='分类表';

-- ----------------------------
-- Records of blog_category
-- ----------------------------
INSERT INTO `blog_category` VALUES ('1', '未分类', 'default');

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) DEFAULT NULL COMMENT '评论的文章',
  `author` varchar(50) DEFAULT NULL COMMENT '评论者',
  `email` varchar(200) DEFAULT NULL COMMENT '评论者的邮箱',
  `email_md5` char(32) DEFAULT NULL COMMENT '邮箱MD5值，用于显示gravatar头像',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '评论者的浏览器',
  `content` text CHARACTER SET utf8mb4 COMMENT '评论内容',
  `ip` varchar(128) DEFAULT NULL COMMENT '评论者的ip地址',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '引用的回复，0表示没有引用',
  `is_admin` tinyint(4) DEFAULT '0' COMMENT '是否为博主评论  0：否  1：是',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `status` tinyint(4) NOT NULL COMMENT '评论状态 0：待审核 1：已发布 2：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Records of blog_comment
-- ----------------------------
INSERT INTO `blog_comment` VALUES ('1', '1', 'zhouhua', 'iszhouhua@163.com', '2bf0ebee5f19445f2af02908d5c3ab0e', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36', '欢迎！', '127.0.0.1', '0', '0', '2018-01-01 15:46:59', '1');

-- ----------------------------
-- Table structure for blog_config
-- ----------------------------
DROP TABLE IF EXISTS `blog_config`;
CREATE TABLE `blog_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '参数名',
  `value` longtext COMMENT '参数值',
  `type` tinyint(4) DEFAULT NULL COMMENT '参数类型 1：全局变量 2：系统配置',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING BTREE COMMENT '参数名唯一'
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='博客配置表';

-- ----------------------------
-- Records of blog_config
-- ----------------------------
INSERT INTO `blog_config` VALUES ('1', 'BLOG_TITLE', '我的个人博客', '1', '网站标题');
INSERT INTO `blog_config` VALUES ('2', 'BLOG_KEYWORDS', '个人博客', '1', '网站关键词，关键字之间用,分隔');
INSERT INTO `blog_config` VALUES ('3', 'BLOG_DESCRIPTION', '我的个人博客', '1', '网站描述');
INSERT INTO `blog_config` VALUES ('4', 'BLOG_URL', 'http://127.0.0.1:8080/', '1', '网站链接');
INSERT INTO `blog_config` VALUES ('5', 'BLOG_AUTHOR', 'admin', '1', '网站作者');
INSERT INTO `blog_config` VALUES ('6', 'BLOG_AVATAR', '/images/avatar.jpg', '1', '头像');
INSERT INTO `blog_config` VALUES ('7', 'BLOG_NOTICE', '公告', '1', '公告');
INSERT INTO `blog_config` VALUES ('8', 'DEFAULT_IMAGE', '/images/default-preview.jpg', '1', '默认预览图');
INSERT INTO `blog_config` VALUES ('9', 'AUTHOR_DESCRIPTION', '一二三四五，上山打老虎。', '1', '头像下的描述内容');
INSERT INTO `blog_config` VALUES ('10', 'FILING_ICP', null, '1', 'ICP备案');
INSERT INTO `blog_config` VALUES ('11', 'FILING_SECURITY', null, '1', '公安备案');
INSERT INTO `blog_config` VALUES ('12', 'COMMENT_CHECK', 'false', '2', '评论是否需要校检');
INSERT INTO `blog_config` VALUES ('13', 'BACKGROUND_LIST', '[\"/images/slide/background1.jpg\",\"/images/slide/background2.jpg\",\"/images/slide/background3.jpg\",\"/images/slide/background4.jpg\",\"/images/slide/background5.jpg\",\"/images/slide/background6.jpg\"]', '1', '网站的背景图片集合，格式为JSON数组');
INSERT INTO `blog_config` VALUES ('14', 'BLOG_HEAD', '<meta name=\"google-site-verification\" content=\"_-xMXp-rAz3pUIziyjcIoJ8D9VOV6yb7XrB5qaeq_Fg\" />\n<meta name=\"baidu-site-verification\" content=\"HsvFoNGZDC\" />\n<meta name=\"360-site-verification\" content=\"42179cf63604add68d1e11b26f44c322\" />\n<meta name=\"sogou_site_verification\" content=\"XvRe3wQaqS\"/>\n', '1', '博客头部插入的代码，如站点验证代码等');
INSERT INTO `blog_config` VALUES ('15', 'BLOG_SCRIPT','<script>/*百度统计*/     var _hmt = _hmt || [];    (function() {        var hm = document.createElement(\"script\");        hm.src = \"https://hm.baidu.com/hm.js?77737a53e73e57c6c44b4640d1c108a1\";        var s = document.getElementsByTagName(\"script\")[0];        s.parentNode.insertBefore(hm, s);    })();</script>\n<script>/*百度自动推送*/  (function(){     var bp = document.createElement(\'script\');     var curProtocol = window.location.protocol.split(\':\')[0];     if (curProtocol === \'https\') {         bp.src = \'https://zz.bdstatic.com/linksubmit/push.js\';     }     else {         bp.src = \'http://push.zhanzhang.baidu.com/push.js\';     }     var s = document.getElementsByTagName(\"script\")[0];     s.parentNode.insertBefore(bp, s); })(); </script>\n<script>/*360自动收录*/ (function(){ var src = (document.location.protocol == \"http:\") ? \"http://js.passport.qihucdn.com/11.0.1.js?6396cb521d4daf069727dc8d995a3878\":\"https://jspassport.ssl.qhimg.com/11.0.1.js?6396cb521d4daf069727dc8d995a3878\"; document.write(\'<script src=\"\' + src + \'\" id=\"sozz\"><\\/script>\'); })(); </script>\n' , '1', '博客尾部插入的脚本，如统计代码、推送代码等');
INSERT INTO `blog_config` VALUES ('16', 'FILE_STORAGE', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"\",\"qiniuBucketName\":\"\",\"qiniuDomain\":\"\",\"qiniuPrefix\":\"\",\"qiniuSecretKey\":\"\",\"localDirectory\":\"/data/images/\",\"localDomain\":\"http://127.0.0.1:8080/\",\"type\":4,\"localPrefix\":\"upload\"}', '2', '云/本地存储配置信息');
-- ----------------------------
-- Table structure for blog_link
-- ----------------------------
DROP TABLE IF EXISTS `blog_link`;
CREATE TABLE `blog_link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '链接名称',
  `url` varchar(500) DEFAULT NULL COMMENT '链接地址',
  `type` tinyint(4) DEFAULT NULL COMMENT '链接类型 1：友情链接 2：个人链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='链接表';

-- ----------------------------
-- Records of blog_link
-- ----------------------------
INSERT INTO `blog_link` VALUES ('1', '周华个人博客', 'https://www.iszhouhua.com', '1');
INSERT INTO `blog_link` VALUES ('2', 'GitHub', 'https://iszhouhua.github,io', '2');

-- ----------------------------
-- Table structure for blog_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_log`;
CREATE TABLE `blog_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(128) NOT NULL COMMENT 'IP地址',
  `city` varchar(20) DEFAULT NULL COMMENT '所在城市',
  `url` varchar(200) DEFAULT NULL COMMENT '首次访问的链接',
  `referer` varchar(500) DEFAULT NULL COMMENT '首次访问的来源',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '首次访问的浏览器类型',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后访问时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `visits` int(11) DEFAULT '1' COMMENT '总访问次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ip` (`ip`) USING BTREE COMMENT 'IP唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访客日志表';

-- ----------------------------
-- Records of blog_log
-- ----------------------------

-- ----------------------------
-- Table structure for blog_menu
-- ----------------------------
DROP TABLE IF EXISTS `blog_menu`;
CREATE TABLE `blog_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名',
  `url` varchar(500) DEFAULT NULL COMMENT '菜单链接',
  `is_blank` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否在新窗口打开菜单  0：否，1：是',
  `icon` varchar(100) DEFAULT NULL COMMENT 'Font Awesome图标',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '菜单排序，越小的越靠前',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_url` (`url`) COMMENT '菜单链接唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of blog_menu
-- ----------------------------
INSERT INTO `blog_menu` VALUES ('1', '首页', '/', '0', 'fa-home', '1');
INSERT INTO `blog_menu` VALUES ('2', '分类', '/category/', '0', 'fa-list', '3');
INSERT INTO `blog_menu` VALUES ('3', '标签', '/tag/', '0', 'fa-tags', '4');
INSERT INTO `blog_menu` VALUES ('4', '关于我', '/about.html', '0', 'fa-user', '10');

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '标签名',
  `url` varchar(50) DEFAULT NULL COMMENT '标签链接',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_url` (`url`) COMMENT '标签链接唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';

-- ----------------------------
-- Records of blog_tag
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `password` char(32) NOT NULL,
  `salt` char(16) NOT NULL COMMENT '密码盐',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `email_md5` char(32) DEFAULT NULL COMMENT '邮箱MD5值，用于显示gravatar头像',
  `last_login` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_fail` tinyint(4) NOT NULL DEFAULT '0' COMMENT '登录失败次数，超过一定次数禁止登录',
  `is_disable` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否禁用 0：否 1：是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', 'f592d0ce304114b279e85b18f804334b', '7af4a47cb431d8f4', 'admin@admin.com', '64e1b8d34f425d19e1ee2ea7236d3028', '2018-12-31 20:13:25', '2018-12-31 20:13:25', '0', '0');

-- ----------------------------
-- Table structure for blog_spider
-- ----------------------------
DROP TABLE IF EXISTS `blog_spider`;
CREATE TABLE `blog_spider` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '爬虫标识名',
  `title_rule` varchar(500) NOT NULL COMMENT '标题爬取规则',
  `content_rule` varchar(500) NOT NULL COMMENT '文章内容爬取规则',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='爬虫规则';

-- ----------------------------
-- Records of blog_spider
-- ----------------------------
INSERT INTO `blog_spider` VALUES ('1', '博客园', 'h1.postTitle', 'div.postBody');
INSERT INTO `blog_spider` VALUES ('2', 'CSDN', 'h1.title-article', 'div#content_views');
INSERT INTO `blog_spider` VALUES ('3', '简书', 'h1.title', 'div.show-content');
INSERT INTO `blog_spider` VALUES ('4', '思否', '#articleTitle a', 'div.article');
