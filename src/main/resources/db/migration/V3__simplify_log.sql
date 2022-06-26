ALTER TABLE `blog_log`
DROP COLUMN `type`,
DROP COLUMN `params`,
DROP COLUMN `result`,
DROP COLUMN `browser`,
DROP COLUMN `operating_system`,
CHANGE COLUMN `city` `region` varchar(20) COMMENT '所属区域',
MODIFY COLUMN `duration` int(11) COMMENT '接口响应时长（单位：毫秒）';