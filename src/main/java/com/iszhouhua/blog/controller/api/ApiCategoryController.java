package com.iszhouhua.blog.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.pojo.Category;
import com.iszhouhua.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 分类管理
 *
 * @author ZhouHua
 * @since 2018-12-22
 */
@RestController
@RequestMapping("api/category")
public class ApiCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public Result list(Page<Category> page) {
        return Result.success(categoryService.page(page));
    }

    @GetMapping("all")
    public Result all() {
        return Result.success(categoryService.list());
    }

    @PostMapping
    public Result save(@RequestBody Category category) {
        ValidatorUtils.validate(category);
        categoryService.save(category);
        return Result.success(category);
    }

    @PutMapping
    public Result update(@RequestBody Category category) {
        ValidatorUtils.validate(category);
        if (Objects.isNull(category.getId())) {
            return Result.fail("ID不能为空");
        }
        categoryService.updateById(category);
        return Result.success(category);
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success(categoryService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id) {
        return categoryService.removeById(id) ? Result.success() : Result.fail("删除失败");
    }
}
