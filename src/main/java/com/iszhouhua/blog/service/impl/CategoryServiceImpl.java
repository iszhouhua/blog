package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.CategoryMapper;
import com.iszhouhua.blog.model.Category;
import com.iszhouhua.blog.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 分类服务实现类
 * @author ZhouHua
 * @since 2018-12-14
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
