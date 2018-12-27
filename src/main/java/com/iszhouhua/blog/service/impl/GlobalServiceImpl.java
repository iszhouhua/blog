package com.iszhouhua.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iszhouhua.blog.mapper.GlobalMapper;
import com.iszhouhua.blog.model.Global;
import com.iszhouhua.blog.service.GlobalService;
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
public class GlobalServiceImpl extends ServiceImpl<GlobalMapper, Global> implements GlobalService {

    @Override
    public Map<String, Object> findAllGlobal() {
        Map<String, Object> variables = new HashMap<>();
        List<Global> globals = list();
        globals.forEach(variable -> variables.put(variable.getName(),variable.getValue()));
        return variables;
    }
}
