package com.iszhouhua.blog.model.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author ZhouHua
 * @date 2020/5/29
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码不能小于6位")
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 登录失败次数，超过一定次数禁用账号
     */
    private Integer loginFailNum;

    /**
     * 是否禁用 0：否 1：是
     */
    private Boolean isDisable;

    /**
     * 用户是否为管理员 0：不是 1：是
     */
    private Boolean isAdmin;

    /**
     * 用户头像
     */
    private String avatar;
}
