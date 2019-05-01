package com.iszhouhua.blog.model.enums;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统配置参数名
 * @author ZhouHua
 * @date 2018/04/13
 */
@Slf4j
public enum  ConfigNameEnum {
    /**
     * 评论是否需要校检之后才显示
     */
    COMMENT_CHECK,

    /**
     * 图片保存路径
     */
    IMAGE_HOME,

    /**
     * 图片访问链接
     */
    IMAGE_URL;
}
