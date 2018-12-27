package com.iszhouhua.blog.common.exception;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * controller异常处理器
 * @author ZhouHua
 * @date 2018/12/22
 */
@Slf4j
@RestControllerAdvice
public class BlogExceptionHandler {

    /**
     * 处理已捕获异常
     */
    @ExceptionHandler(BlogException.class)
    public Result handleRRException(BlogException e){
        log.error(e.getMessage(), e);
        return new Result(e.getCode(),e.getMsg());
    }

    /**
     * 其他未捕获异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error(e.getMessage(), e);
        return new Result(CodeEnum.UNKNOWN_ERROR.getValue(),e.getMessage());
    }
}
