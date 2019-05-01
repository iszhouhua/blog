package com.iszhouhua.blog.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 前台controller异常处理器
 * @author ZhouHua
 * @date 2018/12/22
 */
@Slf4j
@ControllerAdvice(basePackages = "com.iszhouhua.blog.controller.front")
public class FrontExceptionHandler {

    /**
     * 自定义异常，统一返回404页面
     */
    @ExceptionHandler(BlogException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerRRException(BlogException e){
        log.error(e.getMessage(), e);
        return "error/404";
    }

    /**
     * 其他未知异常，统一返回500页面
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerException(Exception e){
        log.error(e.getMessage(), e);
        return "error/500";
    }
}
