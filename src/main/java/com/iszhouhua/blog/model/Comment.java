package com.iszhouhua.blog.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
     * 评论文章
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

    public boolean isAdmin() {
        return isAdmin;
    }
}
