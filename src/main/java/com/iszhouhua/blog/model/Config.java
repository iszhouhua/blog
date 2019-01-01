package com.iszhouhua.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.iszhouhua.blog.model.enums.ConfigTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 博客配置
 * @author ZhouHua
 * @since 2018-12-01
 */
@Data
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 变量名
     */
    private String name;

    /**
     * 变量值
     */
    private String value;

    /**
     * 配置类型 1：全局参数 2：系统配置
     */
    private ConfigTypeEnum type;

    /**
     * 描述
     */
    @TableField(select = false)
    private String description;
}
