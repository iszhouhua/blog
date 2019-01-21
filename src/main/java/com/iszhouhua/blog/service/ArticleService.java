package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.Article;

import java.util.List;

/**
 * 文章服务类
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface ArticleService extends IService<Article> {
    /**
     * 根据链接获取文章
     * @param url 链接
     * @return 文章
     */
    Article findArticleByUrl(String url);

    /**
     * 获得所有置顶文章
     * @return 文章集合
     */
    List<Article> findAllTopArticles();

    /**
     * 根据关键字分页查询文章
     * @param page 分页对象
     * @param keyword 关键字
     * @return
     */
    IPage<Article> findPageByKeyword(Page<Article> page, String keyword);

    /**
     * 分页查询指定标签下的文章
     * @param page 分页对象
     * @param tagId 标签ID
     * @return
     */
    IPage<Article> findPageByTag(Page<Article> page,Long tagId);

    /**
     * 分页查询指定分类文章
     * @param page 分页对象
     * @param categoryId 分类ID
     * @return
     */
    IPage<Article> findPageByCategory(Page<Article> page,Long categoryId);

    /**
     * 查询热门文章
     * @param count 需要查询的数量
     * @return
     */
    List<Article> findHotArticles(Integer count);

    /**
     * 随机查询文章
     * @param count 需要查询的数量
     * @return
     */
    List<Article> findRandomArticles(Integer count);
    /**
     * 根据ID查询上一篇文章
     * @param id 文章ID
     * @return 文章
     */
    Article findPreviousById(Long id);
    /**
     * 根据ID查询下一篇文章
     * @param id 文章ID
     * @return 文章
     */
    Article findNextById(Long id);

    /**
     * 获取最新的n条文章
     * @param number 需要获取的条数
     * @return
     */
    List<Article> findLatestArticle(int number);
}
