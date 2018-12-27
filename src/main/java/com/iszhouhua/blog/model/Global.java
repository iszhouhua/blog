package com.iszhouhua.blog.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 全局变量
 * @author ZhouHua
 * @since 2018-12-01
 */
@Data
public class Global implements Serializable {

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
     * 备注
     */
    @TableField(select = false)
    private String remark;


}
