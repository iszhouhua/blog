package com.iszhouhua.blog.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 评论状态枚举类
 * @author ZhouHua
 * @date 2018/12/26
 */
public enum CommentStatusEnum implements IEnum<Integer> {


    /**
     * 已发布
     */
    PUBLISHED(0, "已发布"),

    /**
     * 待审核
     */
    CHECKING(1, "待审核"),

    /**
     * 已删除
     */
    RECYCLE(2, "已删除");

    private Integer value;
    private String desc;

    CommentStatusEnum(Integer value, String desc) {
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
