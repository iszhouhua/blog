package com.iszhouhua.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台api接口
 * @author ZhouHua
 * @date 2018/12/25
 */
@RestController
public class ApiController {
    @Autowired
    private CommentService commentService;
    /**
     * 加载评论
     * @param current 需要加载的页数
     * @param size 需要加载的数量
     * @param articleId 文章ID
     * @return
     */
    @PostMapping("comment/more")
    public List<Comment> commentPage(int current, int size, long articleId){
        return commentService.findPageByArticleId(new Page<>(current,size,false),articleId).getRecords();
    }
}
