package com.iszhouhua.blog.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.PBKDF2Utils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * 用户管理
 *
 * @author ZhouHua
 * @since 2018-12-22
 */
@RestController
@RequestMapping("api/user")
public class ApiUserController {
    @Autowired
    private UserService userService;

    @GetMapping("list")
    public Result list(Page<User> page, User user) {
        String nickname = user.getNickname();
        user.setNickname(null);
        String email = user.getEmail();
        user.setEmail(null);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        if (StringUtils.isNotBlank(nickname)) {
            queryWrapper.like("nickname", nickname);
        }
        if (StringUtils.isNotBlank(email)) {
            queryWrapper.like("email", email);
        }
        IPage<User> userPage = userService.page(page, queryWrapper);
        return Result.success("查询成功", userPage);
    }

    @PutMapping
    public Result update(@RequestBody User user, HttpSession session) {
        if (Objects.isNull(user.getId())) {
            return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "用户ID不能为空");
        }
        User currentUser = (User) session.getAttribute(Const.USER_SESSION_KEY);
        if (currentUser.getId().equals(user.getId())) {
            if (Objects.nonNull(user.getIsDisable()) && user.getIsDisable()) {
                return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "不可以禁用自己");
            } else if (Objects.nonNull(user.getIsAdmin()) && !user.getIsAdmin()) {
                return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "不可以将自己降为普通用户");
            }
        }
        boolean res = userService.updateById(user);
        if (res && currentUser.getId().equals(user.getId())) {
            currentUser = userService.findUserById(currentUser.getId());
            session.setAttribute(Const.USER_SESSION_KEY, currentUser);
        }
        return res ? Result.success("修改成功", user) : Result.fail("修改失败", user);
    }

    @GetMapping
    public Result info(Long id, HttpSession session) {
        User user = null;
        if (Objects.isNull(id)) {
            user = (User) session.getAttribute(Const.USER_SESSION_KEY);
        } else {
            user = userService.findUserById(id);
        }
        return Result.success("获取成功", user);
    }

    @PostMapping("changePass")
    public Result changePass(String oldPass, String newPass, HttpSession session) throws Exception {
        ValidatorUtils.isBlank(oldPass, "原密码不能为空");
        ValidatorUtils.isBlank(newPass, "新密码不能为空");
        User user = (User) session.getAttribute(Const.USER_SESSION_KEY);
        boolean checkPass = PBKDF2Utils.verify(oldPass, user.getSalt(), user.getPassword());
        if (!checkPass) {
            return Result.fail("原密码输入错误");
        }
        //生成新盐
        String salt = PBKDF2Utils.getSalt();
        //加密
        String password = PBKDF2Utils.getPBKDF2(newPass, salt);
        user.setSalt(salt);
        user.setPassword(password);
        userService.updateById(user);
        //退出登录
        session.removeAttribute(Const.USER_SESSION_KEY);
        return Result.success("修改密码成功");
    }
}
