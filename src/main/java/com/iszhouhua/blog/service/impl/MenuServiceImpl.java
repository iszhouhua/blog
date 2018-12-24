package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.MenuMapper;
import com.iszhouhua.blog.model.Menu;
import com.iszhouhua.blog.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> findAllMenu() {
        return list(new QueryWrapper<Menu>().orderByAsc("sort"));
    }
}
