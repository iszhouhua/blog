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
 * 后台登录拦截器
 *
 * @author ZhouHua
 * @date 2018/12/21
 */
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //如果是管理员则放行
        User user = (User) request.getSession().getAttribute(Const.USER_SESSION_KEY);
        if (Objects.nonNull(user)) {
            if (user.getIsAdmin()) {
                return true;
            } else {
                //非管理员禁止登陆
                throw new BlogException(CodeEnum.FORBIDDEN.getValue(), "权限不足！");
            }
        }
        //否则进行拦截
        throw new BlogException(CodeEnum.NOT_LOGIN.getValue(), "未登录！");
    }
}
