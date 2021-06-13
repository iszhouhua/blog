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
     * 所在城市
     */
    private String city;

    /**
     * 访问链接
     */
    private String url;

    /**
     * 来源
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

    /**
     * 接口响应时长（单位：毫秒）
     */
    private Integer duration;

    /**
     * 访问类型
     */
    private String type;

    /**
     * 访问参数
     */
    private String params;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 执行的方法
     */
    private String method;

    /**
     * 请求是否正常 1：正常 0：异常
     */
    private Boolean isNormal;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String operatingSystem;
}
