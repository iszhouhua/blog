package com.iszhouhua.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.model.dto.CommentDto;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentDto> selectLatestComments(Integer count);
}
