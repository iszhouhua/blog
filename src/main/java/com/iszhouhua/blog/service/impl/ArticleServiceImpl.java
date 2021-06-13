package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.ArticleMapper;
import com.iszhouhua.blog.model.pojo.Article;
import com.iszhouhua.blog.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章服务实现类
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public Article findArticleByUrl(String url) {
        Article article = baseMapper.selectArticleByUrl(url);
        return article;
    }

    @Override
    @Async
    public void updateForVisitsById(Long articleId) {
        baseMapper.updateForVisitsById(articleId);
    }

    @Override
    public List<Article> findAllTopArticles() {
        return baseMapper.selectTopArticles();
    }

    @Override
    public IPage<Article> findPageByKeyword(Page<Article> page, String keyword) {
        page.setDesc("id");
        keyword = StringUtils.isBlank(keyword) ? null : '%' + keyword + '%';
        return baseMapper.selectArticleList(page, keyword);
    }

    @Override
    public IPage<Article> findPageByTag(Page<Article> page, Long tagId) {
        page.setDesc("id");
        return baseMapper.selectListByTag(page, tagId);
    }

    @Override
    public IPage<Article> findPageByCategory(Page<Article> page, Long categoryId) {
        page.setDesc("id");
        return baseMapper.selectListByCategory(page, categoryId);
    }

    @Override
    @Cacheable(key = "targetClass + methodName + #count")
    public List<Article> findHotArticles(Integer count) {
        return baseMapper.selectHotArticles(count);
    }

    @Override
    public List<Article> findRandomArticles(Integer count) {
        return baseMapper.selectRandomArticles(count);
    }

    @Override
    public Article findPreviousById(Long id) {
        return baseMapper.selectPreviousById(id);
    }

    @Override
    public Article findNextById(Long id) {
        return baseMapper.selectNextById(id);
    }

    @Override
    public List<Article> findLatestArticle(int number) {
        return baseMapper.selectLatestArticle(number);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Override
    @Cacheable(key = "#articleId")
    public Article findArticleById(Long articleId) {
        return baseMapper.selectById(articleId);
    }
}
