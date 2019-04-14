package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.CommentMapper;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.model.enums.CommentStatusEnum;
import com.iszhouhua.blog.service.CommentService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public IPage<Comment> findPageByArticleId(Page<Comment> page,Long articleId) {
        IPage<Comment> commentPage=baseMapper.selectPage(page,new QueryWrapper<Comment>().eq("article_id",articleId).eq("status",CommentStatusEnum.PUBLISHED.getValue()).orderByDesc("id"));
        //获得所有引用评论
        commentPage.getRecords().forEach(comment -> {
            //只要parentId大于0，就表示存在引用评论
            List<Comment> replyList=new ArrayList<>();
            long parentId=comment.getParentId();
            while (parentId>0){
                Comment reply=baseMapper.selectById(parentId);
                if(reply!=null){
                    replyList.add(reply);
                    parentId=reply.getParentId();
                }else{
                    parentId=0;
                }
            }
            comment.setComments(replyList);
        });
        return commentPage;
    }

    @Override
    @Cacheable(key = "targetClass + methodName + #p0 + #p1")
    public List<Comment> findLatestComments(Integer count,boolean showCheck) {
        return baseMapper.selectLatestComments(count,showCheck);
    }

    @Override
    public IPage<Comment> findCommentsByPage(Page<Comment> page, QueryWrapper wrapper) {
        return baseMapper.selectCommentPage(page,wrapper);
    }

    @Override
    public Comment findCommentById(Long id) {
        return baseMapper.selectCommentById(id);
    }
}
