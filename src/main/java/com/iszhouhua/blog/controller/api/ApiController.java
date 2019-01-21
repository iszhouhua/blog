package com.iszhouhua.blog.controller.api;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.constant.SysConfig;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 控制登录和上传等
 * @author ZhouHua
 * @since 2018-12-22
 */
@RestController
@RequestMapping("api")
@Slf4j
public class ApiController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user 用户名和密码
     * @param session
     * @return
     */
    @PostMapping(value = "login")
    public Result login(User user, HttpSession session) throws Exception {
        //检查是否重复登录
        if(null!=session.getAttribute(Const.USER_SESSION_KEY)){
            return Result.success("已登录",session.getId());
        }
        ValidatorUtils.validate(user);
        Result result = userService.login(user.getUsername(),user.getPassword());
        if(result.getCode()==CodeEnum.SUCCESS.getValue()){
            //登录成功，将用户信息保存至session
            session.setAttribute(Const.USER_SESSION_KEY,result.getData());
            result.setData(session.getId());
        }
        return result;
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("logout")
    public Result logout(HttpSession session) {
        session.removeAttribute(Const.USER_SESSION_KEY);
        return Result.success();
    }

    /**
     * 上传图片
     * @return
     */
    @PostMapping("uploadImage")
    public Result uploadImage(MultipartFile image){
        LocalDateTime dateTime=LocalDateTime.now();
        ValidatorUtils.isNull(image,"上传的图片不能为空");
        Result result=new Result();
        //上传的路径
        String savePath=dateTime.getYear() + "/" + dateTime.getMonthValue() + "/";
        //获取当前年月以创建目录
        File mediaPath = new File(SysConfig.IMAGE_HOME, savePath);
        if (!mediaPath.exists()) {
            mediaPath.mkdirs();
        }
        String fileName = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('.'));
        try {
            image.transferTo(new File(mediaPath.getAbsoluteFile(), fileName));
            result.setMsg("图片上传成功");
            result.setCode(CodeEnum.SUCCESS.getValue());
            result.setData(SysConfig.IMAGE_URL+savePath+fileName);
        } catch (IOException e) {
            log.error("图片上传失败",e.getMessage());
            result.setMsg("图片上传失败");
            result.setCode(CodeEnum.FAIL.getValue());
        }
        return result;
    }
}
