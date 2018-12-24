package com.iszhouhua.blog.controller.admin;


import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 后台用户控制器
 * @author ZhouHua
 * @since 2018-12-22
 */
@Slf4j
@RestController
@RequestMapping("admin/user")
public class BackUserController {
    @Autowired
    private UserService userService;

    /**
     * 获得当前登录用户数据
     * @return
     */
    @GetMapping("info")
    public Result info(HttpSession session){
        User user= (User) session.getAttribute(Const.USER_SESSION_KEY);
        return Result.success("获取成功",user);
    }
}
