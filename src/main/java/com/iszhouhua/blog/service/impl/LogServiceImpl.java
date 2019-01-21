package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.LogMapper;
import com.iszhouhua.blog.model.Log;
import com.iszhouhua.blog.service.LogService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 访客日志服务实现类
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
@CacheConfig(cacheNames="log")
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public boolean modifyForVisitsByIp(String ip) {
        return baseMapper.updateForVisitsByIp(ip) > 0;
    }

    @Override
    @Cacheable(key = "#ip")
    public boolean isExistLogByIp(String ip) {
        return count(new QueryWrapper<Log>().eq("ip",ip)) > 0;
    }

    @Override
    @CachePut(key = "#log.ip")
    public boolean saveLog(Log log) {
        return save(log);
    }

    @Override
    public List<Log> findLatestLog(int number) {
        return baseMapper.selectLatestLog(number);
    }

    @Override
    @Cacheable(key = "targetClass + methodName")
    public Map<String,Integer> statBrowser(){
        Map<String,Integer> result=new HashMap<>();
        List<String> userAgents=baseMapper.selectAllUserAgent();
        for (String userAgent : userAgents) {
            String browserType=UserAgent.parseUserAgentString(userAgent).getBrowser().getGroup().getName();
            if(result.containsKey(browserType)){
                result.put(browserType,result.get(browserType)+1);
            }else{
                result.put(browserType,1);
            }
        }
        return result;
    }

    @Override
    @Cacheable(key = "targetClass + methodName")
    public Map<String, Integer> statOperatingSystem() {
        Map<String,Integer> result=new HashMap<>();
        List<String> userAgents=baseMapper.selectAllUserAgent();
        for (String userAgent : userAgents) {
            String operatingSystem=UserAgent.parseUserAgentString(userAgent).getOperatingSystem().getGroup().getName();
            if(result.containsKey(operatingSystem)){
                result.put(operatingSystem,result.get(operatingSystem)+1);
            }else{
                result.put(operatingSystem,1);
            }
        }
        return result;
    }

    @Override
    @Cacheable(key = "targetClass + methodName")
    public List<Map<String,Integer>> statCity() {
        return baseMapper.selectCityCount();
    }

}
