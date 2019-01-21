package com.iszhouhua.blog.common.aspect;

import com.google.gson.Gson;
import com.iszhouhua.blog.common.util.IPUtils;
import com.iszhouhua.blog.model.Log;
import com.iszhouhua.blog.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 访客日志，切面处理类
 * @author ZhouHua
 * @date 2018/12/30
 */
@Slf4j
@Aspect
@Component
public class BlogLogAspect {
	@Autowired
	private LogService logService;

    @Before("within(com.iszhouhua.blog.controller.front..*) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void before(){
        //获取request
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //IP地址
        String ip=IPUtils.getIpAddr(request);
        Log blogLog = new Log();
        blogLog.setIp(ip);
        //所在城市
        blogLog.setCity(IPUtils.getCity(ip));
        if(StringUtils.isEmpty(blogLog.getCity())||"0".equals(blogLog.getCity())){
            blogLog.setCity("未知");
        }
        //访问链接
        blogLog.setUrl(request.getRequestURL().toString());
        //请求来源
        blogLog.setReferer(request.getHeader("referer"));
        //浏览器类型
        blogLog.setUserAgent(request.getHeader("user-agent"));
        //同一IP游客只存一次记录，后面访问只增加访问次数
        boolean isExist=logService.isExistLogByIp(ip);
        if(isExist){
            logService.modifyForVisitsByIp(ip);
        }else{
            logService.saveLog(blogLog);
        }
        log.info("访客记录：{}",new Gson().toJson(blogLog));
	}
}
