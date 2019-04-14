package com.iszhouhua.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.exception.BlogException;
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
     * @param pageIndex 需要加载的页码
     * @return
     */
    @GetMapping("/page/{pageIndex}")
    public String index(Model model,@PathVariable(value = "pageIndex") Integer pageIndex) {
        if(pageIndex==1){
            List<Article> topArticles = articleService.findAllTopArticles();
            model.addAttribute("top",topArticles);
        }
        IPage<Article> page=articleService.findPageByKeyword(new Page<>(pageIndex,Const.PAGE_SIZE),null);
        model.addAttribute("page",page);
        // 热门文章
        List<Article> hotArticles=articleService.findHotArticles(Const.HOT_ARTICLE_SIZE);
        model.addAttribute("hotArticles", hotArticles);
        // 热门标签
        model.addAttribute("hotTags", tagService.findHotTags(Const.HOT_TAG_SIZE));
        // 友情链接
        model.addAttribute("friendLinks", linkService.findLinkByType(LinkTypeEnum.FRIEND_LINK.getValue()));
        // 个人链接
        model.addAttribute("personalLinks", linkService.findLinkByType(LinkTypeEnum.PERSONAL_LINK.getValue()));
        //分页代码
        model.addAttribute("pageHtml",pagination((int)page.getCurrent(),(int)page.getPages(), "/page/"));
        return "index";
    }

    /**
     * 文章详情页
     * @return
     */
    @GetMapping("{url}.html")
    public String index(Model model, @PathVariable(value = "url") String url) {
        Article info=articleService.findArticleByUrl(url);
        if(null==info){
            throw new BlogException(CodeEnum.NOT_FOUND.getValue(),"文章不存在："+url);
        }
        model.addAttribute("info",info);
        //查询当前文章的第一页评论
        if(info.isComment()){
            IPage<Comment> commentPage=commentService.findPageByArticleId(new Page<>(1,Const.COMMENT_SIZE),info.getId());
            model.addAttribute("comments",commentPage);
        }
        //上一篇和下一篇
        Article previousArticle=articleService.findPreviousById(info.getId());
        model.addAttribute("previousArticle",previousArticle);
        Article nextArticle=articleService.findNextById(info.getId());
        model.addAttribute("nextArticle",nextArticle);
        //自定义文章
        if(info.getStatus()==ArticleStatusEnum.CUSTOM.getValue()){
            return "custom";
        }
        return "article";
    }
}
