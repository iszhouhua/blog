package com.iszhouhua.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
     * 文章描述
     */
    private String description;

    /**
     * 文章内容
     */
    private String content;

    /**
     *  Markdown格式的文章内容
     */
    private String contentMd;

    /**
     * 文章所属分类
     */
    private Long categoryId;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
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
     * 状态 0：草稿 1：已发布 2：已回收
     */
    private Integer status;

    /**
     * 文章标签
     */
    @TableField(exist = false)
    private List<Tag> tags;

    public boolean isTop() {
        return isTop;
    }

    public boolean isComment() {
        return isComment;
    }
}
