package com.aop.web;

import com.aop.handler.ReqLog;
import com.aop.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @Description :
 * @Author : wsz
 * @Date: 2020-06-15 11:27
 */
@RestController
@RequestMapping("async")
public class AsyncWeb {

    @Autowired
    private AsyncService asyncService;

    @ReqLog()
    @GetMapping(value = "get")
    public String getAsync(long millis) throws ExecutionException, InterruptedException {
        return asyncService.getAsync(millis);
    }
}
