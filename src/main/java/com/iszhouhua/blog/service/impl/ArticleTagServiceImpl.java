package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.ArticleTagMapper;
import com.iszhouhua.blog.model.ArticleTag;
import com.iszhouhua.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章与标签的对应关系 服务实现类
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    @Override
    public boolean saveBatch(Long articleId, List<Long> tagIds) {
        remove(new QueryWrapper<ArticleTag>().eq("article_id",articleId).notIn("tag_id",tagIds));
        List<ArticleTag> oldArticleTags=list(new QueryWrapper<ArticleTag>().eq("article_id",articleId));
        oldArticleTags.forEach(articleTag -> tagIds.remove(articleTag.getTagId()));
        if(!tagIds.isEmpty()){
            List<ArticleTag> articleTags=tagIds.stream().map(tagId -> new ArticleTag(articleId,tagId)).collect(Collectors.toList());
            return saveBatch(articleTags);
        }
        return true;
    }
}
