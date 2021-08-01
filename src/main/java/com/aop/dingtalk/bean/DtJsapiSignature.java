package com.aop.dingtalk.bean;

import java.io.Serializable;

/**
 * @author wsz
 * @date 2020/8/26  10:18
 */
public class DtJsapiSignature implements Serializable {

    private String corpId;
    private String agentId;
    private String nonceStr;
    private long timestamp;
    private String url;
    private String signature;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "DtJsapiSignature{" +
                "corpId='" + corpId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", timestamp=" + timestamp +
                ", url='" + url + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
