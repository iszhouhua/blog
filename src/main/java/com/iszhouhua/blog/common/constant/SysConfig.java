package com.iszhouhua.blog.common.constant;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 系统配置相关变量
 * @author ZhouHua
 * @date 2018/12/30
 */
@Slf4j
public class SysConfig {
    /**
     * 评论是否需要校检之后才显示
     */
    public static boolean COMMENT_CHECK=false;

    /**
     * 图片保存路径
     */
    public static String IMAGE_HOME="/data/image/";

    /**
     * 图片访问链接
     */
    public static String IMAGE_URL="/image/";

    /**
     * 设置系统配置
     * @param configMap 包含配置的map
     */
    public static void setSysConfig(Map<String,String> configMap) {
        Field[] fields = SysConfig.class.getFields();
        for (Field field : fields) {
            if(configMap.get(field.getName())!=null){
                setSysConfig(field.getName(),configMap.get(field.getName()));
            }
        }
    }

    /**
     * 设置系统配置
     * @param name 属性名
     * @param value 属性值
     */
    public static void setSysConfig(String name,String value) {
        try {
            Field field = SysConfig.class.getField(name);
            if(boolean.class.equals(field.getType())){
                field.setBoolean(name,Boolean.parseBoolean(value));
            }else{
                field.set(name,value);
            }
        } catch (NoSuchFieldException e) {
            log.error("属性不存在："+name,e);
        } catch (IllegalAccessException e) {
            log.error("属性值非法："+value,e);
        }
    }
}
