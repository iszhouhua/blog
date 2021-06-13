package com.iszhouhua.blog.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.iszhouhua.blog.common.constant.AuthSource;
import com.iszhouhua.blog.common.constant.ConfigConst;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.config.OauthProperties;
import com.iszhouhua.blog.service.AuthService;
import com.iszhouhua.blog.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author zhouhua
 * @since 2021-04-24
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private Cache<String, String> cache;
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private ConfigService configService;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private OauthProperties oauthProperties;

    @Override
    @Async
    public void sendCode(String email,String operation) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject(operation);
        String blogTitle = configService.findByName(ConfigConst.BLOG_TITLE);
        helper.setFrom(blogTitle + "<" + from + ">");
        helper.setTo(email);
        helper.setSentDate(new Date());
        // 这里引入的是Template的Context
        Context context = new Context();
        // 设置模板中的变量
        context.setVariable("code", generateCode(email));
        context.setVariable("operation", operation);
        context.setVariable("timeout", Const.CODE_TIMEOUT);
        // 第一个参数为模板的名称
        String process = templateEngine.process("mail.html", context);
        // 第二个参数true表示这是一个html文本
        helper.setText(process, true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public boolean verifyCode(String email, String code) {
        if (code.equals(cache.getIfPresent(email))) {
            cache.invalidate(email);
            return true;
        }
        return false;
    }

    @Override
    public AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest;
        switch (source.toLowerCase()) {
            case AuthSource.GITHUB:
                authRequest = new AuthGithubRequest(oauthProperties.getGithub());
                break;
            case AuthSource.GITEE:
                authRequest = new AuthGiteeExtendRequest(oauthProperties.getGitee());
                break;
            default:
                throw new BlogException("未获取到有效的Auth配置");
        }
        return authRequest;
    }

    private String generateCode(String email) {
        String captchaStr = cache.getIfPresent(email);
        if (StringUtils.isBlank(captchaStr)) {
            //仅不存在缓存数据的时候才生成新的验证码
            captchaStr = String.format("%06d", (int) (Math.random() * 10000));
        }
        cache.put(email, captchaStr);
        return captchaStr;
    }
}
