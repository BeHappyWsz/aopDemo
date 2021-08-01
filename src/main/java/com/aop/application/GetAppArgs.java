package com.aop.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * 获取应用的启动参数
 * @author wsz
 * @date 2020/8/21  17:08
 */
@Component
public class GetAppArgs {

    @Autowired
    public GetAppArgs (ApplicationArguments args) {
        System.out.println(args.getOptionNames());
    }
}
