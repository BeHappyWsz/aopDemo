package com.aop.service;

import com.aop.configuration.Config;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description :
 * @Author : wsz
 * @Date: 2020-06-15 11:28
 */
@Service
public class AsyncService {

    @Autowired
    Config config;

    @Autowired
    RestTemplate restTemplate;

    @Value("${restUrl}")
    private String key;


    @Async
    public String getAsync(long millis) throws ExecutionException, InterruptedException {
        System.out.println(config.getName());
        System.out.println(key);
        Future<String> future = doAsync(millis);
        System.out.println(future.get());
        return "aaa";
    }

    @Async
    public Future<String> doAsync(long millis) {
        try {
            Thread.sleep(millis);
            return new AsyncResult<>("aaaaa");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 会优先被加载
     * @return
     */
//    @Bean
//    @ConditionalOnClass({BeanB.class})
//    public BeanC initBeanC() {
//        return BeanC.getInstance();
//    }
}
