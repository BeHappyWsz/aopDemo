package com.aop.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author wsz
 * @date 2020/8/24  14:27
 */
@Data
@Configuration
@ConfigurationProperties(value = "config")
public class Config {

     Integer id;

     String name;

     List<String> likes;

     Map keys;
}
