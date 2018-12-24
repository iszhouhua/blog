package com.iszhouhua.blog.service;

import com.iszhouhua.blog.model.Global;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 全局变量
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface GlobalService extends IService<Global> {
    /**
     * 获取所有全局变量
     * @return 转换成Map之后的变量
     */
    Map<String, Object> findAllGlobal();
}
