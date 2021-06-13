package com.iszhouhua.blog.model.pojo;

/**
 * 爬虫规则表
 *
 * @author ZhouHua
 * @since 2019-04-16
 */

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@TableName("blog_spider")
public class Spider implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 爬虫标识名
     */
    @NotBlank(message = "爬虫名称不能为空")
    private String name;
    /**
     * 标题爬取规则
     */
    @NotBlank(message = "标题规则不能为空")
    private String titleRule;
    /**
     * 文章内容爬取规则
     */
    @NotBlank(message = "内容规则不能为空")
    private String contentRule;
}
