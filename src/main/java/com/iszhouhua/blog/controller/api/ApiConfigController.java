package com.iszhouhua.blog.controller.api;

import com.iszhouhua.blog.common.constant.SysConfig;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.Config;
import com.iszhouhua.blog.model.enums.ConfigTypeEnum;
import com.iszhouhua.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * 配置管理
 * @author ZhouHua
 * @since 2019-01-07
 */
@RestController
@RequestMapping("api/config")
public class ApiConfigController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @GetMapping
    public Result list() {
        return Result.success("查询成功",configService.list());
    }

    @PostMapping
    public Result save(@RequestBody Config config){
        ValidatorUtils.validate(config);
        boolean result=configService.saveOrUpdate(config);
        if(result){
            if(ConfigTypeEnum.GLOBAL_OPTION.getValue()==config.getType()){
                //更新全局参数
                thymeleafViewResolver.addStaticVariable(config.getName(),config.getValue());
            }else if(ConfigTypeEnum.SYSTEM_CONFIG.getValue()==config.getType()){
                //更新系统配置
                SysConfig.setSysConfig(config.getName(),config.getValue());
            }
            return Result.success("保存成功");
        }else{
            return Result.success("保存失败");
        }
    }

    @PutMapping
    public Result info(Long id){
        return Result.success("查询成功",configService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id){
        Config config=configService.getById(id);
        boolean result=configService.removeById(id);
        if(result){
            if(ConfigTypeEnum.GLOBAL_OPTION.getValue()==config.getType()){
                //更新全局参数
                thymeleafViewResolver.addStaticVariable(config.getName(),null);
            }else if(ConfigTypeEnum.SYSTEM_CONFIG.getValue()==config.getType()){
                //更新系统配置
                SysConfig.setSysConfig(config.getName(),null);
            }
            return Result.success("删除成功");
        }else{
            return Result.success("删除失败");
        }
    }
}
