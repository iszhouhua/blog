package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.ConfigMapper;
import com.iszhouhua.blog.model.Config;
import com.iszhouhua.blog.model.enums.ConfigTypeEnum;
import com.iszhouhua.blog.service.ConfigService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局参数
 * @author ZhouHua
 * @since 2018-12-01
 */
@Service
@CacheConfig(cacheNames = "config")
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    @Cacheable(key = "targetClass + methodName")
    public Map<String, String> findAllGlobal() {
        Map<String, String> result = new HashMap<>();
        List<Config> configs = list(new QueryWrapper<Config>().eq("type",ConfigTypeEnum.GLOBAL_OPTION.getValue()));
        configs.forEach(variable -> result.put(variable.getName(),variable.getValue()));
        return result;
    }

    @Override
    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Override
    @Cacheable(key = "#name")
    public String findByName(String name) {
        return baseMapper.selectConfigByName(name);
    }
}
