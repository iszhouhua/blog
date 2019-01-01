package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.ConfigMapper;
import com.iszhouhua.blog.model.Config;
import com.iszhouhua.blog.model.enums.ConfigTypeEnum;
import com.iszhouhua.blog.service.ConfigService;
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
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public Map<String, String> findAllByType(ConfigTypeEnum type) {
        Map<String, String> options = new HashMap<>();
        List<Config> configs = list(new QueryWrapper<Config>().eq("type",type));
        configs.forEach(variable -> options.put(variable.getName(),variable.getValue()));
        return options;
    }
}
