package com.iszhouhua.blog.common.storage;


import com.google.gson.JsonObject;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.exception.BlogException;
import com.iszhouhua.blog.common.util.GsonUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯云存储
 */
public class QcloudCloudStorage extends BaseStorage {
    private COSClient client;

    public QcloudCloudStorage(StorageConfig config){
        super(config);
        //初始化
        init();
    }

    private void init(){
    	Credentials credentials = new Credentials(config.getQcloudAppId(), config.getQcloudSecretId(),
                config.getQcloudSecretKey());
    	
    	//初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        clientConfig.setRegion(config.getQcloudRegion());
        
    	client = new COSClient(clientConfig, credentials);
    }

    @Override
    public String upload(byte[] data, String path) {
        //腾讯云必需要以"/"开头
        if(!path.startsWith("/")) {
            path = "/" + path;
        }
        
        //上传到腾讯云
        UploadFileRequest request = new UploadFileRequest(config.getQcloudBucketName(), path, data);
        String response = client.uploadFile(request);
        JsonObject jsonObject = GsonUtils.parseObject(response);
        if(jsonObject.get("code").getAsInt() != 0) {
            throw new BlogException(CodeEnum.UPLOAD_ERROR.getValue(),"文件上传失败，" + jsonObject.get("message"));
        }

        String domain=config.getQcloudDomain();
        if(domain.endsWith("/")){domain=domain.substring(0,domain.length()-1);}
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
        return upload(data, getPath(config.getQcloudPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getQcloudPrefix(), suffix));
    }
}
