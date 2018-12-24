package com.iszhouhua.blog.model.dto;

import com.iszhouhua.blog.model.Category;
import com.iszhouhua.blog.model.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章传输对象
 * @author ZhouHua
 * @date 2018/12/9
 */
@Data
@NoArgsConstructor
public class ArticleDto {
    private Long id;

    /**
     * 文章链接
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
     * 文章分类
     */
    private Category category;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    /**
     * 访问量
     */
    private Integer visits;

    /**
     * 文章的预览图片
     */
    private String image;

    /**
     * 文章标签
     */
    private List<Tag> tags;

    /**
     * 文章是否置顶
     */
    private Boolean isTop;

    /**
     * 是否允许评论
     */
    private Boolean isComment;

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
}
