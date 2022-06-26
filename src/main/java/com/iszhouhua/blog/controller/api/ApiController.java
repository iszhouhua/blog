package com.iszhouhua.blog.controller.api;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.common.storage.OSSFactory;
import com.iszhouhua.blog.common.util.PBKDF2Utils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.param.LoginParam;
import com.iszhouhua.blog.model.param.RegisterParam;
import com.iszhouhua.blog.model.param.ResetPasswordParam;
import com.iszhouhua.blog.model.pojo.Log;
import com.iszhouhua.blog.model.pojo.User;
import com.iszhouhua.blog.service.*;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制登录和上传等
 *
 * @author ZhouHua
 * @since 2018-12-22
 */
@RestController
@RequestMapping("api")
@Slf4j
@Validated
public class ApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private LogService logService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;

    /**
     * 登录
     *
     * @param param    用户名和密码
     * @param session
     * @return
     */
    @PostMapping(value = "login")
    public Result login(@RequestBody LoginParam param, HttpSession session) {
        if (StringUtils.isBlank(param.getUsername())) {
            return Result.fail("用户名或邮箱不能为空");
        }
        if (StringUtils.isBlank(param.getPassword())) {
            return Result.fail("密码不能为空");
        }
        User user = Validator.isEmail(param.getUsername()) ? userService.findUserByEmail(param.getUsername()) : userService.findUserByUsername(param.getUsername());
        if (null == user) {
            return Result.fail("账号不存在");
        }
        if (user.getIsDisable()) {
            return Result.fail("账号已被禁用");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return Result.fail("当前账号暂未设置密码");
        }
        //验证密码是否正确
        boolean res = false;
        try {
            res = PBKDF2Utils.verify(param.getPassword(), user.getSalt(), user.getPassword());
        } catch (Exception e) {
            throw new BlogException("密码验证失败");
        }
        //验证密码是否正确
        if (res) {
            //重置登录失败次数
            user.setLoginFailNum(0);
            //更新最后登录时间
            user.setLastLoginTime(new Date());
            userService.updateById(user);
        } else {
            //密码每错误一次，失败次数+1
            Integer loginFail = user.getLoginFailNum() + 1;
            user.setLoginFailNum(loginFail);
            //超过阈值，禁用账号
            if (loginFail >= Const.LOGIN_FAIL_COUNT) {
                user.setIsDisable(true);
                userService.updateById(user);
                return Result.fail("密码错误，可尝试登陆次数已达上限，账号已禁用！");
            } else {
                userService.updateById(user);
                return Result.fail(String.format("密码错误，连续输错密码%d次后将禁用账号，您已经输错了%d次。", Const.LOGIN_FAIL_COUNT, loginFail));
            }
        }
        //登录成功，将用户信息保存至session
        session.setAttribute(Const.USER_SESSION_KEY, user);
        return Result.success(session.getId());
    }

    /**
     * 注册
     *
     * @param param
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping("register")
    public Result register(@Validated @RequestBody RegisterParam param, HttpSession session) throws Exception {
        if (null != userService.findUserByUsername(param.getUsername())) {
            return Result.fail("用户名已被注册");
        }
        if (null != userService.findUserByEmail(param.getEmail())) {
            return Result.fail("邮箱已被注册");
        }
        if (!authService.verifyCode(param.getEmail(), param.getCode())) {
            return Result.fail("验证码错误");
        }
        User user = new User();
        BeanUtils.copyProperties(param, user);
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
     * 发送验证码
     *
     * @param email
     * @return
     */
    @GetMapping("sendCode")
    public Result sendCode(@NotBlank(message = "邮箱不能为空") @Email(message = "邮箱格式非法") String email,@NotBlank(message = "操作类型不能为空") String operation) throws MessagingException {
        authService.sendCode(email, operation);
        return Result.success();
    }

    /**
     * 重置密码
     *
     * @param param
     * @return
     */
    @PostMapping("resetPassword")
    public Result resetPassword(@Validated @RequestBody ResetPasswordParam param) throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
        User user = userService.findUserByEmail(param.getEmail());
        if (user == null) {
            return Result.fail("账号不存在");
        }
        if (!authService.verifyCode(user.getEmail(), param.getCode())) {
            return Result.fail("验证码错误");
        }
        //生成新盐
        String salt = PBKDF2Utils.getSalt();
        //加密
        String password = PBKDF2Utils.getPBKDF2(param.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        userService.modifyUserById(user);
        return Result.success();
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("logout")
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

    /**
     * 获取统计数据
     *
     * @return
     */
    @GetMapping("statistics")
    public Result statistics() {
        Map<String, Integer> data = new HashMap<>();
        int totalVisits = logService.count();
        data.put("totalVisits", totalVisits);
        int latestVisits = logService.count(new QueryWrapper<Log>().apply("create_time > DATE_SUB(CURDATE(), INTERVAL 1 WEEK)"));
        data.put("latestVisits", latestVisits);
        data.put("totalComment",commentService.count());
        data.put("totalArticle",articleService.count());
        return Result.success(data);
    }
}
