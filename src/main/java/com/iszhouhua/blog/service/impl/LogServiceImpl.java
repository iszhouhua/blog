package com.iszhouhua.blog.service.impl;

import com.iszhouhua.blog.model.Log;
import com.iszhouhua.blog.mapper.LogMapper;
import com.iszhouhua.blog.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 访客日志服务实现类
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public boolean modifyForVisitsByIp(String ip) {
        return baseMapper.updateForVisitsByIp(ip) > 0;
    }
}
