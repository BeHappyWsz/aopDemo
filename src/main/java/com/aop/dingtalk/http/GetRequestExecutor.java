package com.aop.dingtalk.http;

import com.alibaba.fastjson.JSONObject;
import com.aop.dingtalk.common.DtErrorException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * @author wsz
 * @date 2020/8/26  13:49
 */
public class GetRequestExecutor implements RequestExecutor<JSONObject, String> {

    @Override
    public JSONObject execute(String uri, String queryParam) throws IOException, DtErrorException {
        if (queryParam != null) {
            if (uri.indexOf(63) == -1) {
                uri = uri + '?';
            }

            uri = uri + (uri.endsWith("?") ? queryParam : '&' + queryParam);
        }
        HttpGet httpGet = new HttpGet(uri);

        JSONObject var8;
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            CloseableHttpResponse response = client.execute(httpGet);
            Throwable var6 = null;

            try {
                String responseContent = new BasicResponseHandler().handleResponse(response);

                var8 = JSONObject.parseObject(responseContent);
                if (var8.getInteger("errcode") != 0) {
                    throw new DtErrorException(var8);
                }
            } catch (Throwable var24) {
                var6 = var24;
                throw var24;
            } finally {
                if (response != null) {
                    if (var6 != null) {
                        try {
                            response.close();
                        } catch (Throwable var23) {
                            var6.addSuppressed(var23);
                        }
                    } else {
                        response.close();
                    }
                }
            }
        } finally {
            httpGet.releaseConnection();
        }

        return var8;
    }

}
