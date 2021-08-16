package com.iszhouhua.blog.config;

import com.iszhouhua.blog.common.constant.ConfigConst;
import com.iszhouhua.blog.common.constant.StorageType;
import com.iszhouhua.blog.common.interceptor.AdminInterceptor;
import com.iszhouhua.blog.common.interceptor.LoginInterceptor;
import com.iszhouhua.blog.common.resolver.CustomAugmentResolver;
import com.iszhouhua.blog.common.storage.StorageConfig;
import com.iszhouhua.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * 拦截器
 *
 * @author ZhouHua
 * @date 2018/12/17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //前后台登录后都可以请求的内容
        String[] frontLoginPaths = {
                "/api/comment/save",
                "/api/logout",
                "/api/user/changePass"
        };
        //后台登录拦截器
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/api/**").
                excludePathPatterns(frontLoginPaths)
                .excludePathPatterns(
                        "/api/login",
                        "/api/uploadImage",
                        "/api/comment/more",
                        "/api/register",
                        "/api/sendCode",
                        "/oauth/**",
                        "/api/resetPassword"
                );
        //前台登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(frontLoginPaths);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 访问/admin重定向到/admin/index.html
        registry.addRedirectViewController("/admin", "/admin/index.html");
    }

    /**
     * 跨域配置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    /**
     * 参数解析
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CustomAugmentResolver());
    }

    @Autowired
    private ConfigService configService;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        StorageConfig config = configService.getConfigObject(ConfigConst.FILE_STORAGE, StorageConfig.class);
        if(config.getType()== StorageType.LOCAL){
            String resourceHandler = "/"+config.getLocalPrefix()+"/**";
            String resourceLocations = "file:"+config.getLocalDirectory()+"/"+config.getLocalPrefix()+"/";
            registry.addResourceHandler(resourceHandler.replace("//","/")).addResourceLocations(resourceLocations.replace("//","/"));
        }
    }
}
