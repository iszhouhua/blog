package com.iszhouhua.blog.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户表
 * @author ZhouHua
 * @date 2018/12/17
 */
@Data
@TableName("sys_user")
public class User {
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 最后登录时间
     */
    private Date lastLogin;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 登录失败次数，超过一定次数禁止登录
     */
    private Integer loginFail;

    /**
     * 是否禁用 0：否 1：是
     */
    private Boolean isDisable;

    public boolean isDisable() {
        return isDisable;
    }

    public void setDisable(Boolean disable) {
        isDisable = disable;
    }
}
