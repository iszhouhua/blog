package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.User;
import org.apache.commons.codec.DecoderException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 用户服务类
 * @author ZhouHua
 * @since 2018-12-17
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws DecoderException
     * @throws InvalidKeySpecException
     */
    Result login(String username,String password) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException;
}
