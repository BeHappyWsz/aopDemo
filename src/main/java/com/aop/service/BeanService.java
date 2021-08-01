package com.aop.service;

import com.aop.bean.BeanA;
import com.aop.bean.BeanB;
import com.aop.bean.BeanC;
import com.aop.bean.BeanD;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 同为@Configuration类，都可用@ConditionalOnBean({***.class})/@ConditionalOnClass({***.class})。
 * 非同为@Configuration类，使用@ConditionalOnClass({***.class})；
 *  不过根据优先级，IOC优先解析@Component/@Service/@Controller，最后解析@Configuration，真正的初始化顺序不一致。
 *  全限定类+type可以@ConditionalOnBean(type = {"com.aop.bean.BeanB"})
 *  全限定类+name无效@ConditionalOnBean(name = {"com.aop.bean.BeanB"})/@ConditionalOnBean(name = {"beanB"})
 * @author wsz
 * @date 2020/8/19  16:12
 */
@Configuration
public class BeanService {

    @Bean
    public BeanA initBeanA () {
        return BeanA.getInstance();
    }

    @Bean
    @ConditionalOnBean({BeanA.class})
    public BeanB initBeanB() {
        return BeanB.getInstance();
    }

    @Bean
    @ConditionalOnClass({BeanC.class})
    public BeanD initBeanD () {
        System.out.println("initBeanD-" + BeanC.name);
        BeanD instance = BeanD.getInstance();
        return instance;
    }
}
