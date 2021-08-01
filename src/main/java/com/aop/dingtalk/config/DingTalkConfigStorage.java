package com.aop.dingtalk.config;

import java.util.concurrent.locks.Lock;

/**
 * 钉钉配置存储
 * @author wsz
 * @date 2020/8/26  9:32
 */
public interface DingTalkConfigStorage {

    /** 基础配置 **/
    String getCorpId();

    Integer getAgentId();

    String getAppKey();

    String getAppSecret();

    long getExpiresTime();

    /**
     * 设置钉钉服务器baseApiUrl
     * 默认值是 https://oapi.dingtalk.com
     * @param baseApiUrl
     */
    void setBaseApiUrl(String baseApiUrl);

    /**
     * 读取钉钉API Url
     * @param path
     * @return
     */
    String getApiUrl(String path);

    /** accessToken相关 **/

    String getAccessToken();

    Lock getAccessTokenLock();

    boolean isAccessTokenExpired();

    void expireAccessToken();

    void updateAccessToken(String accessToken, int expiresIn);

    /** JsapiTicket相关 **/
    String getJsapiTicket();

    Lock getJsapiTicketLock();

    boolean isJsapiTicketExpired();

    void expireJsapiTicket();

    void updateJsapiTicket(String jsapiTicket, int expiresInSeconds);

    /**
     * 请求失败时：是否自动刷新
     * @return
     */
    boolean autoRefreshToken();
}
