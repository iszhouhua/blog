package com.iszhouhua.blog.controller.api;


import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.PBKDF2Utils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户管理
 * @author ZhouHua
 * @since 2018-12-22
 */
@RestController
@RequestMapping("api/user")
public class ApiUserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public Result save(@RequestBody User user,HttpSession session){
        ValidatorUtils.validate(user);
        if(StringUtils.isNotEmpty(user.getEmail())){
            user.setEmailMd5(DigestUtils.md5Hex(user.getEmail()));
        }
        boolean res=userService.updateById(user);
        if(!res){
            return Result.fail("保存失败");
        }
        //更新session中的数据
        session.setAttribute(Const.USER_SESSION_KEY,user);
        return Result.success("保存成功",user);
    }

    @PutMapping
    public Result info(HttpSession session){
        User user= (User) session.getAttribute(Const.USER_SESSION_KEY);
        return Result.success("获取成功",user);
    }

    @PostMapping("changePass")
    public Result changePass(String oldPass,String newPass,HttpSession session) throws Exception{
        ValidatorUtils.isBlank(oldPass,"原密码不能为空");
        ValidatorUtils.isBlank(newPass,"新密码不能为空");
        User user= (User) session.getAttribute(Const.USER_SESSION_KEY);
        boolean checkPass=PBKDF2Utils.verify(oldPass,user.getSalt(),user.getPassword());
        if(!checkPass){
            return Result.fail("原密码输入错误");
        }
        //生成新盐
        String salt=PBKDF2Utils.getSalt();
        //加密
        String password=PBKDF2Utils.getPBKDF2(newPass,salt);
        user.setSalt(salt);
        user.setPassword(password);
        userService.updateById(user);
        //退出登录
        session.removeAttribute(Const.USER_SESSION_KEY);
        return Result.success("修改密码成功");
    }
}
