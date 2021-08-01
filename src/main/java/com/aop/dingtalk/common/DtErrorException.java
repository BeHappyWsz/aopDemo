package com.aop.dingtalk.common;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wsz
 * @date 2020/8/26  14:49
 */
public class DtErrorException extends Exception {

    private JSONObject error;

    public DtErrorException (JSONObject error) {
        this.error = error;
    }

    public DtErrorException(JSONObject error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public JSONObject getError() {
        return this.error;
    }
}
