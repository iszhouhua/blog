package com.iszhouhua.blog.model.vo;

import com.iszhouhua.blog.model.pojo.User;
import lombok.Data;

/**
 * @author zhouhua
 * @since 2021-05-15
 */
@Data
public class UserVo {
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    public static UserVo from(User user) {
        if (user == null) {
            return null;
        }
        UserVo result = new UserVo();
        result.setId(user.getId());
        result.setNickname(user.getNickname());
        result.setAvatar(user.getAvatar());
        return result;
    }
}
