package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.Link;

import java.util.List;

/**
 * 链接服务类
 * @author ZhouHua
 * @since 2018-12-20
 */
public interface LinkService extends IService<Link> {
    /**
     * 根据类型查询链接
     * @param type 链接类型
     * @return
     */
    List<Link> findLinkByType(Integer type);
}
