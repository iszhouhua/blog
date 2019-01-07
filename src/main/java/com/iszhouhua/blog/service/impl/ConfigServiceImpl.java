package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.ConfigMapper;
import com.iszhouhua.blog.model.Config;
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
    public Map<String, String> findAllByType(Integer type) {
        List<Config> configs = list(new QueryWrapper<Config>().eq("type",type));
        return listToMap(configs);
    }

    @Override
    public Map<String, String> findAll() {
        return listToMap(list());
    }

    /**
     * 配置集合转map
     * @param list
     * @return
     */
    private Map<String, String> listToMap(List<Config> list){
        Map<String, String> map = new HashMap<>();
        list.forEach(variable -> map.put(variable.getName(),variable.getValue()));
        return map;
    }
}
