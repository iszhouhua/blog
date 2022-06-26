package com.iszhouhua.blog.model.pojo;

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
     * 所属区域
     */
    private String region;

    /**
     * 访问链接
     */
    private String url;

    /**
     * 来源
     */
    private String referer;

    /**
     * 浏览器信息
     */
    private String userAgent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 接口响应时长（单位：毫秒）
     */
    private Integer duration;

    /**
     * 执行的方法
     */
    private String method;

    /**
     * 请求是否正常 1：正常 0：异常
     */
    private Boolean isNormal;
}
