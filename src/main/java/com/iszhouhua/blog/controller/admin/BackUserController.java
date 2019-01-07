package com.iszhouhua.blog.controller.admin;


import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.User;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 后台用户控制器
 * @author ZhouHua
 * @since 2018-12-22
 */
@RestController
@RequestMapping("admin/user")
public class BackUserController {

    @PutMapping
    public Result info(HttpSession session){
        User user= (User) session.getAttribute(Const.USER_SESSION_KEY);
        return Result.success("获取成功",user);
    }
}
