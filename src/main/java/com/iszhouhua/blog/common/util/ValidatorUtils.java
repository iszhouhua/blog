package com.iszhouhua.blog.common.util;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.exception.BlogException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BlogException(CodeEnum.VALIDATION_ERROR.getValue(),message);
        }
    }

    public static void isNull(Object obj, String message) {
        if (obj==null) {
            throw new BlogException(CodeEnum.VALIDATION_ERROR.getValue(),message);
        }
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws BlogException  校验不通过，则报BlogException异常
     */
    public static void validate(Object object, Class<?>... groups) throws BlogException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getMessage());
            }
            throw new BlogException(CodeEnum.VALIDATION_ERROR.getValue(),msg.toString());
        }
    }
}
