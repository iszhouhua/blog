package com.iszhouhua.blog.common.storage;


import com.iszhouhua.blog.common.constant.ConfigConst;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.common.util.SpringUtils;
import com.iszhouhua.blog.service.ConfigService;

import static com.iszhouhua.blog.common.constant.StorageType.*;

/**
 * 文件上传Factory
 */
public final class OSSFactory {
    private static ConfigService configService;

    static {
        configService = SpringUtils.getBean(ConfigService.class);
    }

    public static BaseStorage build() {
        //获取存储配置信息
        StorageConfig config = configService.getConfigObject(ConfigConst.FILE_STORAGE, StorageConfig.class);
        switch (config.getType()) {
            case QINIU:
                return new QiniuCloudStorage(config);
            case ALIYUN:
                return new AliyunCloudStorage(config);
            case QCLOUD:
                return new QcloudCloudStorage(config);
            case LOCAL:
                return new LocalStorage(config);
            default:
                throw new BlogException("未配置存储类型");
        }
    }
}
