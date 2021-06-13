package com.iszhouhua.blog.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author zhouhua
 * @since 2021-04-26
 */
@Data
public class ResetPasswordParam {
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码不能小于6位")
    private String password;
    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "请输入6位验证码")
    private String code;
}
