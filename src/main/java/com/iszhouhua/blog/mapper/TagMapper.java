package com.iszhouhua.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iszhouhua.blog.model.Tag;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 查询当前文章的标签
     * @param articleId 文章ID
     * @return
     */
    List<Tag> selectTagsByArticleId(Long articleId);

    /**
     * 查询热门标签
     * @param hotArticles 热门文章ID集合
     * @return
     */
    List<Tag> selectHotTags(List<Long> hotArticles);
}
