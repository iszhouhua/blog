package com.iszhouhua.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.model.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 文章 Mapper 接口
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 根据链接查询文章
     * @param url 文章链接
     * @return 文章
     */
    Article selectArticleByUrl(String url);
    /**
     * 访问次数+1
     * @param id 文章ID
     * @return
     */
    @Update("update blog_article set visits=visits+1 where id=#{id}")
    int updateForVisitsById(Long id);
    /**
     * 查询所有置顶文章
     * @return
     */
    List<Article> selectTopArticles();
    /**
     * 分页查询文章
     * @param page 分页对象
     * @param keyword 关键字
     * @return 分页对象
     */
    Page<Article> selectArticleList(Page page, @Param("keyword")String keyword);
    /**
     * 根据标签ID分页查询文章
     * @param page 分页对象
     * @param tagId 标签ID
     * @return 分页对象
     */
    Page<Article> selectListByTag(Page page, @Param("tagId")Long tagId);
    /**
     * 根据分类ID分页查询文章
     * @param page 分页对象
     * @param categoryId 分类ID
     * @return 分页对象
     */
    Page<Article> selectListByCategory(Page page, @Param("categoryId")Long categoryId);

    /**
     * 查询热门文章
     * @param count 需要查询的数量
     * @return
     */
    @Select("select id,url,title,visits from blog_article where status=1 order by visits desc limit #{count}")
    List<Article> selectHotArticles(Integer count);

    /**
     * 随机查询文章
     * @param count 需要查询的数量
     * @return
     */
    @Select("SELECT t1.id,t1.url,t1.title,t1.visits FROM blog_article AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(id) FROM blog_article)) AS id) AS t2 WHERE t1.id >= t2.id and t1.status=1 ORDER BY t1.id ASC LIMIT #{count}")
    List<Article> selectRandomArticles(Integer count);

    /**
     * 查询上一篇文章
     * @param id 文章ID
     * @return
     */
    @Select("select id,url,title from blog_article where status=1 and id<#{id} limit 1")
    Article selectPreviousById(Long id);

    /**
     * 查询下一篇文章
     * @param id 文章ID
     * @return
     */
    @Select("select id,url,title from blog_article where status=1 and id>#{id} limit 1")
    Article selectNextById(Long id);

    /**
     * 查询最新的文章
     * @return
     */
    @Select("select * from blog_article where status=1 order by id desc limit #{number}")
    List<Article> selectLatestArticle(int number);
}
