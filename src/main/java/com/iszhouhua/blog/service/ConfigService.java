package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.Config;

import java.util.Map;

/**
 * 配置服务类
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface ConfigService extends IService<Config> {
    /**
     * 获得所有全局变量
     * @return 转换成Map之后的变量
     */
    Map<String, String> findAllGlobal();

    /**
     * 清除配置缓存
     */
    void clearCache();

    /**
     * 根据参数名获得指定参数
     * @return
     */
    String findByName(String name);
}
