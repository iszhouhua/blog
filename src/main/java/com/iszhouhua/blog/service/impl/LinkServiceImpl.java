package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.LinkMapper;
import com.iszhouhua.blog.model.Link;
import com.iszhouhua.blog.service.LinkService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 友情链接表 服务实现类
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    @Cacheable(value = "link",key = "targetClass + methodName + #type")
    public List<Link> findLinkByType(Integer type) {
        return list(new QueryWrapper<Link>().eq("type",type));
    }
}
