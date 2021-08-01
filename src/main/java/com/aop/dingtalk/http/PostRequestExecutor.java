package com.aop.dingtalk.http;

import com.alibaba.fastjson.JSONObject;
import com.aop.dingtalk.common.DtErrorException;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * @author wsz
 * @date 2020/8/26  14:05
 */
public class PostRequestExecutor implements RequestExecutor<JSONObject, String> {

    @Override
    public JSONObject execute(String uri, String postEntity) throws DtErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);

        if (postEntity != null) {
            StringEntity entity = new StringEntity(postEntity, Consts.UTF_8);
            entity.setContentType("application/json; charset=utf-8");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("charset", "utf-8");
            httpPost.setEntity(entity);
        }

        JSONObject var8;

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            CloseableHttpResponse response = client.execute(httpPost);
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
            httpPost.releaseConnection();
        }

        return var8;
    }
}
