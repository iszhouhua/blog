package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.CommentMapper;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.model.dto.CommentDto;
import com.iszhouhua.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Cacheable(key = "targetClass + methodName + #p0.current + #p1")
    public IPage<CommentDto> findPageByArticleId(Page<Comment> page,Long articleId) {
        IPage<Comment> commentPage=commentMapper.selectPage(page,new QueryWrapper<Comment>().eq("article_id",articleId).orderByDesc("id"));
        List<CommentDto> commentDtoList=new ArrayList<>();
        //获得所有引用评论
        commentPage.getRecords().forEach(comment -> {
            CommentDto commentDto=new CommentDto(comment);
            //只要parentId大于0，就表示存在引用评论
            List<Comment> replyList=new ArrayList<>();
            long parentId=comment.getParentId();
            while (parentId>0){
                Comment reply=commentMapper.selectById(parentId);
                replyList.add(reply);
                parentId=reply.getParentId();
            }
            commentDto.setComments(replyList);
            commentDtoList.add(commentDto);
        });
        IPage<CommentDto> commentDtoPage=new Page<>(commentPage.getCurrent(),commentPage.getSize(),commentPage.getTotal(),false);
        commentDtoPage.setRecords(commentDtoList);
        return commentDtoPage;
    }

    @Override
    @Cacheable(key = "targetClass + methodName + #count")
    public List<CommentDto> findLatestComments(Integer count) {
        return commentMapper.selectLatestComments(count);
    }
}
