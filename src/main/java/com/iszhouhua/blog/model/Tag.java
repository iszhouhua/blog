package com.iszhouhua.blog.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 标签表
 * @author ZhouHua
 * @since 2018-12-01
 */
@Data
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 标签链接
     */
    private String url;
}
