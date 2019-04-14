package com.iszhouhua.blog.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Thymeleaf配置
 * @author ZhouHua
 * @date 2018/12/9
 */
@Configuration
public class ThymeleafConfig{

    /**
     * 开启Thymeleaf Layout Dialect支持
     * @return
     */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect(new GroupingStrategy());
    }
}
