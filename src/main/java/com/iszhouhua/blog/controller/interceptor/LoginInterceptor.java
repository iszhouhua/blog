package com.iszhouhua.blog.controller.interceptor;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台登录拦截器
 * @author ZhouHua
 * @date 2018/12/21
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //如果user不为空则放行
        if (null != request.getSession().getAttribute(Const.USER_SESSION_KEY)) {
            return true;
        }
        //否则进行拦截
        throw new BlogException(CodeEnum.NOT_LOGIN.getValue(),"未登录！");
    }
}
