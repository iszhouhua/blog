package com.iszhouhua.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.model.dto.ArticleDto;
import com.iszhouhua.blog.model.dto.CommentDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台文章控制器
 * @author ZhouHua
 * @date 2018/12/1
 */
@Controller
@RequestMapping("article")
public class ArticleController extends BaseController {
    /**
     * 文章详情页
     * @return
     */
    @GetMapping("{url}.html")
    public String index(Model model,@PathVariable(value = "url") String url) {
        ArticleDto info=articleService.findArticleByUrl(url);
        model.addAttribute("info",info);
        //查询当前文章的第一页评论
        if(!info.isComment()){
            IPage<CommentDto> commentPage=commentService.findPageByArticleId(new Page<>(1,10),info.getId());
            model.addAttribute("comments",commentPage);
        }
        return "article";
    }
}
