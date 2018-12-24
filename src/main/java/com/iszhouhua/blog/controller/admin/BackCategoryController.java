package com.iszhouhua.blog.controller.admin;


import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类后控制器
 * @author ZhouHua
 * @since 2018-12-22
 */
@RestController
@RequestMapping("admin/category")
public class BackCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public Result list(){
        return Result.success("查询成功",categoryService.list());
    }
}
