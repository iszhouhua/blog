package com.iszhouhua.blog.config;

import com.iszhouhua.blog.common.constant.SysConfig;
import com.iszhouhua.blog.model.enums.ConfigTypeEnum;
import com.iszhouhua.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 项目启动成功之后执行
 * @author ZhouHua
 * @date 2018/12/30
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    private ConfigService configService;

    @Override
    public void run(ApplicationArguments args) {
        //加载配置
        Map<String,String> config=configService.findAllByType(ConfigTypeEnum.SYSTEM_CONFIG.getValue());
        SysConfig.setSysConfig(config);
    }
}
