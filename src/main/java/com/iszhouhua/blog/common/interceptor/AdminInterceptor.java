package com.iszhouhua.blog.common.interceptor;

import com.iszhouhua.blog.model.pojo.User;
import com.iszhouhua.blog.service.UserService;

/**
 * 后台登录拦截器
 *
 * @author ZhouHua
 * @date 2018/12/21
 */
public class AdminInterceptor extends LoginInterceptor {

    public AdminInterceptor(UserService userService) {
        super(userService);
    }

    @Override
    protected boolean hasPermission(User user) {
        return user.getIsAdmin();
    }
}
