package com.iszhouhua.blog.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 链接类型枚举类
 * @author ZhouHua
 * @date 2018/12/26
 */
public enum LinkTypeEnum implements IEnum<Integer> {
    /**
     * 友情链接
     */
    FRIEND_LINK(1,"友情链接"),
    /**
     * 个人链接
     */
    PERSONAL_LINK(2,"个人链接");


    private Integer value;
    private String desc;

    LinkTypeEnum(Integer value, String desc) {
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
