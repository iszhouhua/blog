package com.iszhouhua.blog.model.enums;

/**
 * 文章状态枚举类
 * @author ZhouHua
 * @date 2018/12/26
 */
public enum ArticleStatusEnum{

    /**
     * 已发布
     */
    PUBLISHED(1, "已发布"),

    /**
     * 草稿
     */
    DRAFT(0, "草稿"),

    /**
     * 回收站
     */
    RECYCLE(2, "回收站"),

    /**
     * 自定义文章
     */
    CUSTOM(3,"自定义文章");

    private int value;
    private String desc;

    ArticleStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
