/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-12-24 23:22:39
*/

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
  `category_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '分类ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_top` tinyint(4) NOT NULL DEFAULT '0' COMMENT '文章是否置顶  0：否  1：是',
  `is_comment` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否开启评论 0：关闭 1：开启',
  `visits` int(11) NOT NULL DEFAULT '0' COMMENT '访问量',
  `status` tinyint(4) NOT NULL COMMENT '状态 0：草稿 1：已发布 2：已回收',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_url` (`url`) COMMENT '文章链接唯一索引',
  UNIQUE KEY `uk_title` (`title`) USING HASH COMMENT '文章标题唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of blog_article
-- ----------------------------
INSERT INTO `blog_article` VALUES ('1', 'test2', '测试文章   Article Id:1', '测试描述   Article Id:1', 'https://secure.gravatar.com/avatar/2bf0ebee5f19445f2af02908d5c3ab0e', '<p>325</p>\n', '325', '1', '2018-12-09 22:07:13', '2018-12-14 20:40:26', '1', '1', '27', '1');
INSERT INTO `blog_article` VALUES ('2', 'test', '制作一个不轻量的hexo主题:cube', '之前自己用Laravel写了个博客, 用了一段时间果断弃之, 又用回了原来的Hexo, 上网找了下主题, 没找到自己想要的, 于是决定自己写一款主题(在写主题的过程倒是找到了很多自己觉得不错的2333), 这篇文章只是用来记录一下最近这段时间写这个主题的过程, 中间存在着很多的问题, 主题目前还没有正式完成, 最近实习 + 毕设搞得比较颓, 9月份之后会逐步完善修复。', 'http://pic1.win4000.com/wallpaper/2017-12-04/5a24c0e98479b.jpg', '\r\n        <p>建议每篇文章都加上自己的preview的url地址</p>\r\n<a id=\"more\"></a>\r\n<h2 id=\"前言\"><a href=\"#前言\" class=\"headerlink\" title=\"前言\"></a>前言</h2><p>之前自己用Laravel写了个博客, 用了一段时间果断弃之, 又用回了原来的Hexo, 上网找了下主题, 没找到自己想要的, 于是决定自己写一款主题(在写主题的过程倒是找到了很多自己觉得不错的2333), 这篇文章只是用来记录一下最近这段时间写这个主题的过程, 中间存在着很多的问题, 主题目前还没有正式完成, 最近实习 + 毕设搞得比较颓, 9月份之后会逐步完善修复。</p>\r\n<p><a href=\"https://github.com/ZEROKISEKI/hexo-theme-cube\" target=\"_blank\" rel=\"noopener\">hexo-theme-cube</a><br><a href=\"https://github.com/ZEROKISEKI/cube-generator\" target=\"_blank\" rel=\"noopener\">cube-generator</a><br><a href=\"http://sora1.coding.me/\" target=\"_blank\" rel=\"noopener\">示例网站</a>  </p>\r\n<p>其中<code>cube-generator</code>是主题的生成器, 通过运行对应的gulp命令能够重新生成主题cube</p>\r\n<h2 id=\"构建主题\"><a href=\"#构建主题\" class=\"headerlink\" title=\"构建主题\"></a>构建主题</h2><p>看了一些github上的主题项目, 很多都是ES5 + jQuery的实现方式, 于是自己有了一个想法，用ES6来写这个主题样式, 最终代码是要转化为ES5的, 那么就需要babel进行转换了, 又因为写样式决定采用SaSS, 所以就决定用gulp进行自动化构建, 所以最终的决定是:</p>\r\n<ul>\r\n<li>gulp中利用<code>browserify + babelify</code>对前端代码进行ES6到ES5的转换, Babel转换的规则写在<code>babelify</code>上面, js代码的入口规定为<code>source/js/app.js</code>, <code>app.js</code>中<code>import</code>对应功能的js代码, gulp进行<code>bundle script</code>的生成代码如下:</li>\r\n</ul>\r\n<pre><code>const b = browserify({\r\n    cache: {},\r\n       packageCache: {},\r\n       entries: path.join(\'./source/js/app.js\'),\r\n       debug: true\r\n})\r\n\r\n// 配置babel转换规则\r\nb.transform(babelify, {\r\n       presets: [\'env\', \'es2015\', \'stage-2\'],\r\n       plugins: [[\'transform-runtime\', {\r\n           \'polyfill\': true,\r\n           \'regenerator\': true\r\n       }]]\r\n})\r\n\r\ngulp.task(\'buildScript\', bundle)\r\n\r\n// b.on(\'update\', bundle)\r\n\r\n// bundle生成script.js并且放在cube/source/js/script.js上面\r\nfunction bundle() {\r\n       return b.bundle()\r\n           .on(\'error\', err =&gt; console.log(err))\r\n           .pipe(source(\'script.js\'))\r\n           .pipe(buffer())\r\n           .pipe(sourcemaps.init({ loadMaps: true }))\r\n           .pipe(uglify())\r\n           .pipe(sourcemaps.write(\'./\'))\r\n           .pipe(gulp.dest(path.join(Path.theme, Path.source, \'/js\')))\r\n}\r\n</code></pre><p>JS的部分如下所示:</p>\r\n<p><div class=\"gal-image-container\"><p class=\"gal-image\"><a href=\"http://7xqtjy.com1.z0.glb.clouddn.com/js%E6%A8%A1%E5%9D%97-1.png\" class=\"highslide-image\" onclick=\"return hs.expand(this);\"><img src=\"http://7xqtjy.com1.z0.glb.clouddn.com/js%E6%A8%A1%E5%9D%97-1.png\" alt=\"\" width=\"650\" title=\"点击放大\"></a></p></div></p>\r\n<p><a href=\"http://7xqtjy.com1.z0.glb.clouddn.com/js%E6%A8%A1%E5%9D%97-1.png\" target=\"_blank\" rel=\"noopener\"><img src=\"http://7xqtjy.com1.z0.glb.clouddn.com/js%E6%A8%A1%E5%9D%97-1.png\" alt=\"JS模块\"></a></p>\r\n<ul>\r\n<li>在gulp中利用<code>gulp-inject</code>, 将<code>source/sass/</code>目录下除了<code>style.scss</code>的<code>.scss</code>文件用<code>@import</code>的方式进行注入,最后再对<code>style.scss</code>用<code>gulp-sass</code>进行编译生成最终的css文件, 代码如下:</li>\r\n</ul>\r\n<pre><code>// highlight和lightgallery已经在其他文件通过@import的方式引用了索引文件\r\n// 所以这里不注入对应文件夹里面的文件\r\nfunction buildStyle() {\r\n       const injectFiles = gulp.src([\r\n           path.join(Path.source, \'sass/*.scss\'),\r\n           \'!\' + path.join(Path.source, \'sass/highlight/*.scss\'),\r\n           \'!\' + path.join(Path.source, \'sass/lightgallery/*.scss\'),\r\n           \'!\' + path.join(Path.source, \'/sass/style.scss\')\r\n       ], { read: false })\r\n        const injectOptions = {\r\n           transform(filePath) {\r\n               filePath = filePath.replace(`${Path.source}/sass/`, \'\');\r\n               filePath = filePath.replace(\'_\', \'\');\r\n               return `@import \"${filePath}\";`\r\n        },\r\n           starttag: \'// injector\',\r\n           endtag: \'// endinjector\',\r\n           addRootSlash: false\r\n    }\r\n\r\n    return gulp.src(path.join(Path.source, \'/sass/style.scss\'))\r\n           .pipe(Inject(injectFiles, injectOptions))\r\n           .pipe(Sass())\r\n           .pipe(postcss([ autoprefixer() ]))\r\n           .pipe(cleanCss({debug: true}))\r\n           .pipe(gulp.dest(path.join(Path.theme, Path.source, \'/css\')))\r\n}\r\n</code></pre><ul>\r\n<li>将上面两部生成的<code>script.js</code>和<code>style.css</code>注入到<code>header.ejs</code>和<code>layout.ejs</code>(注入的其实是ejs的模板写法), 然后将ejs文件，字体和图片等静态资源移动到主题文件夹中，将主题文件夹放置到Hexo站点的themes文件夹里面, 完成主题的生成:</li>\r\n</ul>\r\n<pre><code>gulp.task(\'injectStyle\', [\'buildStyle\'], () =&gt; {\r\n     const injectStyle = gulp.src(path.join(Path.theme, Path.source,\'/css/style.css\'), {\r\n          read: false\r\n     })\r\n\r\n     const injectOptions = {\r\n         starttag: \'&lt;!-- inject:style --&gt;\',\r\n         addRootSlash: false,\r\n         transform(filePath) {\r\n             return `&lt;link href=\"&lt;%- url_for(\'css/${filePath}\') %&gt;\" rel=\"stylesheet\" type=\"text/css\"&gt;`\r\n         },\r\n         ignorePath: path.join(Path.theme, Path.source, \'css\')\r\n     }\r\n\r\n     return gulp.src(path.join(Path.layout, \'/_partial/head.ejs\'))\r\n         .pipe(Inject(injectStyle, injectOptions))\r\n         .pipe(gulp.dest(path.join(Path.theme, Path.layout, \'_partial\')))\r\n})\r\n\r\n\r\ngulp.task(\'injectScript\', [\'buildScript\'], () =&gt; {\r\n    const injectScript = gulp.src(path.join(Path.theme, Path.source, \'/js/script.js\'), {\r\n        read: false\r\n    })\r\n\r\n    const injectOptions = {\r\n        starttag: \'&lt;!-- inject:script --&gt;\',\r\n        addRootSlash: false,\r\n        transform(filePath) {\r\n            return `&lt;script src=\"&lt;%- url_for(\'js/${filePath}\') %&gt;\"&gt;&lt;/script&gt;`\r\n        },\r\n        ignorePath: path.join(Path.theme, Path.source, \'js\')\r\n    }\r\n\r\n    return gulp.src(path.join(Path.layout, \'/layout.ejs\'))\r\n        .pipe(Inject(injectScript, injectOptions))\r\n        .pipe(gulp.dest(path.join(Path.theme, Path.layout)))\r\n})\r\n\r\ngulp.task(\'inject\', [\'injectStyle\', \'injectScript\'], () =&gt; {\r\n    return gulp.src([\r\n        \'!\' + path.join(Path.layout, \'/layout.ejs\'),\r\n        \'!\' + path.join(Path.layout, \'/_partial/head.ejs\'),\r\n        path.join(Path.layout, \'/**/*.ejs\')\r\n    ]).pipe(gulp.dest(path.join(Path.theme, Path.layout)))\r\n})\r\n\r\n通过这两个任务产生的ejs代码如下:\r\n\r\n    layout.ejs:\r\n\r\n    &lt;!-- inject:script --&gt;\r\n    &lt;script src=\"&lt;%- url_for(\'js/script.js\') %&gt;\"&gt;&lt;/script&gt;\r\n    &lt;!-- endinject --&gt;\r\n\r\n    _partial/head.ejs:\r\n\r\n    &lt;!-- inject:style --&gt;\r\n    &lt;link href=\"&lt;%- url_for(\'css/style.css\') %&gt;\" rel=\"stylesheet\" type=\"text/css\"&gt;\r\n    &lt;!-- endinject --&gt;\r\n</code></pre><ul>\r\n<li>通过<code>gulp-watch</code>监听对应领域的文件, 如果发生了变动就重新生成主题</li>\r\n</ul>\r\n<p>通过上面四个步骤的构建, 就可以在<code>source/js/</code>里面编写es6代码了, 开启<code>gulp watch</code>之后会监听对应模板和scss以及js等文件的变化重新生成主题(用node 子进程进行了<code>hexo clean &amp; hexo g</code>, 只要等待终端提示即可直接<code>hexo s</code>查看效果</p>\r\n<h2 id=\"博客的构建\"><a href=\"#博客的构建\" class=\"headerlink\" title=\"博客的构建\"></a>博客的构建</h2><p>Hexo博客的构建没什么特别的, 可以参考github上一些优秀的Hexo主题项目的布局格式, 结合<a href=\"http://hexo.io\" target=\"_blank\" rel=\"noopener\">Hexo官方文档</a>, 在对应的模板ejs引用对应的Hexo变量实现主题模板的编写即可。关于这个主题的具体构建代码可以看<a href=\"https://github.com/ZEROKISEKI/cube-generator\" target=\"_blank\" rel=\"noopener\">cube-generator</a></p>\r\n<h2 id=\"待解决问题\"><a href=\"#待解决问题\" class=\"headerlink\" title=\"待解决问题\"></a>待解决问题</h2><ul>\r\n<li><p>gulp的构建写得还是很乱, 原本的想法是想用<code>browser-sync</code>实现修改文件实时刷新的, 但是hexo启动服务器采用的是<code>hexo s</code>启动, 要先通过<code>hexo g</code>重新生成<code>public</code>文件夹才会刷新内容, 所以用<code>browser-sync</code>的想法也不了了之,后续有时间要把gulp的构建重新写一遍</p>\r\n</li>\r\n<li><p>移动端方面做的调试不多, 貌似在一些手机浏览器会出现一些奇怪的问题(我的小米4自带的原生浏览器加载不了用lightgallery读取的图片, 我觉得这个不是我的锅233, 但是有时间还是要看看)，highlight.js加载较慢导致手机打开博客页面一开始可以横向滚动(应该是我用了async进行异步的关系)</p>\r\n</li>\r\n<li><p>第三方评论系统只测试了畅言和gitment, disqus因为我这边暂时无法那个(你懂的), 所以这个部分还没写, 很多评论系统都挂了，所以在想是不是要自己也来写个评论系统2333</p>\r\n</li>\r\n<li><p>其他一些奇奇怪怪的问题….</p>\r\n</li>\r\n</ul>', '', '1', '2018-12-09 21:38:35', '2018-12-14 20:40:26', '1', '1', '23', '1');
INSERT INTO `blog_article` VALUES ('3', 'test3', '测试文章3   Article Id:3', '测试   Article Id:3', null, '<p>31545</p>\n<h1><a id=\"15325262_1\"></a>15325262</h1>\n', '31545\n# 15325262\n\n\n\n', '1', '2018-12-12 20:25:25', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('4', 'test4', '测试文章4', '测试描述', null, '测试', null, '1', '2018-12-12 20:27:38', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('5', 'test5', '测试文章5', '测试描述', null, '测试', null, '1', '2018-12-12 20:30:05', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('6', 'test6', '测试文章6', '测试描述', null, '测试', null, '1', '2018-12-12 20:30:09', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('7', 'test7', '测试文章7', '测试描述', null, '测试', null, '1', '2018-12-12 20:30:15', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('8', 'test8', '测试文章8', '测试描述', null, '测试', null, '1', '2018-12-12 20:30:21', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('9', 'test9', '测试8', null, null, '9', null, '1', '2018-12-12 22:13:42', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('10', 'test10', '10', null, null, '10', null, '1', '2018-12-12 22:13:52', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('11', 'test11', '11', null, null, '11', null, '1', '2018-12-12 22:14:00', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('12', 'test12', '12', null, null, '12', null, '1', '2018-12-12 22:14:27', '2018-12-14 20:40:26', '0', '1', '0', '1');
INSERT INTO `blog_article` VALUES ('13', '13', '13', null, null, null, null, '1', '2018-12-12 22:24:22', '2018-12-14 20:40:26', '0', '1', '1', '1');
INSERT INTO `blog_article` VALUES ('14', '14', '1122', null, null, null, null, '1', '2018-12-12 22:24:25', '2018-12-14 20:40:26', '0', '1', '1', '1');
INSERT INTO `blog_article` VALUES ('15', '15', '', null, null, null, null, '1', '2018-12-12 22:24:28', '2018-12-14 20:40:26', '0', '1', '1', '1');
INSERT INTO `blog_article` VALUES ('16', '143266', '你好吗', '我很好哦', '121426437', '<p>1253</p>\n<h1><a id=\"5_1\"></a>钱5</h1>\n<p>25<br />\n撒发广告</p>\n', '1253\n # 钱5 \n25 \n撒发广告', '1', '2018-12-24 20:39:27', '2018-12-24 20:39:27', '0', '1', '0', '0');
INSERT INTO `blog_article` VALUES ('18', '2018-12-24-20:47:18', '12416', '', '', '<p>13525</p>\n', '13525', '1', '2018-12-24 20:47:18', '2018-12-24 20:47:19', '0', '1', '3', '1');

-- ----------------------------
-- Table structure for blog_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_tag`;
CREATE TABLE `blog_article_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='文章与标签的对应关系';

-- ----------------------------
-- Records of blog_article_tag
-- ----------------------------
INSERT INTO `blog_article_tag` VALUES ('3', '2', '1');
INSERT INTO `blog_article_tag` VALUES ('4', '1', '1');
INSERT INTO `blog_article_tag` VALUES ('5', '1', '2');
INSERT INTO `blog_article_tag` VALUES ('6', '16', '3');
INSERT INTO `blog_article_tag` VALUES ('7', '16', '4');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='分类表';

-- ----------------------------
-- Records of blog_category
-- ----------------------------
INSERT INTO `blog_category` VALUES ('1', '未分类', 'default');
INSERT INTO `blog_category` VALUES ('2', 'JAVA', 'java');
INSERT INTO `blog_category` VALUES ('3', 'SpringBoot', 'springboot');
INSERT INTO `blog_category` VALUES ('4', 'VUE', 'vue');
INSERT INTO `blog_category` VALUES ('5', '前端技术', 'front');

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL COMMENT '评论文章',
  `author` varchar(50) DEFAULT NULL COMMENT '评论者',
  `email` varchar(200) DEFAULT NULL COMMENT '评论者的邮箱',
  `email_md5` char(32) DEFAULT NULL COMMENT '邮箱MD5值，用于显示gravatar头像',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '评论者的浏览器',
  `content` text CHARACTER SET utf8mb4 COMMENT '评论内容',
  `ip` varchar(128) DEFAULT NULL COMMENT '评论者的ip地址',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '引用的回复，0表示没有引用',
  `is_admin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为博主评论  0：否  1：是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Records of blog_comment
-- ----------------------------
INSERT INTO `blog_comment` VALUES ('1', '1', 'a', '', '2bf0ebee5f19445f2af02908d5c3ab0e', null, '你好', null, '0', '0', '2018-12-15 18:15:04');
INSERT INTO `blog_comment` VALUES ('2', '1', 'b', '', '2bf0ebee5f19445f2af02908d5c3ab0e', '', '我很好', '', '1', '0', '2018-12-15 18:15:04');
INSERT INTO `blog_comment` VALUES ('3', '1', '嘿嘿嘿', '', '2bf0ebee5f19445f2af02908d5c3ab0e', '', '嘿嘿嘿', '', '0', '0', '2018-12-15 18:15:04');
INSERT INTO `blog_comment` VALUES ('4', '1', '沙发', '', '2bf0ebee5f19445f2af02908d5c3ab24', null, '沙发', null, '0', '0', '2018-12-15 18:50:18');
INSERT INTO `blog_comment` VALUES ('5', '1', 'xyn', null, null, null, '我也很好', null, '2', '0', '2018-12-15 23:07:22');

-- ----------------------------
-- Table structure for blog_global
-- ----------------------------
DROP TABLE IF EXISTS `blog_global`;
CREATE TABLE `blog_global` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '变量名',
  `value` varchar(5000) DEFAULT NULL COMMENT '变量值',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING BTREE COMMENT '变量名唯一'
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='全局属性表';

-- ----------------------------
-- Records of blog_global
-- ----------------------------
INSERT INTO `blog_global` VALUES ('1', 'BLOG_TITLE', '周华个人博客', '网站标题');
INSERT INTO `blog_global` VALUES ('2', 'BLOG_KEYWORDS', '周华,个人博客,java', '网站关键词');
INSERT INTO `blog_global` VALUES ('3', 'BLOG_DESCRIPTION', '周华的个人博客。一名懒惰的码农的技术分享平台。', '网站描述');
INSERT INTO `blog_global` VALUES ('4', 'BLOG_URL', 'https://www.iszhouhua.com/', '网站链接');
INSERT INTO `blog_global` VALUES ('5', 'BLOG_AUTHOR', '周华', '网站作者');
INSERT INTO `blog_global` VALUES ('6', 'AVATAR', '/imgs/avatar.jpg', '头像');
INSERT INTO `blog_global` VALUES ('7', 'BLOG_NOTICE', '公告', '公告');
INSERT INTO `blog_global` VALUES ('8', 'DEFAULT_IMAGE', '/imgs/preview/preview4.jpg', '默认预览图');
INSERT INTO `blog_global` VALUES ('9', 'AUTHOR_DESCRIPTION', '一二三四五，上山打老虎。', '头像下的描述内容');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='链接表';

-- ----------------------------
-- Records of blog_link
-- ----------------------------
INSERT INTO `blog_link` VALUES ('1', '周华个人博客', 'https://www.iszhouhua.com', '1');
INSERT INTO `blog_link` VALUES ('2', 'GitHub', 'https://iszhouhua.github,io', '2');
INSERT INTO `blog_link` VALUES ('3', '百度', 'https://www.baidu.com', '1');

-- ----------------------------
-- Table structure for blog_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_log`;
CREATE TABLE `blog_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(128) DEFAULT NULL COMMENT 'IP地址',
  `log_url` varchar(500) DEFAULT NULL COMMENT '访问链接',
  `log_title` varchar(200) DEFAULT NULL COMMENT '访问标题',
  `referer` varchar(500) DEFAULT NULL COMMENT '来源网页',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '浏览器类型',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
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
INSERT INTO `blog_menu` VALUES ('4', 'JAVA', '/category/java/', '0', 'fa-coffee', '9');

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='标签表';

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO `blog_tag` VALUES ('1', 'java基础', 'java-basic');
INSERT INTO `blog_tag` VALUES ('2', '数据结构', 'data-structure');
INSERT INTO `blog_tag` VALUES ('3', '算法', 'algorithm');
INSERT INTO `blog_tag` VALUES ('4', '设计模式', 'design-pattern');
INSERT INTO `blog_tag` VALUES ('5', '数据结构与算法', 'data-structure-and-algorithm');
INSERT INTO `blog_tag` VALUES ('6', '创造型模式', 'creational-pattern');
INSERT INTO `blog_tag` VALUES ('7', '结构型模式', 'structural-pattern');
INSERT INTO `blog_tag` VALUES ('8', '行为型模式', 'behavioral-pattern');
INSERT INTO `blog_tag` VALUES ('9', '策略模式', 'strategy-pattern');
INSERT INTO `blog_tag` VALUES ('10', '观察者模式', 'observer-pattern');
INSERT INTO `blog_tag` VALUES ('11', '装饰模式', 'decorator-pattern');
INSERT INTO `blog_tag` VALUES ('12', '简单工厂模式', 'simple-factory-pattern');
INSERT INTO `blog_tag` VALUES ('13', '工厂方法模式', 'factory_method-pattern');
INSERT INTO `blog_tag` VALUES ('14', '抽象工厂模式', 'abstract-factory-pattern');
INSERT INTO `blog_tag` VALUES ('15', '空间复杂度', 'space-complexity');
INSERT INTO `blog_tag` VALUES ('16', '时间复杂度', 'time-complexity');

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
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', 'f592d0ce304114b279e85b18f804334b', '7af4a47cb431d8f4', 'iszhouhua@163.com', '2018-12-21 20:13:25', '2018-12-21 20:13:25', '0', '0');
