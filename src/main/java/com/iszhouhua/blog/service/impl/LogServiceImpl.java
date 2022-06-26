package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.LogMapper;
import com.iszhouhua.blog.model.pojo.Log;
import com.iszhouhua.blog.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 访客日志服务实现类
 * @author ZhouHua
 * @since 2018-12-01
 */
@Slf4j
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
    @Override
    public List<Log> findLatestLog(int number) {
        return baseMapper.selectLatestLog(number);
    }

    @Override
    @Async
    public void addLog(Log log) {
        if(Objects.isNull(log.getIp()))return;
        save(log);
    }
}
