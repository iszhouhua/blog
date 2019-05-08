package com.iszhouhua.blog.common.storage;

import com.iszhouhua.blog.common.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.util.DateUtils;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * 数据存储(支持本地、七牛云、阿里云、腾讯云)
 */
public abstract class BaseStorage {
    /** 云存储配置信息 */
    StorageConfig config;

    BaseStorage(StorageConfig config){
        this.config = config;
    }

    /**
     * 文件路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    String getPath(String prefix, String suffix) {
        //文件路径
        LocalDateTime dateTime=LocalDateTime.now();
        String path = dateTime.getYear() + "/" + dateTime.getMonthValue()+"/" + dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        if(StringUtils.isNotBlank(prefix)){
            path = prefix.endsWith("/")?prefix + path:prefix + "/" + path;
        }
        return path + suffix;
    }

    /**
     * 文件上传
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名
     * @return        返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     * @param data     文件字节数组
     * @param suffix   后缀
     * @return         返回http地址
     */
    public abstract String uploadSuffix(byte[] data, String suffix);

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     * @param inputStream  字节流
     * @param suffix       后缀
     * @return             返回http地址
     */
    public abstract String uploadSuffix(InputStream inputStream, String suffix);

}
