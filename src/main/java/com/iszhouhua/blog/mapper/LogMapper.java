package com.iszhouhua.blog.mapper;

import com.iszhouhua.blog.model.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 访客日志表 Mapper 接口
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface LogMapper extends BaseMapper<Log> {
    @Update("update blog_log set visits=visits+1,update_time=now() where ip=#{ip}")
    int updateForVisitsByIp(String ip);
}
