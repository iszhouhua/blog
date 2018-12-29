package com.iszhouhua.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.model.Article;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.model.enums.ArticleStatusEnum;
import com.iszhouhua.blog.model.enums.LinkTypeEnum;
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
            List<Article> topArticles = articleService.findAllTopArticles();
            model.addAttribute("top",topArticles);
        }
        IPage<Article> page=articleService.findPageByKeyword(new Page<>(pageIndex,5),null);
        model.addAttribute("page",page);
        // 热门文章
        List<Article> hotArticles=articleService.findHotArticles(8);
        model.addAttribute("hotArticles", hotArticles);
        // 热门标签
        model.addAttribute("hotTags", tagService.findHotTags(20));
        // 友情链接
        model.addAttribute("friendLinks", linkService.findLinkByType(LinkTypeEnum.FRIEND_LINK));
        // 个人链接
        model.addAttribute("personalLinks", linkService.findLinkByType(LinkTypeEnum.PERSONAL_LINK));
        model.addAttribute("isIndex",true);
        return "index";
    }

    /**
     * 文章详情页
     * @return
     */
    @GetMapping("{url}.html")
    public String index(Model model,@PathVariable(value = "url") String url) {
        Article info=articleService.findArticleByUrl(url);
        if(null==info){
            return notFound();
        }
        model.addAttribute("info",info);
        //查询当前文章的第一页评论
        if(info.isComment()){
            IPage<Comment> commentPage=commentService.findPageByArticleId(new Page<>(1,10),info.getId());
            model.addAttribute("comments",commentPage);
        }
        //自定义文章
        if(info.getStatus()==ArticleStatusEnum.CUSTOM){
            return "custom";
        }
        return "article";
    }

    /**
     * 404页面
     * @return
     */
    @GetMapping("404.html")
    public String error404(){
        return "common/404";
    }
}
