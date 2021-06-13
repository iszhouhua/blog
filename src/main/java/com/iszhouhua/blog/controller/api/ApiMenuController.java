package com.iszhouhua.blog.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.pojo.Menu;
import com.iszhouhua.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 目录管理
 *
 * @author ZhouHua
 * @since 2018-01-25
 */
@RestController
@RequestMapping("api/menu")
public class ApiMenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("list")
    public Result list(Page<Menu> page) {
        return Result.success(menuService.page(page));
    }

    @PostMapping
    public Result save(@RequestBody Menu menu) {
        ValidatorUtils.validate(menu);
        menuService.save(menu);
        return Result.success(menu);
    }

    @PutMapping
    public Result update(@RequestBody Menu menu) {
        ValidatorUtils.validate(menu);
        if (Objects.isNull(menu.getId())) {
            return Result.fail("ID不能为空");
        }
        menuService.updateById(menu);
        return Result.success();
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success(menuService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id) {
        return menuService.removeById(id) ? Result.success() : Result.fail("删除失败");
    }
}
