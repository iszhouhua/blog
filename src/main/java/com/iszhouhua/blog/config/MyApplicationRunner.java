package com.iszhouhua.blog.config;

import com.xkcoding.http.HttpUtil;
import com.xkcoding.http.support.hutool.HutoolImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动成功之后执行
 * @author zhouhua
 * @since 2021-05-11
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //设置JustAuth使用的http工具
        HttpUtil.setHttp(new HutoolImpl());
    }
}
