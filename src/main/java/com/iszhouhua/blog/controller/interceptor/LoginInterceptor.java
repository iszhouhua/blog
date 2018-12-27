package com.iszhouhua.blog.controller.interceptor;

import com.google.gson.Gson;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.Result;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 后台登录拦截器
 * @author ZhouHua
 * @date 2018/12/21
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果user不为空则放行
        if (null != request.getSession().getAttribute(Const.USER_SESSION_KEY)) {
            return true;
        }
        //否则进行拦截
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        Result result=new Result(CodeEnum.NOT_LOGIN.getValue(),"未登录！");
        out.write(new Gson().toJson(result));
        out.close();
        return false;
    }
}
