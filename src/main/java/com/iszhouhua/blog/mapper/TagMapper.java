package com.iszhouhua.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iszhouhua.blog.model.Tag;
import org.apache.ibatis.annotations.Select;

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
     * @param count 查询的数量
     * @return
     */
    @Select("SELECT t1.* FROM blog_tag AS t1 LEFT JOIN blog_article_tag AS t2 ON t1.id = t2.tag_id LEFT JOIN blog_article AS t3 ON t2.article_id=t3.id ORDER BY t3.visits DESC LIMIT #{count}")
    List<Tag> selectHotTags(Integer count);
}
