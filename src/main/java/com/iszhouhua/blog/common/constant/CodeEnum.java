package com.iszhouhua.blog.common.constant;

/**
 * 全局状态码
 * @author ZhouHua
 * @date 2018/10/30
 */
public enum CodeEnum {
    /**
     * 成功
     */
    SUCCESS(1),
    /**
     * 失败
     */
    FAIL(0),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR(-1),
    /**
     * 未登录
     */
    NOT_LOGIN(40001),

    /**
     * 参数校检不通过
     */
    VALIDATION_ERROR(40002),

    /**
     * 重复插入数据
     */
    DUPLICATE_KEY(40003),

    /**
     * 未找到数据
     */
    NOT_FOUND(40004),

    /**
     *  文章抓取失败
     */
    SPIDER_ERROR(40005);

    private int value;

    CodeEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
