package com.iszhouhua.blog.config;

import com.iszhouhua.blog.controller.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 * @author ZhouHua
 * @date 2018/12/17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //后台登录拦截器
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/login");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 访问/admin重定向到/admin/index.html
        registry.addRedirectViewController("/admin", "/admin/index.html");
    }

    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
