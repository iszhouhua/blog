package com.iszhouhua.blog.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.constant.ConfigConst;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.pojo.Config;
import com.iszhouhua.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 配置管理
 *
 * @author ZhouHua
 * @since 2019-01-07
 */
@RestController
@RequestMapping("api/config")
public class ApiConfigController {
    @Autowired
    private ConfigService configService;

    @GetMapping("list")
    public Result list(Page<Config> page) {
        return Result.success(configService.page(page));
    }

    @PostMapping
    public Result save(@RequestBody Config config) {
        ValidatorUtils.validate(config);
        if (config.getName().equals(ConfigConst.FILE_STORAGE)) {
            ValidatorUtils.validateStorageConfig(config.getValue());
        }
        boolean result = configService.save(config);
        if (result) {
            configService.clearCache();
            return Result.success();
        } else {
            return Result.fail("保存失败");
        }
    }


    @PutMapping
    public Result update(@RequestBody Config config) {
        ValidatorUtils.validate(config);
        if (Objects.isNull(config.getId())) {
            return Result.fail("ID不能为空");
        }
        if (config.getName().equals(ConfigConst.FILE_STORAGE)) {
            ValidatorUtils.validateStorageConfig(config.getValue());
        }
        boolean result = configService.updateById(config);
        if (result) {
            configService.clearCache();
            return Result.success();
        } else {
            return Result.fail("修改失败");
        }
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success(configService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id) {
        boolean result = configService.removeById(id);
        if (result) {
            configService.clearCache();
            return Result.success();
        } else {
            return Result.fail("删除失败");
        }
    }

    @GetMapping("global")
    public Result global() {
        return Result.success(configService.findAllGlobal());
    }
}
