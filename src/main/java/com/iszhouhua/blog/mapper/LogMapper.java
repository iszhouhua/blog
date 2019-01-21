package com.iszhouhua.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iszhouhua.blog.model.Log;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 访客日志表 Mapper 接口
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface LogMapper extends BaseMapper<Log> {
    /**
     * 指定IP的访问次数+1
     * @param ip
     * @return
     */
    @Update("update blog_log set visits=visits+1,update_time=now() where ip=#{ip}")
    int updateForVisitsByIp(String ip);

    /**
     * 查询最新的日志
     * @return
     */
    @Select("select * from blog_log order by update_time desc limit #{number}")
    List<Log> selectLatestLog(int number);

    /**
     * 查询所有UserAgent
     * @return
     */
    @Select("select user_agent from blog_log")
    List<String> selectAllUserAgent();

    /**
     * 统计访问最多的十个城市
     * @return
     */
    @Select("SELECT city,COUNT(city) count FROM blog_log GROUP BY city ORDER BY count DESC LIMIT 10")
    List<Map<String,Integer>> selectCityCount();
}
