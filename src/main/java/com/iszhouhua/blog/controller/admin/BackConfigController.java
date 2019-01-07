package com.iszhouhua.blog.controller.admin;

import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台配置控制器
 * @author ZhouHua
 * @since 2019-01-07
 */
@RestController
@RequestMapping("admin/config")
public class BackConfigController {
    @Autowired
    private ConfigService configService;

    @GetMapping
    public Result list(Integer type) {
        return type==null?Result.success("查询成功",configService.findAll()):Result.success("查询成功",configService.findAllByType(type));
    }
}
