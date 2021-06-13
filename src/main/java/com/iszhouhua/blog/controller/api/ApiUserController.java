package com.iszhouhua.blog.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.annotation.CurrentUser;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.PBKDF2Utils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.pojo.User;
import com.iszhouhua.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
        return Result.success(userPage);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping
    public Result save(@RequestBody User user) throws Exception {
        ValidatorUtils.validate(user);
        if (null != userService.findUserByUsername(user.getUsername())) {
            return Result.fail("用户名已存在");
        }
        if (null != userService.findUserByEmail(user.getEmail())) {
            return Result.fail("邮箱已存在");
        }
        //生成盐
        String salt = PBKDF2Utils.getSalt();
        //加密
        String password = PBKDF2Utils.getPBKDF2(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setLoginFailNum(0);
        user.setCreateTime(new Date());
        boolean flag = userService.save(user);
        if (!flag) {
            return Result.fail("添加失败");
        }
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody User user, HttpSession session, @CurrentUser User currentUser) {
        if (Objects.isNull(user.getId())) {
            return Result.fail(CodeEnum.VALIDATION_ERROR.getValue(), "用户ID不能为空");
        }
        if (currentUser.getId().equals(user.getId())) {
            if (Objects.nonNull(user.getIsDisable()) && user.getIsDisable()) {
                return Result.fail(CodeEnum.VALIDATION_ERROR.getValue(), "不可以禁用自己");
            } else if (Objects.nonNull(user.getIsAdmin()) && !user.getIsAdmin()) {
                return Result.fail(CodeEnum.VALIDATION_ERROR.getValue(), "不可以将自己降为普通用户");
            }
        }
        //密码不可通过此接口更新，将其置空
        user.setPassword(null);
        user.setSalt(null);
        user = userService.modifyUserById(user);
        if (currentUser.getId().equals(user.getId())) {
            currentUser = userService.findUserById(currentUser.getId());
            session.setAttribute(Const.USER_SESSION_KEY, currentUser);
        }
        return Result.success();
    }

    @GetMapping
    public Result info(Long id, @CurrentUser User currentUser) {
        User user = null;
        if (Objects.isNull(id)) {
            user = currentUser;
        } else {
            user = userService.findUserById(id);
        }
        return Result.success(user);
    }

    @PostMapping("changePass")
    public Result changePass(String oldPass, String newPass, HttpSession session, @CurrentUser User currentUser) throws Exception {
        ValidatorUtils.isBlank(oldPass, "原密码不能为空");
        ValidatorUtils.isBlank(newPass, "新密码不能为空");
        boolean checkPass = PBKDF2Utils.verify(oldPass, currentUser.getSalt(), currentUser.getPassword());
        if (!checkPass) {
            return Result.fail("原密码输入错误");
        }
        //生成新盐
        String salt = PBKDF2Utils.getSalt();
        //加密
        String password = PBKDF2Utils.getPBKDF2(newPass, salt);
        currentUser.setSalt(salt);
        currentUser.setPassword(password);
        userService.modifyUserById(currentUser);
        //退出登录
        session.removeAttribute(Const.USER_SESSION_KEY);
        return Result.success();
    }
}
