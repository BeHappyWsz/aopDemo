package com.aop.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * SpringApplication启动后立马执行
 * 1.CommandLineRunner
 * 2.ApplicatioRunner
 * @author wsz
 * @date 2020/8/24  13:45
 */
@Component
public class DoOnceStart implements CommandLineRunner {

    @Override
    public void run(String... args) {
        Arrays.asList(args).forEach(arg -> System.out.println(arg));
    }
}
