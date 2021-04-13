package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.PBKDF2Utils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.mapper.UserMapper;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.service.UserService;
import org.apache.commons.codec.DecoderException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/**
 * 用户服务实现类
 *
 * @author ZhouHua
 * @date 2018/12/17
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Result login(String username, String password) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException {
        Result result = new Result();
        //判断账号是否可用
        User user = getOne(new QueryWrapper<User>().eq("username", username).or().eq("email", username));
        if (null == user) {
            result.setMsg("账号不存在");
            return result;
        }
        if (user.getIsDisable()) {
            result.setMsg("账号已被禁用");
            return result;
        }
        //验证密码是否正确
        boolean res = PBKDF2Utils.verify(password, user.getSalt(), user.getPassword());
        //验证密码是否正确
        if (res) {
            result.setCode(CodeEnum.SUCCESS.getValue());
            result.setMsg("登录成功");
            result.setData(user);
            //重置登录失败次数
            user.setLoginFailNum(0);
            //更新最后登录时间
            user.setLastLoginTime(new Date());
        } else {
            //密码每错误一次，失败次数+1
            Integer loginFail = user.getLoginFailNum() + 1;
            user.setLoginFailNum(loginFail);
            //超过阈值，禁用账号
            if (loginFail >= Const.LOGIN_FAIL_COUNT) {
                user.setIsDisable(true);
                result.setMsg("密码错误，可尝试登陆次数已达上限，账号已禁用！");
            } else {
                result.setMsg(String.format("密码错误，连续输错密码%d次后将禁用账号，您已经输错了%d次。", Const.LOGIN_FAIL_COUNT, loginFail));
            }
        }
        updateById(user);
        return result;
    }

    @Override
    @Cacheable(key = "#userId")
    public User findUserById(Long userId) {
        return baseMapper.selectById(userId);
    }

    @Override
    public User modifyUserById(User user) {
        updateById(user);
        return baseMapper.selectById(user.getId());
    }

    @Override
    public User findUserByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public User findUserByEmail(String email) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("email", email));
    }

    @Override
    public boolean save(User user) {
        return super.save(user);
    }
}
