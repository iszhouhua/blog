package com.iszhouhua.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.model.Comment;
import org.apache.ibatis.annotations.Param;

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
    /**
     * 查询最新的n条评论
     * @param count 需要查询的条数
     * @param showCheck 是否查询待审核的评论
     * @return
     */
    List<Comment> selectLatestComments(@Param("count") Integer count,@Param("showCheck")boolean showCheck);
    /**
     * 分页查询评论
     * @param page
     * @param wrapper
     * @return
     */
    Page<Comment> selectCommentPage(Page page,@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 根据ID查询评论
     * @param id
     * @return
     */
    Comment selectCommentById(Long id);
}
