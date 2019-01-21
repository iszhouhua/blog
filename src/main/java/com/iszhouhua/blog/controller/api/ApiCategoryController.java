package com.iszhouhua.blog.controller.api;


import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.Category;
import com.iszhouhua.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 * @author ZhouHua
 * @since 2018-12-22
 */
@RestController
@RequestMapping("api/category")
public class ApiCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result list(){
        return Result.success("查询成功",categoryService.list());
    }

    @PostMapping
    public Result save(@RequestBody Category category){
        ValidatorUtils.validate(category);
        categoryService.saveOrUpdate(category);
        return Result.success("保存成功",category);
    }

    @PutMapping
    public Result info(Long id){
        return Result.success("查询成功",categoryService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id){
        return categoryService.removeById(id)?Result.success("删除成功"):Result.fail("删除失败");
    }
}
