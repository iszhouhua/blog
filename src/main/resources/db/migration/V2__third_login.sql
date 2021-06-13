ALTER TABLE `blog_user`
    MODIFY COLUMN `password` char(32) NULL DEFAULT NULL,
    MODIFY COLUMN `salt` char(16) NULL DEFAULT NULL;

CREATE TABLE IF NOT EXISTS `blog_third_user`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `user_id`  bigint(20)  NOT NULL COMMENT '用户id',
    `uuid`  varchar(50)  NOT NULL COMMENT '用户第三方系统的唯一id',
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `nickname`    varchar(50)           DEFAULT NULL COMMENT '用户昵称',
    `email`       varchar(200) DEFAULT NULL COMMENT '邮箱',
    `avatar` varchar(200) NULL DEFAULT NULL COMMENT '用户头像',
    `gender` varchar(10) NOT NULL DEFAULT '未知',
    `remark` varchar(200) NULL DEFAULT NULL COMMENT '用户备注',
    `source` varchar(20) NOT NULL COMMENT '数据来源',
    `raw_user_info` text NOT NULL COMMENT '原始用户信息',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_uuid_source` (`username`,`source`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8 COMMENT ='第三方用户表';