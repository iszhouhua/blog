package com.iszhouhua.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.iszhouhua.blog.model.enums.ArticleStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章表
 * @author ZhouHua
 * @since 2018-12-01
 */
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 文章标题
     */
    private String url;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 文章描述
     */
    private String description;

    /**
     * 文章内容
     */
    @TableField(select = false)
    private String content;

    /**
     *  Markdown格式的文章内容
     */
    @TableField(select = false)
    private String contentMd;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    @TableField(update="now()")
    private Date updateTime;

    /**
     * 文章的预览图片
     */
    private String image;

    /**
     * 文章是否置顶
     */
    private Boolean isTop;

    /**
     * 是否关闭评论评论
     */
    private Boolean isComment;

    /**
     * 访问量
     */
    private Integer visits;

    /**
     * 状态 0：已发布 1：草稿 2：回收站 3：自定义文章
     */
    private ArticleStatusEnum status;

    /**
     * 文章标签
     */
    @TableField(exist = false)
    private List<Tag> tags;

    /**
     * 文章分类
     */
    @TableField(exist = false)
    private Category category;

    /**
     * 获得文章关键字
     * @return
     */
    public String getKeywords() {
        List<String> list=tags.stream().map(Tag::getName).collect(Collectors.toList());
        return String.join(",",list);
    }

    public boolean isTop() {
        return isTop;
    }

    public boolean isComment() {
        return isComment;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }

    public void setComment(Boolean comment) {
        isComment = comment;
    }
}
