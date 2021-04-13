package com.iszhouhua.blog.controller.api;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.common.storage.OSSFactory;
import com.iszhouhua.blog.common.util.PBKDF2Utils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * 控制登录和上传等
 *
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
     *
     * @param user    用户名和密码
     * @param session
     * @return
     */
    @PostMapping(value = "login")
    public Result login(@RequestBody User user, HttpSession session) throws Exception {
        if (StringUtils.isBlank(user.getUsername())) {
            return Result.fail("用户名或邮箱不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return Result.fail("密码不能为空");
        }
        Result result = userService.login(user.getUsername(), user.getPassword());
        if (result.getCode() == CodeEnum.SUCCESS.getValue()) {
            //登录成功，将用户信息保存至session
            session.setAttribute(Const.USER_SESSION_KEY, result.getData());
            Result.success(session.getId());
        }
        return Result.fail(result.getCode(), result.getMsg());
    }

    /**
     * 注册
     *
     * @param user
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping("register")
    public Result register(@RequestBody User user, HttpSession session) throws Exception {
        ValidatorUtils.validate(user);
        if (null != userService.findUserByUsername(user.getUsername())) {
            return Result.fail("用户名已被注册");
        }
        if (null != userService.findUserByEmail(user.getEmail())) {
            return Result.fail("邮箱已被注册");
        }
        //生成盐
        String salt = PBKDF2Utils.getSalt();
        //加密
        String password = PBKDF2Utils.getPBKDF2(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setIsAdmin(false);
        user.setIsDisable(false);
        user.setLoginFailNum(0);
        user.setCreateTime(new Date());
        boolean flag = userService.save(user);
        if (!flag) {
            return Result.fail("注册失败");
        }
        session.setAttribute(Const.USER_SESSION_KEY, user);
        return Result.success();
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("logout")
    public Result logout(HttpSession session) {
        session.removeAttribute(Const.USER_SESSION_KEY);
        return Result.success();
    }

    /**
     * 上传图片
     *
     * @return
     */
    @PostMapping("uploadImage")
    public Result uploadImage(MultipartFile image) {
        ValidatorUtils.isNull(image, "上传的图片不能为空");
        if (!image.getContentType().contains("image")) {
            throw new BlogException(CodeEnum.VALIDATION_ERROR.getValue(), "文件格式错误");
        }

        //上传文件
        String suffix = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        Result result = new Result();
        try {
            //判断图片是否较大，超过阈值进行压缩
            if (image.getSize() > Const.COMPRESSION_SIZE) {
                Thumbnails.Builder builder = Thumbnails.of(image.getInputStream());
                //经测试，png格式图片无法正确压缩，若格式为png，则转换成jpg
                final boolean suffixIsPng = ".png".equals(suffix);
                if (suffixIsPng) {
                    builder.outputFormat("jpg");
                    suffix = ".jpg";
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                builder.scale(1f).outputQuality(0.5f).toOutputStream(bytes);
                String url = OSSFactory.build().uploadSuffix(bytes.toByteArray(), suffix);
                result.setData(url);
            } else {
                String url = OSSFactory.build().uploadSuffix(image.getBytes(), suffix);
                result.setData(url);
            }
            result.setMsg("图片上传成功");
            result.setCode(CodeEnum.SUCCESS.getValue());
        } catch (IOException e) {
            log.error("图片上传失败", e);
            result.setMsg("图片上传失败");
            result.setCode(CodeEnum.FAIL.getValue());
        }
        return result;
    }
}
