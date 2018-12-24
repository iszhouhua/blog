package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.Article;
import com.iszhouhua.blog.model.Tag;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface TagService extends IService<Tag> {
    /**
     * 查询热门标签
     * @param hotArticles 热门文章
     * @return
     */
    List<Tag> findHotTags(List<Article> hotArticles);

    /**
     * 查询当前文章的标签
     * @param articleId 文章ID
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);
}
