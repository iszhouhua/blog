package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.User;
import org.apache.commons.codec.DecoderException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 用户服务类
 *
 * @author ZhouHua
 * @since 2018-12-17
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param username 用户名或邮箱
     * @param password 密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws DecoderException
     * @throws InvalidKeySpecException
     */
    Result login(String username, String password) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException;

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return
     */
    User findUserById(Long userId);

    /**
     * 根据用户ID修改用户信息
     *
     * @param user 用户
     * @return
     */
    User modifyUserById(User user);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 根据用户邮箱获取用户信息
     *
     * @param email 用户邮箱
     * @return
     */
    User findUserByEmail(String email);
}
