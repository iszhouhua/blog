package com.iszhouhua.blog.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 文章状态枚举类
 * @author ZhouHua
 * @date 2018/12/26
 */
public enum ArticleStatusEnum implements IEnum<Integer> {

    /**
     * 已发布
     */
    PUBLISHED(0, "已发布"),

    /**
     * 草稿
     */
    DRAFT(1, "草稿"),

    /**
     * 回收站
     */
    RECYCLE(2, "回收站"),

    /**
     * 自定义文章
     */
    CUSTOM(3,"自定义文章");

    private Integer value;
    private String desc;

    ArticleStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
