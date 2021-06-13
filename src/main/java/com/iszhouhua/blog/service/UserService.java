package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.pojo.User;
import me.zhyd.oauth.model.AuthUser;

/**
 * 用户服务类
 *
 * @author ZhouHua
 * @since 2018-12-17
 */
public interface UserService extends IService<User> {

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

    /**
     * 获取第三方授权内容绑定的用户信息
     * @param authUser
     * @return
     */
    User getThirdBindUser(AuthUser authUser);
}
