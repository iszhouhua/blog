package com.iszhouhua.blog.model.dto;

import com.iszhouhua.blog.model.Article;
import com.iszhouhua.blog.model.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 评论传输对象
 * @author ZhouHua
 * @since 2018-12-15
 */
@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;

    /**
     * 评论者
     */
    private String author;

    /**
     * 邮箱MD5值，用于显示gravatar头像
     */
    private String emailMd5;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 评论的文章
     */
    private Article article;

    /**
     * 引用的回复
     */
    private List<Comment> comments;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.emailMd5 = comment.getEmailMd5();
        this.createTime = comment.getCreateTime();
        this.content = comment.getContent();
    }
}
