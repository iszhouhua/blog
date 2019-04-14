package com.iszhouhua.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.model.Article;
import com.iszhouhua.blog.model.Category;
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
        return "categories";
    }

    /**
     * 获得指定分类下的文章
     * @return
     */
    @GetMapping("{url}")
    public String category(Model model,@PathVariable(value = "url") String url) {
        return category(model,url,1);
    }

    /**
     *
     * @param model
     * @param url
     * @param pageIndex 需要加载的页码
     * @return
     */
    @GetMapping("{url}/{pageIndex}")
    public String category(Model model,@PathVariable(value = "url") String url,@PathVariable(value = "pageIndex") Integer pageIndex) {
        Category category=categoryService.getOne(new QueryWrapper<Category>().eq("url",url));
        if(null==category){
            throw new BlogException(CodeEnum.NOT_FOUND.getValue(),"类别不存在："+url);
        }
        IPage<Article> page=articleService.findPageByCategory(new Page<>(pageIndex,Const.PAGE_SIZE),category.getId());
        model.addAttribute("info",category);
        model.addAttribute("page",page);
        //分页代码
        model.addAttribute("pageHtml",pagination((int)page.getCurrent(),(int)page.getPages(), "/category/"+url+"/"));
        return "list";
    }
}
