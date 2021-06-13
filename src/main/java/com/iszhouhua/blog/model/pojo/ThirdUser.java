package com.iszhouhua.blog.model.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方用户表
 *
 * @author ZhouHua
 * @date 2020/5/29
 */
@Data
public class ThirdUser implements Serializable {

    private Long id;
    /**
     * 绑定的用户ID
     */
    private Long userId;
    /**
     * 用户第三方系统的唯一id
     */
    private String uuid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户备注（各平台中的用户个人介绍）
     */
    private String remark;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 性别
     */
    private String gender;
    /**
     * 用户来源
     */
    private String source;
    /**
     * 第三方平台返回的原始用户信息
     */
    private String rawUserInfo;

    /**
     * 创建时间
     */
    private Date createTime;
}
