package com.iszhouhua.blog.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 菜单表
 * @author ZhouHua
 * @since 2018-12-01
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 菜单名
     */
    @NotBlank(message = "菜单名不能为空")
    private String name;

    /**
     * 菜单链接
     */
    @NotBlank(message = "菜单链接不能为空")
    private String url;

    /**
     * 是否在新窗口打开菜单
     */
    private Boolean isBlank;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单排序，越小的越靠前
     */

    @NotNull(message = "菜单排序不能为空")
    private Integer sort;

    public boolean isBlank() {
        return isBlank;
    }

    public void setBlank(Boolean blank) {
        isBlank = blank;
    }
}
