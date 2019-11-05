package com.iszhouhua.blog.common.storage;


import com.iszhouhua.blog.common.constant.ConfigConst;
import com.iszhouhua.blog.common.constant.StorageType;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.common.util.SpringUtils;
import com.iszhouhua.blog.service.ConfigService;
import org.apache.commons.lang3.StringUtils;

/**
 * 文件上传Factory
 */
public final class OSSFactory {
    private static ConfigService configService;

    static {
       configService = SpringUtils.getBean(ConfigService.class);
    }

    public static BaseStorage build(){
        //获取存储配置信息
        StorageConfig config = configService.getConfigObject(ConfigConst.FILE_STORAGE, StorageConfig.class);
        if(config.getType() == StorageType.QINIU.getValue()){
            return new QiniuCloudStorage(config);
        }else if(config.getType() == StorageType.ALIYUN.getValue()){
            return new AliyunCloudStorage(config);
        }else if(config.getType() == StorageType.QCLOUD.getValue()){
            return new QcloudCloudStorage(config);
        }else if(config.getType() == StorageType.LOCAL.getValue()){
            if(StringUtils.isBlank(config.getLocalDirectory())){
                //未指定存储目录，默认存储于项目静态文件夹内
                String localDirectory=System.getProperty("user.dir")+"/src/main/resources/static";
                config.setLocalDirectory(localDirectory);
                config.setLocalDomain(configService.findByName(ConfigConst.DOMIN));
            }
            return new LocalStorage(config);
        }
        throw new BlogException("未配置存储类型");
    }
}
