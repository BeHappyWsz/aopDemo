package com.aop.dingtalk.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.aop.dingtalk.api.DingTalkService;
import com.aop.dingtalk.api.DtChatService;
import com.aop.dingtalk.api.DtOuath2Service;
import com.aop.dingtalk.api.DtUserService;
import com.aop.dingtalk.bean.DtJsapiSignature;
import com.aop.dingtalk.common.DingTalkApiPathConsts;
import com.aop.dingtalk.common.DtErrorException;
import com.aop.dingtalk.common.RandomUtils;
import com.aop.dingtalk.config.DingTalkConfigStorage;
import com.aop.dingtalk.http.GetRequestExecutor;
import com.aop.dingtalk.http.PostRequestExecutor;
import com.aop.dingtalk.http.RequestExecutor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.concurrent.locks.Lock;

/**
 * @author wsz
 * @date 2020/8/26  10:25
 */
@Slf4j
public class BaseDingTalkServiceImpl implements DingTalkService {

    private DtOuath2Service ouath2Service = new DtOuath2ServiceImpl(this);
    private DtUserService userService = new DtUserServiceImpl(this);
    private DtChatService chatService = new DtChatServiceImpl(this);

    /**
     * 全局的是否正在刷新access token的锁.
     */
    protected final Object globalAccessTokenRefreshLock = new Object();

    /**
     * 全局的是否正在刷新jsapi_ticket的锁.
     */
    protected final Object globalJsapiTicketRefreshLock = new Object();

    protected DingTalkConfigStorage configStorage;

    /**
     * 临时文件目录.
     */
    private File tmpDirFile;
    private int retrySleepMillis = 1000;
    private int maxRetryTimes = 5;

    @Override
    public boolean checkSignature(String msgSignature, String timestamp, String nonce, String data) {
        return false;
    }

