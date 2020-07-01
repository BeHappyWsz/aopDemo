package com.aop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description :
 * @Author : wsz
 * @Date: 2020-06-15 11:28
 */
@Service
public class AsyncService {

    @Value("key")
    private String key;

    @Async
    public String getAsync(long millis) throws ExecutionException, InterruptedException {
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
}
