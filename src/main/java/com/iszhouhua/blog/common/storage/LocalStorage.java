package com.iszhouhua.blog.common.storage;

import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.exception.BlogException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地存储
 */
public class LocalStorage extends BaseStorage {
    public LocalStorage(StorageConfig config){
        super(config);
    }
    @Override
    public String upload(byte[] data, String path) {
        try {
            FileUtils.writeByteArrayToFile(new File(config.getLocalDirectory(),path),data);
        } catch (IOException e) {
            throw new BlogException(CodeEnum.UPLOAD_ERROR.getValue(),"上传文件失败，请核对本地配置信息", e);
        }
        String domain=config.getLocalDomain();
        if(!domain.endsWith("/")){domain+="/";}
        return domain + path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new BlogException(CodeEnum.UPLOAD_ERROR.getValue(),"上传文件失败", e);
        }
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getLocalPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getLocalPrefix(), suffix));
    }
}
