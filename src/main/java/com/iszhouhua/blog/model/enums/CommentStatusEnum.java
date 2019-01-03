package com.iszhouhua.blog.model.enums;

/**
 * 评论状态枚举类
 * @author ZhouHua
 * @date 2018/12/26
 */
public enum CommentStatusEnum {


    /**
     * 待审核
     */
    CHECKING(0, "待审核"),

    /**
     * 已发布
     */
    PUBLISHED(1, "已发布"),

    /**
     * 已删除
     */
    RECYCLE(2, "已删除");

    private int value;
    private String desc;

    CommentStatusEnum(Integer value, String desc) {
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
