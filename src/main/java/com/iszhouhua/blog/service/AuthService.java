package com.iszhouhua.blog.service;

import me.zhyd.oauth.request.AuthRequest;

import javax.mail.MessagingException;

/**
 * @author zhouhua
 * @since 2021-04-24
 */
public interface AuthService {
    /**
     * 发送验证码
     * @param email
     * @return
     */
    void sendCode(String email,String operation) throws MessagingException;

    /**
     * 验证验证码
     * @param email
     * @param code
     * @return
     */
    boolean verifyCode(String email, String code);

    /**
     * 根据具体的授权来源，获取授权请求
     *
     * @param source
     * @return
     */
    AuthRequest getAuthRequest(String source);
}
