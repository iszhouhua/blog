-- 当存储过程`proc_temp`存在时，删除。
drop procedure if exists proc_temp;$$
-- 创建存储过程`proc_temp`
create procedure proc_temp()
begin
    declare row_num int;
    SELECT count(*) into row_num FROM  information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='blog_article' AND COLUMN_NAME='type';
    if row_num = 0 then
        -- 修改文章表
        ALTER TABLE `blog_article`
            ADD COLUMN `type`  tinyint NOT NULL DEFAULT 0 COMMENT '文章类型 0：普通文章 1：自定义文章';
        UPDATE blog_article SET `type`=1,`status`=1 WHERE `status`=3;
    end if;

end;$$

-- 调用存储过程`proc_temp`
call proc_temp();$$
drop procedure if exists proc_temp;$$