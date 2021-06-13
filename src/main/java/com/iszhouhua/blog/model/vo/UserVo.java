package com.iszhouhua.blog.model.vo;

import lombok.Data;

/**
 * @author zhouhua
 * @since 2021-05-15
 */
@Data
public class UserVo {
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
     * 用户来源
     */
    private String source;
}
