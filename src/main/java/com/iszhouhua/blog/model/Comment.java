package com.iszhouhua.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论表
 *
 * @author ZhouHua
 * @since 2018-12-14
 */
@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 目标类型 1：文章 2：评论
     */
    private Integer targetType;

    /**
     * 评论主体id：文章id、评论id等
     */
    private Long targetId;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 回复目标的用户id
     */
    private Long replyUserId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论者使用的浏览器
     */
    private String userAgent;

    /**
     * 评论者的ip地址
     */
    private String ip;

    /**
     * 父级评论
     */
    private Long parentId;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 评论状态 0：待审核 1：已发布 2：已删除
     */
    private Integer status;

    /**
     * 评论的文章
     */
    @TableField(exist = false)
    private Article article;

    /**
     * 子级评论
     */
    @TableField(exist = false)
    private List<Comment> subComments;

    /**
     * 父级评论
     */
    @TableField(exist = false)
    private Comment parentComment;

    /**
     * 评论人
     */
    @TableField(exist = false)
    private User user;

    /**
     * 被追评的人
     */
    @TableField(exist = false)
    private User replyUser;
}
