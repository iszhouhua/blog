package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface MenuService extends IService<Menu> {
    /**
     * 获得所有菜单
     * @return 菜单集合
     */
    List<Menu> findAllMenu();
}
