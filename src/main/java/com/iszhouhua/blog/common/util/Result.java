package com.iszhouhua.blog.common.util;

import com.iszhouhua.blog.common.constant.CodeEnum;
import lombok.Data;

/**
 * 返回结果
 */
@Data
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

    public Result(){
    }

    public Result(int code){
        this.code = code;
    }

    public Result(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return success(null);
    }

    public static Result success(String message) {
        return success(message,null);
    }

    public static Result success(String message, Object data) {
        return new Result(CodeEnum.SUCCESS.getValue(),message,data);
    }

    public static Result fail() {
        return  fail(null);
    }

    public static Result fail(String message) {
        return fail(message,null);
    }

    public static Result fail(String message, Object data) {
        return new Result(CodeEnum.FAIL.getValue(),message,data);
    }
}
