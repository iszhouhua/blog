package com.iszhouhua.blog.controller.front;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.common.util.JsonUtils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.pojo.User;
import com.iszhouhua.blog.service.AuthService;
import com.iszhouhua.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 授权相关接口
 *
 * @author ZhouHua
 * @since 2021-5-12
 */
@Slf4j
@Controller
@RequestMapping("oauth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("/render/{source}")
    public void render(@PathVariable("source") String source, @RequestParam(value = "redirect_to",defaultValue = "/") String redirectTo, HttpServletResponse response) throws IOException {
        log.debug("进入render：{}", source);
        AuthRequest authRequest = authService.getAuthRequest(source);
        String authorizeUrl = authRequest.authorize(redirectTo);
        log.debug("authorizeUrl:{}", authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

    /**
     * oauth平台中配置的授权回调地址
     */
    @GetMapping("/callback/{source}")
    public void callback(@PathVariable("source") String source, AuthCallback callback, HttpServletResponse response, HttpSession session) throws IOException {
        PrintWriter writer = null;
        try {
            log.debug("进入callback：{}, callback params：{}", source, JsonUtils.toJson(callback));
            AuthRequest authRequest = authService.getAuthRequest(source);
            AuthResponse<?> result = authRequest.login(callback);
            log.info("login response:{}", JsonUtils.toJson(result));
            if (result.ok()) {
                AuthUser data = (AuthUser) result.getData();
                if (StringUtils.isBlank(data.getEmail())) {
                    throw new BlogException("未获取到邮箱信息");
                }
                User user = userService.getThirdBindUser(data);
                //更新最后登录时间
                user.setLastLoginTime(new Date());
                user.setLoginFailNum(0);
                userService.updateById(user);
                session.setAttribute(Const.USER_SESSION_KEY, user);
                response.sendRedirect(callback.getState());
                return;
            }
            writer = response.getWriter();
            writer.println(JsonUtils.toJson(Result.fail(result.getCode(), result.getMsg())));
            writer.flush();
        } catch (Exception e) {
            log.error("callback is error.", e);
            if (writer == null) {
                writer = response.getWriter();
            }
            writer.println(JsonUtils.toJson(Result.fail(CodeEnum.UNKNOWN_ERROR.getValue(), e.getMessage())));
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
