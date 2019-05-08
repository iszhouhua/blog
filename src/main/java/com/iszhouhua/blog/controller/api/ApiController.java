package com.iszhouhua.blog.controller.api;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.common.storage.OSSFactory;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.service.ConfigService;
import com.iszhouhua.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
        ValidatorUtils.isNull(image,"上传的图片不能为空");
        if(!image.getContentType().contains("image")){
            throw new BlogException(CodeEnum.VALIDATION_ERROR.getValue(),"文件格式错误");
        }

        //上传文件
        String suffix = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        Result result=new Result();
        try {
            //判断图片是否较大，超过阈值进行压缩
            if(image.getSize()>Const.COMPRESSION_SIZE){
                Thumbnails.Builder builder=Thumbnails.of(image.getInputStream());
                //经测试，png格式图片无法正确压缩，若格式为png，则转换成jpg
                final boolean suffixIsPng=".png".equals(suffix);
                if(suffixIsPng){
                    builder.outputFormat("jpg");
                    suffix=".jpg";
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                builder.scale(1f).outputQuality(0.5f).toOutputStream(bytes);
                String url=OSSFactory.build().uploadSuffix(bytes.toByteArray(), suffix);
                result.setData(url);
            }else{
                String url = OSSFactory.build().uploadSuffix(image.getBytes(), suffix);
                result.setData(url);
            }
            result.setMsg("图片上传成功");
            result.setCode(CodeEnum.SUCCESS.getValue());
        } catch (IOException e) {
            log.error("图片上传失败",e.getMessage());
            result.setMsg("图片上传失败");
            result.setCode(CodeEnum.FAIL.getValue());
        }
        return result;
    }
}
