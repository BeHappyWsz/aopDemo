package com.aop;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AopApplication.class, args);
//        int exit = SpringApplication.exit(run);
//        System.out.println(exit);
    }

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> -100;
    }
}
