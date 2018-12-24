package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.ArticleMapper;
import com.iszhouhua.blog.model.Article;
import com.iszhouhua.blog.model.dto.ArticleDto;
import com.iszhouhua.blog.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章服务实现类
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ArticleDto findArticleByUrl(String url) {
        ArticleDto articleDto=articleMapper.selectArticleByUrl(url);
        //访问次数+1
        Article article=new Article();
        article.setId(articleDto.getId());
        article.setVisits(articleDto.getVisits()+1);
        updateById(article);
        return articleDto;
    }

    @Override
    public List<ArticleDto> findAllTopArticles() {
        return articleMapper.selectTopArticles();
    }

    @Override
    public IPage<ArticleDto> findPageByKeyword(Page<Article> page, String keyword) {
        page.setDesc("id");
        keyword=StringUtils.isBlank(keyword)?null:'%'+keyword+'%';
        return articleMapper.selectArticleList(page,keyword);
    }

    @Override
    public IPage<ArticleDto> findPageByTag(Page<Article> page, Long tagId) {
        page.setDesc("id");
        return articleMapper.selectListByTag(page,tagId);
    }

    @Override
    public IPage<ArticleDto> findPageByCategory(Page<Article> page, Long categoryId) {
        page.setDesc("id");
        return articleMapper.selectListByCategory(page,categoryId);
    }

    @Override
    @Cacheable(key = "targetClass + methodName + #count")
    public List<Article> findHotArticles(Integer count) {
        return articleMapper.selectHotArticles(count);
    }

    @Override
    public List<Article> findRandomArticles(Integer count) {
        return articleMapper.selectRandomArticles(count);
    }

}
