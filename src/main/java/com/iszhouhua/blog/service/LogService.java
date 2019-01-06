package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.Log;

/**
 * 访客日志表 服务类
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface LogService extends IService<Log> {
    /**
     * 根据IP增加访问次数
     * @param ip 访客IP
     * @return
     */
    boolean modifyForVisitsByIp(String ip);
}
