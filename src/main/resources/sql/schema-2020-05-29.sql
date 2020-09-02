-- 当存储过程`proc_temp`存在时，删除。
drop procedure if exists proc_temp;
$$
-- 创建存储过程`proc_temp`
create procedure proc_temp()

begin
    declare row_num int;
    SELECT count(1)
    into row_num
    FROM information_schema.TABLES
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'blog_user';
    if row_num = 0 then
        ALTER TABLE
            `sys_user` rename `blog_user`,
            ADD COLUMN `avatar` varchar(200) NULL DEFAULT NULL COMMENT '用户头像',
            ADD COLUMN `is_admin` bit(1) NOT NULL DEFAULT b'0' COMMENT '用户是否为管理员 0：不是 1：是',
            CHANGE COLUMN `login_fail` `login_fail_num` tinyint(3) NOT NULL DEFAULT 0 COMMENT '登录失败次数，超过一定次数禁止登录',
            DROP COLUMN `email_md5`,
            ADD UNIQUE INDEX `uk_email` (`email`);
        UPDATE `blog_user` SET `is_admin` = 1, `avatar` = '/images/avatar.jpg';
    end if;

    SELECT count(*)
    into row_num
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'blog_comment'
      AND COLUMN_NAME = 'user_id';
    if row_num = 0 then
        -- 新数据不兼容，备份原数据
        ALTER TABLE `blog_comment` rename `blog_comment.bak`;
        -- 重新建一张评论表
        CREATE TABLE `blog_comment`
        (
            `id`            bigint       NOT NULL AUTO_INCREMENT,
            `user_agent`    varchar(500) NULL     DEFAULT NULL COMMENT '评论者使用的浏览器',
            `content`       text         NOT NULL COMMENT '评论内容',
            `ip`            varchar(128) NULL     DEFAULT NULL COMMENT '评论者的ip地址',
            `parent_id`     bigint(20)   NULL     DEFAULT NULL COMMENT '父级评论',
            `user_id`       bigint(20)   NOT NULL COMMENT '评论人',
            `reply_user_id` bigint(20)   NULL     DEFAULT NULL COMMENT '回复的人',
            `target_type`   int(11)      NOT NULL COMMENT '目标类型 1：文章 2：评论',
            `article_id`     bigint(20)   NOT NULL COMMENT '评论的文章',
            `create_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
            `status`        tinyint(2)   NOT NULL COMMENT '评论状态 0：待审核 1：已发布 2：已删除',
            PRIMARY KEY (`id`)
        ) ENGINE = InnoDB
          DEFAULT CHARSET = utf8mb4 COMMENT ='评论表';
    end if;
end;
$$

-- 调用存储过程`proc_temp`
call proc_temp();
$$
drop procedure if exists proc_temp;
$$