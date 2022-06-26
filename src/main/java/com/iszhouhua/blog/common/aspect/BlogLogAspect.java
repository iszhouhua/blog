package com.iszhouhua.blog.common.aspect;

import com.iszhouhua.blog.common.util.IPUtils;
import com.iszhouhua.blog.common.util.JsonUtils;
import com.iszhouhua.blog.model.pojo.Log;
import com.iszhouhua.blog.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * 访客日志，切面处理类
 *
 * @author ZhouHua
 * @date 2018/12/30
 */
@Slf4j
@Aspect
@Component
public class BlogLogAspect {
    @Autowired
    private LogService logService;

    @Around("execution(public * com.iszhouhua.blog.controller.front..*.*(..)) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Log blogLog = new Log();
        try {
            //调用目标方法
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            //响应时长
            int duration = Math.toIntExact(endTime - startTime);
            blogLog.setDuration(duration);
            //执行方法
            String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            blogLog.setMethod(method);
            //获取request和response
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            //请求来源
            blogLog.setReferer(request.getHeader("referer"));
            //浏览器
            String userAgentStr = request.getHeader("user-agent");
            blogLog.setUserAgent(userAgentStr);
            //IP地址
            String ip = IPUtils.getIpAddr(request);
            blogLog.setIp(ip);
            //所在城市
            blogLog.setRegion(IPUtils.getRegion(ip));
            //访问链接
            blogLog.setUrl(request.getRequestURL().toString());
            //判断访问是否正常返回
            boolean isNormal = true;
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            if (Objects.nonNull(response) && response.getStatus() != HttpServletResponse.SC_OK) {
                isNormal = false;
            }
            blogLog.setIsNormal(isNormal);
            log.info("访问日志：{}", JsonUtils.toJson(blogLog));
            logService.addLog(blogLog);
            return result;
        } catch (Throwable t) {
            log.error("aop方法执行失败", t);
            blogLog.setIsNormal(false);
            logService.addLog(blogLog);
            throw t;
        }
    }
}
