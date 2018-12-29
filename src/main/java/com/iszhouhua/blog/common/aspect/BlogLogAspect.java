package com.iszhouhua.blog.common.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iszhouhua.blog.common.util.IPUtils;
import com.iszhouhua.blog.model.Log;
import com.iszhouhua.blog.service.LogService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 访客日志，切面处理类
 * @author ZhouHua
 * @date 2018/12/30
 */
@Aspect
@Component
public class BlogLogAspect {
	@Autowired
	private LogService logService;
	
	@Pointcut("execution(public String com.iszhouhua.blog.controller.*.*(..))")
	public void logPointCut() {}

    @Before("logPointCut()")
	public void before(){

        //获取request
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //获得IP地址
        String ip=IPUtils.getIpAddr(request);

        Log log=logService.getOne(new QueryWrapper<Log>().eq("ip",ip));
        if(log!=null){
            log.setVisits(log.getVisits()+1);
            log.setUpdateTime(new Date());
            logService.updateById(log);
        }else{
            log = new Log();

            log.setIp(ip);

            //访问链接
            log.setUrl(request.getRequestURL().toString());

            //获得请求来源
            log.setReferer(request.getHeader("referer"));

            //获得浏览器类型
            log.setUserAgent(request.getHeader("user-agent"));

            //保存访客日志
            logService.save(log);
        }
	}
}
