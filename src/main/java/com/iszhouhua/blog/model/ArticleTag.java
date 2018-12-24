package com.iszhouhua.blog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文章与标签的对应关系
 * @author ZhouHua
 * @since 2018-12-01
 */
@Data
@NoArgsConstructor
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 标签ID
     */
    private Long tagId;

    public ArticleTag(Long articleId,Long tagId){
        this.articleId=articleId;
        this.tagId=tagId;
    }
}
