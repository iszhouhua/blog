package com.iszhouhua.blog.common.constant;

/**
 * 常量
 *
 * @author ZhouHua
 * @date 2018/12/17
 */
public class Const {
    /**
     * 保存用户登录状态的session key
     */
    public static final String USER_SESSION_KEY = "USER";

    /**
     * 允许登录失败的次数
     */
    public static final int LOGIN_FAIL_COUNT = 10;

    /**
     * 分页时每页显示的条数
     */
    public static final int PAGE_SIZE = 5;

    /**
     * 分页系数
     */
    public static final int PAGE_SIDE_NUM = 4;

    /**
     * 热门文章显示的数量
     */
    public static final int HOT_ARTICLE_SIZE = 8;

    /**
     * 热门标签显示的数量
     */
    public static final int HOT_TAG_SIZE = 20;

    /**
     * 文章评论显示的数量
     */
    public static final int COMMENT_SIZE = 10;

    /**
     * 压缩阈值，超过此大小的图片将被压缩，此处是512kb
     */
    public static final long COMPRESSION_SIZE = 524288L;
    /**
     * 爬虫默认的userAgent
     */
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
    /**
     * 爬虫抓取链接超时时间，单位：毫秒
     */
    public static final int SPIDER_TIMEOUT = 10000;
    /**
     * 验证码有效时间，单位：分钟
     */
    public static final int CODE_TIMEOUT = 5;
}
