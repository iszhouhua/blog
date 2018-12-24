package com.iszhouhua.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.model.Article;
import com.iszhouhua.blog.model.dto.ArticleDto;
import com.iszhouhua.blog.service.LinkService;
import com.iszhouhua.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 前台首页控制器
 * @author ZhouHua
 * @date 2018/12/1
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private TagService tagService;
    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        return index(model,1);
    }

    /**
     * 首页分页
     * @param pageIndex 当前页
     * @return
     */
    @GetMapping("page/{pageIndex}")
    public String index(Model model,@PathVariable(value = "pageIndex") Integer pageIndex) {
        if(pageIndex==1){
            List<ArticleDto> topArticles = articleService.findAllTopArticles();
            model.addAttribute("top",topArticles);
        }
        IPage<ArticleDto> page=articleService.findPageByKeyword(new Page<>(pageIndex,5),null);
        model.addAttribute("page",page);
        // 热门文章
        List<Article> hotArticles=articleService.findHotArticles(8);
        model.addAttribute("hotArticles", hotArticles);
        // 热门标签
        model.addAttribute("hotTags", tagService.findHotTags(hotArticles));
        // 友情链接
        model.addAttribute("friendLinks", linkService.findLinkByType(1));
        // 个人链接
        model.addAttribute("personalLinks", linkService.findLinkByType(2));
        model.addAttribute("isIndex",true);
        return "index";
    }
}
