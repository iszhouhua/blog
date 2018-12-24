package com.iszhouhua.blog.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 分类表
 * @author ZhouHua
 * @since 2018-12-14
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 分类链接
     */
    private String url;
}
