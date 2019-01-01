package com.iszhouhua.blog.controller.admin;


import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.model.enums.ConfigTypeEnum;
import com.iszhouhua.blog.service.ConfigService;
import com.iszhouhua.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台首页控制器
 * @author ZhouHua
 * @since 2018-12-22
 */
@Slf4j
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ConfigService globalService;

    /**
     * 登录
     * @param params 参数
     * @param session
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody Map<String, String> params, HttpSession session) throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
        //检查是否重复登录
        User user= (User) session.getAttribute(Const.USER_SESSION_KEY);
        if(null!=user){
            return Result.success("您已经登录过了！",session.getId());
        }
        //非空验证
        if(StringUtils.isEmpty(params.get("username"))){
            return Result.fail("用户名不能为空");
        }
        if(StringUtils.isEmpty(params.get("password"))){
            return Result.fail("密码不能为空");
        }
        Result result = userService.login(params.get("username"),params.get("password"));
        if(result.getCode()==CodeEnum.SUCCESS.getValue()){
            //登录成功，将用户信息保存至session
            user= (User) result.getData();
            session.setAttribute(Const.USER_SESSION_KEY,user);
            Map<String,Object> map=new HashMap<>(8);
            map.put("token",session.getId());
            map.put("name",user.getNickname());
            map.put("avatar",DigestUtils.md5Hex(user.getEmail()));
            map.put("global",globalService.findAllByType(ConfigTypeEnum.GLOBAL_OPTION));
            result.setData(map);
        }
        return result;
    }
}
