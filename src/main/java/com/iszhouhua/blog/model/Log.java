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
     * 访问链接
     */
    private String logUrl;

    /**
     * 链接标题
     */
    private String logTitle;

    /**
     * 来源网页
     */
    private String referer;

    /**
     * 浏览器类型
     */
    private String userAgent;

    /**
     * 创建时间
     */
    private Date createTime;


}
