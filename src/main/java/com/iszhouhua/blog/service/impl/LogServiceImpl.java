package com.iszhouhua.blog.service.impl;

import com.iszhouhua.blog.model.Log;
import com.iszhouhua.blog.mapper.LogMapper;
import com.iszhouhua.blog.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 访客日志表 服务实现类
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
