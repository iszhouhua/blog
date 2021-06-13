package com.iszhouhua.blog.common.exception;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;

/**
 * 后台controller异常处理器
 *
 * @author ZhouHua
 * @date 2018/12/22
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.iszhouhua.blog.controller.api")
public class BlogExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BlogException.class)
    public Result handlerBlogException(BlogException e) {
        log.error(e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMsg());
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Result.fail(CodeEnum.DUPLICATE_KEY.getValue(), "数据库中已存在该记录");
    }

    /**
     * 验证失败异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
    public Result handle(Exception e) {
        StringBuilder msg = new StringBuilder();
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                //获得验证不通过的信息
                msg.append(item.getMessage());
            }
        }else if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exs = (MethodArgumentNotValidException)e;
            List<ObjectError> errors = exs.getBindingResult().getAllErrors();
            for (ObjectError error : errors) {
                msg.append(error.getDefaultMessage());
            }
        } else {
            msg.append(e.getMessage());
        }
        log.error(msg.toString(), e);
        return Result.fail(CodeEnum.VALIDATION_ERROR.getValue(), msg.toString());
    }

    /**
     * 其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail(CodeEnum.UNKNOWN_ERROR.getValue(), "未知错误！");
    }
}
