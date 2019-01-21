package com.iszhouhua.blog.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 访客日志表
 * @author ZhouHua
 * @since 2018-12-01
 */
@Data
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 首次访问的链接
     */
    private String url;

    /**
     * 首次访问的来源
     */
    private String referer;

    /**
     * 首次访问的浏览器类型
     */
    private String userAgent;

    /**
     * 总访问次数
     */
    private Integer visits;

    /**
     * 最后访问时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
}
