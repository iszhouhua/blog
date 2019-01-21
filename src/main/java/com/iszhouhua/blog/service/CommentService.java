package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.Comment;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface CommentService extends IService<Comment> {

    /**
     * 分页查询当前文章的所有评论
     * @param page 分页信息
     * @param articleId 文章ID
     * @return
     */
    IPage<Comment> findPageByArticleId(Page<Comment> page, Long articleId);

    /**
     * 查询最新的评论
     * @param count 需要查询的评论数量
     * @param showCheck 是否查询待审核的评论
     * @return
     */
    List<Comment> findLatestComments(Integer count,boolean showCheck);

    /**
     * 分页查询评论
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Comment> findCommentsByPage(Page<Comment> page, QueryWrapper wrapper);

    /**
     * 根据ID查询评论信息
     * @param id
     * @return
     */
    Comment findCommentById(Long id);
}
