package com.aop.dingtalk.api;

import com.alibaba.fastjson.JSONObject;
import com.aop.dingtalk.bean.DtJsapiSignature;
import com.aop.dingtalk.common.DtErrorException;
import com.aop.dingtalk.config.DingTalkConfigStorage;

/**
 * 钉钉API Service
 * @author wsz
 * @date 2020/8/26  10:09
 */
public interface DingTalkService {

    boolean checkSignature(String msgSignature, String timestamp, String nonce, String data);

    /** AccessToken **/
    String getAccessToken() throws DtErrorException;

    String getAccessToken(boolean forceRefresh) throws Exception;

    /** JsapiTicket **/
    DtJsapiSignature createJsapiSignature(String url) throws Exception;

    String getJsapiTicket() throws Exception;

    String getJsapiTicket(boolean forceRefresh) throws Exception;

    /** 基础配置 **/

    DingTalkConfigStorage getDingTalkConfigStorage();

    void setDingTalkConfigStorage(DingTalkConfigStorage dingTalkConfigStorage);

    /** 请求 **/

    JSONObject get(String url, String queryParam) throws DtErrorException;

    JSONObject post(String url, String queryParam) throws DtErrorException;

    /** 相关方法 **/
    DtOuath2Service getOuath2Service ();
    DtUserService getUserService ();
    DtChatService getChatService();
}
