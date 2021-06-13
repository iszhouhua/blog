package com.iszhouhua.blog.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.iszhouhua.blog.common.constant.Const;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author zhouhua
 * @since 2021-04-24
 */
@Configuration
public class CacheConfig {
    @Bean("codeCache")
    public Cache<String, String> caffeineCache() {
        return Caffeine.newBuilder().expireAfterWrite(Const.CODE_TIMEOUT, TimeUnit.MINUTES).build();
    }
}
