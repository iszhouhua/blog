package com.iszhouhua.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论表
 * @author ZhouHua
 * @since 2018-12-14
 */
@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 评论的文章，为0表示属于留言内容
     */
    private Long articleId;

    /**
     * 评论者
     */
    private String author;

    /**
     * 评论者的邮箱
     */
    private String email;

    /**
     * 邮箱MD5值，用于显示gravatar头像
     */
    private String emailMd5;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论者的浏览器
     */
    private String userAgent;

    /**
     * 评论者的ip地址
     */
    private String ip;

    /**
     * 引用的回复，0表示没有引用
     */
    private Long parentId;

    /**
     * 是否为博主评论
     */
    private Boolean isAdmin;

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
     * 引用的评论
     */
    @TableField(exist = false)
    List<Comment> comments;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