    @Override
    public String getAccessToken() throws DtErrorException {
        return getAccessToken(false);
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws DtErrorException {
        if (!getDingTalkConfigStorage().isAccessTokenExpired() && !forceRefresh) {
            return getDingTalkConfigStorage().getAccessToken();
        }
        Lock lock = getDingTalkConfigStorage().getAccessTokenLock();
        lock.lock();
        try {
            // 重复判断
            if (!getDingTalkConfigStorage().isAccessTokenExpired() && !forceRefresh) {
                return getDingTalkConfigStorage().getAccessToken();
            }
            String url = String.format(
                getDingTalkConfigStorage().getApiUrl(DingTalkApiPathConsts.GET_TOKEN),
                this.configStorage.getAppKey(),
                this.configStorage.getAppSecret()
            );
            try {
                GetRequestExecutor executor = new GetRequestExecutor();
                JSONObject jsonObject = executor.execute(url, null);
                // 结果取值
                if (jsonObject.getInteger("errcode") != 0) {
                    throw new DtErrorException(jsonObject);
                }
                getDingTalkConfigStorage().updateAccessToken(
                    jsonObject.getString("access_token"),
                    jsonObject.getInteger("expires_in")
                );
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        } finally {
            lock.unlock();
        }
        return getDingTalkConfigStorage().getAccessToken();
    }

    @Override
    public DtJsapiSignature createJsapiSignature(String url) throws Exception {
        long timestamp = System.currentTimeMillis() / 1000;
        String noncestr = RandomUtils.getRandomStr();
        String jsapiTicket = getJsapiTicket(false);
        String signature =
                "jsapi_ticket=" + jsapiTicket +
                "noncestr=" + noncestr +
                "timestamp=" + timestamp +
                "url=" + url;

        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.reset();
        sha1.update(signature.getBytes("UTF-8"));
        String toHex = byteToHex(sha1.digest());

        DtJsapiSignature jsapiSignature = new DtJsapiSignature();

        jsapiSignature.setAgentId(String.valueOf(this.configStorage.getAgentId()));
        jsapiSignature.setCorpId(this.configStorage.getCorpId());

        jsapiSignature.setTimestamp(timestamp);
        jsapiSignature.setNonceStr(noncestr);
        jsapiSignature.setSignature(toHex);

        return jsapiSignature;
    }

    // 字节数组转化成十六进制字符串
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    @Override
    public String getJsapiTicket() throws Exception {
        return getJsapiTicket(false);
    }

    @Override
    public String getJsapiTicket(boolean forceRefresh) throws Exception {
        if (forceRefresh) {
            getDingTalkConfigStorage().expireJsapiTicket();
        }
        if (getDingTalkConfigStorage().isJsapiTicketExpired()) {
            Lock lock = getDingTalkConfigStorage().getJsapiTicketLock();
            lock.lock();
            try {
                // 二次判断避免重刷
                if (getDingTalkConfigStorage().isJsapiTicketExpired()) {
                    synchronized (this.globalJsapiTicketRefreshLock) {
                        JSONObject jsonObject = this.get(
                            this.configStorage.getApiUrl(DingTalkApiPathConsts.GET_JSAPI_TICKET),
                            null
                        );
                        this.configStorage.updateJsapiTicket(jsonObject.getString("ticket"), jsonObject.getInteger("expires_in"));
                    }
                }
            } finally {
                lock.unlock();
            }
        }
        return getDingTalkConfigStorage().getJsapiTicket();
    }

    @Override
    public DingTalkConfigStorage getDingTalkConfigStorage() {
        return this.configStorage;
    }

    @Override
    public void setDingTalkConfigStorage(DingTalkConfigStorage dingTalkConfigStorage) {
        this.configStorage = dingTalkConfigStorage;
    }

    @Override
    public JSONObject get(String url, String queryParam) throws DtErrorException {
        return execute(new GetRequestExecutor(), url, queryParam);
    }

    @Override
    public JSONObject post(String url, String queryParam) throws DtErrorException {
        return execute(new PostRequestExecutor(), url, queryParam);
    }

    @Override
    public DtOuath2Service getOuath2Service() {
        return this.ouath2Service;
    }

    @Override
    public DtUserService getUserService() {
        return this.userService;
    }

    @Override
    public DtChatService getChatService() {
        return this.chatService;
    }

    private <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws DtErrorException {
        int retryTimes = 0;
        do {
            try {
                return this.executeInternal(executor, uri, data);
            } catch (DtErrorException e) {
                if (retryTimes + 1 > this.maxRetryTimes) {
                    log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
                    throw new RuntimeException("钉钉服务端异常，超出重试次数");
                }

                JSONObject error = e.getError();
                Integer errcode = error.getInteger("errcode");
                // 系统繁忙
                if (errcode == -1) {
                    int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
                    try {
                        log.debug("钉钉系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }
        } while (retryTimes++ < this.maxRetryTimes);

        log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
        throw new RuntimeException("微信服务端异常，超出重试次数");
    }

    private <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data) throws DtErrorException {

        if (uri.contains("access_token=")) {
            throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
        }

        String accessToken = getAccessToken(false);

        String uriWithAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token=" + accessToken;

        try {
            T result = executor.execute(uriWithAccessToken, data);
            log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uriWithAccessToken, data, result);
            return result;
        } catch (DtErrorException e) {
            JSONObject error = e.getError();
            Integer errcode = error.getInteger("errcode");
            /**
             * 刷新access_token
             * 40001 获取access_token时Secret错误，或者access_token无效
             * 42001 access_token超时
             * 40014 不合法的access_token
             */
            if (errcode == 40001 || errcode == 42001 || errcode == 40014) {
                this.configStorage.expireAccessToken();
                if (this.getDingTalkConfigStorage().autoRefreshToken()) {
                    return this.execute(executor, uri, data);
                }
            }

            if (errcode != 0) {
                log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uriWithAccessToken, data, error);
                throw new DtErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uriWithAccessToken, data, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
