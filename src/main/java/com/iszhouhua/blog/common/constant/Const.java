package com.iszhouhua.blog.common.constant;

/**
 * 常量
 * @author ZhouHua
 * @date 2018/12/17
 */
public interface Const {
    /**
     * 保存用户登录状态的session key
     */
    String USER_SESSION_KEY="USER";

    /**
     * 允许登录失败的次数
     */
    Integer LOGIN_FAIL_COUNT=5;
}
