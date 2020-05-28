package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.CommentMapper;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.model.enums.CommentStatusEnum;
import com.iszhouhua.blog.service.CommentService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
@CacheConfig(cacheNames = "comment")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public IPage<Comment> findPageByArticleId(Page<Comment> page, Long articleId) {
        IPage<Comment> commentPage = baseMapper.selectPage(page, new QueryWrapper<Comment>()
                .eq("article_id", articleId)
                .eq("parent_id", 0)
                .eq("status", CommentStatusEnum.PUBLISHED.getValue())
                .orderByDesc("id"));
        //获得文章的非一级评论
        List<Comment> subComments = baseMapper.selectList(new QueryWrapper<Comment>()
                .eq("article_id", articleId)
                .gt("parent_id", 0)
                .eq("status", CommentStatusEnum.PUBLISHED.getValue()));
        if (CollectionUtils.isNotEmpty(subComments)) {
            //子评论拼接
            Map<Long, List<Comment>> subCommentsMap = new HashMap<>();
            for (Comment subComment : subComments) {
                subCommentsMap.compute(subComment.getArticleId(), (k, v) -> {
                    if (v == null) return new ArrayList<>();
                    v.add(subComment);
                    return v;
                });
            }
            commentPage.getRecords().forEach(comment -> comment.setComments(subCommentsMap.get(comment.getArticleId())));
        }
        return commentPage;
    }

    @Override
    @Cacheable(key = "targetClass + methodName + #p0 + #p1")
    public List<Comment> findLatestComments(Integer count, boolean showCheck) {
        return baseMapper.selectLatestComments(count, showCheck);
    }

    @Override
    public IPage<Comment> findCommentsByPage(Page<Comment> page, QueryWrapper wrapper) {
        return baseMapper.selectCommentPage(page, wrapper);
    }

    @Override
    public Comment findCommentById(Long id) {
        return baseMapper.selectCommentById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void clearCache() {
    }
}
