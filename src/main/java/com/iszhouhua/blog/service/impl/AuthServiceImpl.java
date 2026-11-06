package com.iszhouhua.blog.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Policy;
import com.iszhouhua.blog.common.constant.AuthSource;
import com.iszhouhua.blog.common.constant.ConfigConst;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.config.OauthProperties;
import com.iszhouhua.blog.model.pojo.VerificationCode;
import com.iszhouhua.blog.service.AuthService;
import com.iszhouhua.blog.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouhua
 * @since 2021-04-24
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private Cache<String, VerificationCode> cache;
    private final SecureRandom random = new SecureRandom();
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
    public void sendCode(String email,String operation) throws MessagingException {
        VerificationCode code = generateCode(email);
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
        context.setVariable("code", code.getValue());
        context.setVariable("operation", operation);
        context.setVariable("timeoutSeconds", remainingSeconds(email));
        // 第一个参数为模板的名称
        String process = templateEngine.process("mail.html", context);
        // 第二个参数true表示这是一个html文本
        helper.setText(process, true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public synchronized boolean verifyCode(String email, String code) {
        VerificationCode verificationCode = cache.getIfPresent(email);
        if (verificationCode == null || verificationCode.getFailedAttempts() >= Const.CODE_MAX_ATTEMPTS) {
            return false;
        }
        if (code.equals(verificationCode.getValue())) {
            cache.invalidate(email);
            return true;
        }
        verificationCode.setFailedAttempts(verificationCode.getFailedAttempts() + 1);
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

    private synchronized VerificationCode generateCode(String email) {
        VerificationCode code = cache.getIfPresent(email);
        long now = System.currentTimeMillis();
        if (code == null) {
            code = new VerificationCode(String.format("%06d", random.nextInt(1_000_000)));
            cache.put(email, code);
        } else if (code.getFailedAttempts() >= Const.CODE_MAX_ATTEMPTS) {
            throw new BlogException("验证码尝试次数过多，请过期后重新获取");
        } else if (now - code.getLastSentAt() < Const.CODE_RESEND_INTERVAL * 1000L) {
            throw new BlogException("验证码发送过于频繁，请稍后重试");
        }
        // 重发保留原验证码的失败次数和过期时间。
        code.setLastSentAt(now);
        return code;
    }

    private long remainingSeconds(String email) {
        Policy.Expiration<String, VerificationCode> expiration = cache.policy().expireAfterWrite().get();
        long timeout = expiration.getExpiresAfter(TimeUnit.NANOSECONDS);
        long age = expiration.ageOf(email, TimeUnit.NANOSECONDS).orElse(timeout);
        return (timeout - age + TimeUnit.SECONDS.toNanos(1) - 1) / TimeUnit.SECONDS.toNanos(1);
    }
}
