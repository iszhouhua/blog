package com.iszhouhua.blog.common.annotation;

import com.iszhouhua.blog.common.constant.Const;

import java.lang.annotation.*;

/**
 * 绑定当前登录的用户
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
    String value() default Const.USER_SESSION_KEY;

}