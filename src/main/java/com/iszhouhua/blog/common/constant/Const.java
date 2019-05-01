package com.iszhouhua.blog.common.constant;

/**
 * 常量
 * @author ZhouHua
 * @date 2018/12/17
 */
public interface Const {
    /**
     * 保存用户登录状态的session key
     */
    String USER_SESSION_KEY="USER";

    /**
     * 允许登录失败的次数
     */
    int LOGIN_FAIL_COUNT=5;

    /**
     * 分页时每页显示的条数
     */
    int PAGE_SIZE=5;

    /**
     * 分页系数
     */
    int PAGE_SIDE_NUM=4;

    /**
     * 热门文章显示的数量
     */
    int HOT_ARTICLE_SIZE=8;

    /**
     * 热门标签显示的数量
     */
    int HOT_TAG_SIZE=20;

    /**
     * 文章评论显示的数量
     */
    int COMMENT_SIZE=10;

    /**
     * 压缩阈值，超过此大小的图片将被压缩，此处是256kb
     */
    long COMPRESSION_SIZE=262144L;
    /**
     * 爬虫默认的userAgent
     */
    String USER_AGENT="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
    /**
     * 爬虫抓取链接超时时间，单位：毫秒
     */
   int SPIDER_TIMEOUT=10000;
}
