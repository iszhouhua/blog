package com.iszhouhua.blog.common.interceptor;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.model.pojo.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 前台登录拦截器
 *
 * @author ZhouHua
 * @date 2020/6/5
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //如果已登录则放行
        User user = (User) request.getSession().getAttribute(Const.USER_SESSION_KEY);
        if (Objects.nonNull(user)) {
            return true;
        }
        //否则进行拦截
        throw new BlogException(CodeEnum.NOT_LOGIN.getValue(), "未登录！");
    }
}
