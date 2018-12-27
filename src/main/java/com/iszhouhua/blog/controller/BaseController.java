package com.iszhouhua.blog.controller;

import com.iszhouhua.blog.service.ArticleService;
import com.iszhouhua.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * controller 增强器
 * @author ZhouHua
 * @date 2018/12/19
 */
public class BaseController {

    @Autowired
    protected CommentService commentService;

    @Autowired
    protected ArticleService articleService;

    /**
     * 前台controller共用数据
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("latestComments", commentService.findLatestComments(8));
        model.addAttribute("randomArticles", articleService.findRandomArticles(8));
    }

    /**
     * 未找到数据，重定向到404页面
     * @return
     */
    protected String notFound(){
        return "redirect:404.html";
    }
}
