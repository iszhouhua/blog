package com.iszhouhua.blog.common.util;

import com.iszhouhua.blog.common.constant.CodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回结果
 *
 * @author ZhouHua
 */
@Data
@Accessors(chain = true)
public class Result {

    /**
     * 状态码
     */
    private int code;

    /**
     * 描述
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;

    public static Result success() {
        return success(true);
    }

    public static Result success(Object data) {
        return new Result().setCode(CodeEnum.SUCCESS.getValue()).setData(data);
    }

    public static Result fail(String message) {
        return fail(CodeEnum.FAIL.getValue(), message);
    }

    public static Result fail(int code, String message) {
        return new Result().setCode(code).setMsg(message);
    }
}
