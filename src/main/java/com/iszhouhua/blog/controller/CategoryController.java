package com.iszhouhua.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.model.Category;
import com.iszhouhua.blog.model.dto.ArticleDto;
import com.iszhouhua.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 前台分类控制器
 * @author ZhouHua
 * @date 2018/12/14
 */
@Controller
@RequestMapping("category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类集合页
     * @return
     */
    @GetMapping
    public String tags(Model model) {
        List<Category> categories=categoryService.list();
        model.addAttribute("list",categories);
        model.addAttribute("title","全部分类");
        return "all_list";
    }

    /**
     * 获得指定分类下的文章
     * @return
     */
    @GetMapping("{url}")
    public String category(Model model,@PathVariable(value = "url") String url) {
        return category(model,url,1);
    }

    @GetMapping("{url}/{pageIndex}")
    public String category(Model model,@PathVariable(value = "url") String url,@PathVariable(value = "pageIndex") Integer pageIndex) {
        Category category=categoryService.getOne(new QueryWrapper<Category>().eq("url",url));
        IPage<ArticleDto> page=articleService.findPageByCategory(new Page<>(pageIndex,5),category.getId());
        model.addAttribute("info",category);
        model.addAttribute("page",page);
        return "list";
    }
}
