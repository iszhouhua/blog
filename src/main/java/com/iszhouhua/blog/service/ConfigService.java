package com.iszhouhua.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iszhouhua.blog.model.Config;

import java.util.Map;

/**
 * 全局变量
 * @author ZhouHua
 * @since 2018-12-01
 */
public interface ConfigService extends IService<Config> {
    /**
     * 根据类型获得所有配置
     * @param type 配置类型
     * @return 转换成Map之后的配置
     */
    Map<String, String> findAllByType(Integer type);

    /**
     * 获得所有配置
     * @return 转换成Map之后的配置
     */
    Map<String, String> findAll();

    /**
     * 保存配置
     * @param map
     * @return
     */
    boolean saveByMap(Map<String, String> map);
}
