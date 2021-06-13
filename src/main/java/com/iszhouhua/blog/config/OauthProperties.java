package com.iszhouhua.blog.config;

import lombok.Data;
import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhouhua
 * @since 2021-05-16
 */
@Component
@ConfigurationProperties(prefix="oauth")
@Data
public class OauthProperties {
    private AuthConfig gitee;
    private AuthConfig github;
}
