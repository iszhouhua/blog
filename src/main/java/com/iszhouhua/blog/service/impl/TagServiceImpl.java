package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.TagMapper;
import com.iszhouhua.blog.model.Tag;
import com.iszhouhua.blog.service.TagService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签服务实现类
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    @Cacheable(value = "tag",key = "targetClass + methodName + #count")
    public List<Tag> findHotTags(Integer count) {
        return baseMapper.selectHotTags(count);
    }

    @Override
    public List<Tag> findTagsByArticleId(Long articleId) {
        return baseMapper.selectTagsByArticleId(articleId);
    }
}
