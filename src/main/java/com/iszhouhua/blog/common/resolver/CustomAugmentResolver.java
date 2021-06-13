package com.iszhouhua.blog.common.resolver;

import com.iszhouhua.blog.common.annotation.CurrentUser;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.model.pojo.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 对request中的userVo对像封装方法入参
 *
 * @author ZhouHua
 * @date 2020/09/09
 */
public class CustomAugmentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        if (methodParameter.getParameterType().equals(User.class)) {
            return nativeWebRequest.getAttribute(Const.USER_SESSION_KEY, RequestAttributes.SCOPE_SESSION);
        }
        return null;
    }
}
