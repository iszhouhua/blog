package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.TagMapper;
import com.iszhouhua.blog.model.Article;
import com.iszhouhua.blog.model.Tag;
import com.iszhouhua.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> findHotTags(List<Article> hotArticles) {
        List<Long> idList=hotArticles.stream().map(Article::getId).collect(Collectors.toList());
        return tagMapper.selectHotTags(idList);
    }

    @Override
    public List<Tag> findTagsByArticleId(Long articleId) {
        return tagMapper.selectTagsByArticleId(articleId);
    }
}
