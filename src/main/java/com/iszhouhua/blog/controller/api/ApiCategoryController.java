package com.iszhouhua.blog.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.Category;
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
        return Result.success("查询成功", categoryService.page(page));
    }

    @GetMapping("all")
    public Result all() {
        return Result.success("查询成功", categoryService.list());
    }

    @PostMapping
    public Result save(@RequestBody Category category) {
        ValidatorUtils.validate(category);
        categoryService.save(category);
        return Result.success("保存成功", category);
    }

    @PutMapping
    public Result update(@RequestBody Category category) {
        ValidatorUtils.validate(category);
        if (Objects.isNull(category.getId())) {
            return Result.fail("ID不能为空");
        }
        categoryService.updateById(category);
        return Result.success("修改成功", category);
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success("查询成功", categoryService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id) {
        return categoryService.removeById(id) ? Result.success("删除成功") : Result.fail("删除失败");
    }
}
