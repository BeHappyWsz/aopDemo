package com.aop.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

/**
 * 直接继承
 */
@Service
public class RestTemplateService extends RestTemplate {

    @Value("${restUrl}")
    private String restUrl;

    public String getRestURL () {
        if (!restUrl.endsWith("/")) {
            restUrl+= "/";
        }
        return restUrl;
    }

    public RestTemplateService () {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(getClient());

        setRequestFactory(factory);
    }

    private HttpClient getClient () {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(1000);
        manager.setDefaultMaxPerRoute(500);

       return HttpClientBuilder.create()
                .setConnectionManager(manager)
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectionRequestTimeout(1000)
                                .setConnectTimeout(1000)
                                .setSocketTimeout(1000)
                                .build()
                )
                // 请求连接失败
                .setRetryHandler((exception, executionCount, context) -> {
                    if (exception instanceof SocketTimeoutException) {
                        return executionCount < 10;
                    }
                    return executionCount < 5;
                })
                // 根据返回结果重试
                .setServiceUnavailableRetryStrategy(new ServiceUnavailableRetryStrategy() {

                    /**
                     * 结果失败：尝试10次
                     * @param response
                     * @param executionCount
                     * @param context
                     * @return
                     */
                    @Override
                    public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
                        return response.getStatusLine().getStatusCode() != 200 && executionCount < 10;
                    }

                    /**
                     * 每次请求间隔
                     * @return
                     */
                    @Override
                    public long getRetryInterval() {
                        return 3000;
                    }
                })
                .build();
    }
}
