package com.iszhouhua.blog.common.interceptor;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.model.pojo.User;
import com.iszhouhua.blog.service.UserService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 前台登录拦截器
 *
 * @author ZhouHua
 * @date 2020/6/5
 */
public class LoginInterceptor implements HandlerInterceptor {
    private final UserService userService;

    public LoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        User user = requireActiveUser(request);
        if (!hasPermission(user)) {
            throw new BlogException(CodeEnum.FORBIDDEN.getValue(), "权限不足！");
        }
        return true;
    }

    protected boolean hasPermission(User user) {
        return true;
    }

    private User requireActiveUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User sessionUser = session == null ? null : (User) session.getAttribute(Const.USER_SESSION_KEY);
        if (sessionUser == null) {
            throw new BlogException(CodeEnum.NOT_LOGIN.getValue(), "未登录！");
        }
        // 权限检查读取数据库当前状态，不使用用户资料缓存。
        User user = userService.getById(sessionUser.getId());
        if (user == null || user.getIsDisable()) {
            session.removeAttribute(Const.USER_SESSION_KEY);
            throw new BlogException(CodeEnum.NOT_LOGIN.getValue(), "账号已失效，请重新登录！");
        }
        session.setAttribute(Const.USER_SESSION_KEY, user);
        return user;
    }
}
