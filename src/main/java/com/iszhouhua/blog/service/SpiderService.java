package com.iszhouhua.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.Article;
import com.iszhouhua.blog.model.Spider;

import java.io.IOException;

/**
 * 爬虫规则服务类
 * @author ZhouHua
 * @since 2018-12-17
 */
public interface SpiderService extends IService<Spider> {
    /**
     * 抓取文章
     * @param spider 抓取规则
     * @param url 抓取的链接
     * @return 抓取到的文章
     */
    Article spiderArticle(Spider spider,String url) throws IOException;
}
