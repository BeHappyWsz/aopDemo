package com.aop.dingtalk.config;

import com.aop.dingtalk.common.DingTalkApiPathConsts;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wsz
 * @date 2020/8/26  9:55
 */
public class DingTalkDefaultConfigImpl implements DingTalkConfigStorage, Serializable {

    private volatile String corpId;

    private volatile Integer agentId;
    private volatile String appKey;
    private volatile String appSecret;

    protected Lock accessTokenLock = new ReentrantLock();
    protected volatile String accessToken;
    private volatile long expiresTime;

    protected Lock jsapiTicketLock = new ReentrantLock();
    private volatile String jsapiTicket;
    private volatile long jsapiTicketExpiresTime;

    private volatile String baseApiUrl;

    @Override
    public void setBaseApiUrl(String baseApiUrl) {
        this.baseApiUrl = baseApiUrl;
    }

    @Override
    public String getApiUrl(String path) {
        if (baseApiUrl == null) {
            baseApiUrl = DingTalkApiPathConsts.DEFAULT_DD_BASE_URL;
        }
        return baseApiUrl + path;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > this.expiresTime;
    }

    @Override
    public void expireAccessToken() {
        this.expiresTime = 0;
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        this.accessToken = accessToken;
        this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public String getJsapiTicket() {
        return this.jsapiTicket;
    }

    @Override
    public Lock getJsapiTicketLock() {
        return this.jsapiTicketLock;
    }

    @Override
    public boolean isJsapiTicketExpired() {
        return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
    }

    @Override
    public void expireJsapiTicket() {
        this.jsapiTicketExpiresTime = 0;
    }

    @Override
    public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        this.jsapiTicket = jsapiTicket;
        // 预留200秒的时间
        this.jsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    @Override
    public String getCorpId() {
        return this.corpId;
    }

    public void setCorpId (String corpId) {
        this.corpId = corpId;
    }

    @Override
    public Integer getAgentId() {
        return this.agentId;
    }

    public void setAgentId (Integer agentId) {
        this.agentId = agentId;
    }

    @Override
    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey (String appKey) {
        this.appKey = appKey;
    }

    @Override
    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret (String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public long getExpiresTime() {
        return this.expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }
}
