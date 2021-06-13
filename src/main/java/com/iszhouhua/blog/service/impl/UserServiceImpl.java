package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.ThirdUserMapper;
import com.iszhouhua.blog.mapper.UserMapper;
import com.iszhouhua.blog.model.pojo.ThirdUser;
import com.iszhouhua.blog.model.pojo.User;
import com.iszhouhua.blog.service.UserService;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ThirdUserMapper thirdUserMapper;

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
    public User getThirdBindUser(AuthUser authUser) {
        ThirdUser thirdUser = thirdUserMapper.selectOne(new QueryWrapper<ThirdUser>().eq("username", authUser.getUsername()).eq("source", authUser.getSource()));
        User user;
        if (thirdUser == null) {
            thirdUser = new ThirdUser();
            BeanUtils.copyProperties(authUser, thirdUser);
            thirdUser.setGender(authUser.getGender().getDesc());
            thirdUser.setRawUserInfo(authUser.getRawUserInfo().toJSONString());
            //同一邮箱视为同一用户
            user = findUserByEmail(thirdUser.getEmail());
            if (user == null) {
                user = new User();
                BeanUtils.copyProperties(thirdUser, user);
                if (findUserByUsername(user.getUsername()) != null) {
                    //用户名重复，添加来源平台作为后缀
                    user.setUsername(thirdUser.getUsername() + "_" + thirdUser.getSource());
                }
                user.setIsAdmin(false);
                user.setIsDisable(false);
                user.setLoginFailNum(0);
                user.setCreateTime(new Date());
                save(user);
            }
            thirdUser.setUserId(user.getId());
            thirdUserMapper.insert(thirdUser);
        } else {
            user = findUserById(thirdUser.getUserId());
        }
        return user;
    }

    @Override
    public boolean save(User user) {
        return super.save(user);
    }
}
