package com.iszhouhua.blog.controller.api;

import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping
    public Result list(Integer type) {
        return type==null?Result.success("查询成功",configService.findAll()):Result.success("查询成功",configService.findAllByType(type));
    }

    @PostMapping
    public Result save(@RequestBody Map<String,String> map){
        configService.saveByMap(map);
        return Result.success("修改成功");
    }
}
