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
     * 主键或关键字重复
     */
    DUPLICATE_KEY(40002),

    /**
     * 其他已知异常
     */
    SERVER_ERROR(50000);

    private final int value;

    CodeEnum(int value){
        this.value = value;
    }

    public int value(){
        return value;
    }
}
