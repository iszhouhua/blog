-- 当存储过程`proc_temp`存在时，删除。
drop procedure if exists proc_temp;$$
-- 创建存储过程`proc_temp`
create procedure proc_temp()
begin
    declare row_num int;
    SELECT count(*) into row_num FROM  information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='sys_user' AND COLUMN_NAME='last_login_time';
    if row_num = 0 then
        -- 修改用户表
        ALTER TABLE `sys_user` CHANGE COLUMN `last_login` `last_login_time`  datetime NULL DEFAULT NULL COMMENT '最后登录时间';
    end if;
    SELECT count(*) into row_num FROM  information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='blog_log' AND COLUMN_NAME='duration';
    if row_num = 0 then
        -- 修改日志表
        ALTER TABLE `blog_log`
            DROP INDEX `uk_ip`,
            DROP COLUMN `update_time`,
            DROP COLUMN `visits`,
            ADD COLUMN `duration` varchar(11) NOT NULL DEFAULT 0 COMMENT '接口响应时长（单位：毫秒）',
            ADD COLUMN `type` varchar(6) NULL DEFAULT NULL COMMENT '访问类型',
            ADD COLUMN `params` text NULL COMMENT '访问参数',
            ADD COLUMN `result` text NULL COMMENT '返回结果',
            ADD COLUMN `method` varchar(200) NULL COMMENT '执行的方法',
            ADD COLUMN `is_normal` tinyint(2) NOT NULL DEFAULT 1 COMMENT '请求是否正常 1：正常 0：异常',
            ADD COLUMN `browser` varchar(50) NULL DEFAULT NULL COMMENT '浏览器',
            ADD COLUMN `operating_system` varchar(100) NULL DEFAULT NULL COMMENT '操作系统';
    end if;

end;$$

-- 调用存储过程`proc_temp`
call proc_temp();$$
drop procedure if exists proc_temp;$$