package com.iszhouhua.blog.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.iszhouhua.blog.common.util.JsonUtils;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.utils.HttpUtils;
import me.zhyd.oauth.utils.UrlBuilder;

/**
 * @author zhouhua
 * @since 2021-06-06
 */
public class AuthGiteeExtendRequest extends AuthGiteeRequest {
    private String emailUrl = "https://gitee.com/api/v5/emails";

    public AuthGiteeExtendRequest(AuthConfig config) {
        super(config);
    }

    public AuthGiteeExtendRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, authStateCache);
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        AuthUser authUser = super.getUserInfo(authToken);
        String emailInfo = doGetEmail(authToken);
        JsonArray jsonArray = JsonUtils.parseArray(emailInfo);
        for (JsonElement jsonElement : jsonArray) {
            JsonObject childObject = jsonElement.getAsJsonObject();
            if(childObject.get("scope").getAsJsonArray().contains(new JsonPrimitive("primary"))){
                authUser.setEmail(childObject.get("email").getAsString());
                break;
            }
        }
        return authUser;
    }

    /**
     * 获取邮箱信息
     *
     * @param authToken token封装
     * @return Response
     */
    private String doGetEmail(AuthToken authToken) {
        return new HttpUtils(config.getHttpConfig()).get(emailUrl(authToken));
    }



    /**
     * 返回获取email的url
     *
     * @param authToken token
     * @return 返回获取email的url
     */
    private String emailUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(emailUrl).queryParam("access_token", authToken.getAccessToken()).build();
    }
}
