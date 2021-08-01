package com.aop.service;

import com.aop.bean.BeanB;
import com.aop.bean.BeanC;
import com.aop.bean.BeanD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author wsz
 * @date 2020/8/20  9:52
 */
@Service
public class BeanServiceA {

    @Autowired
    AsyncService asyncService;

    @Bean
//    @ConditionalOnBean(name = {"com.aop.bean.BeanB"})
    @ConditionalOnClass({BeanB.class})
    public BeanC initBeanC() {
        System.out.println("initBeanC-" + BeanD.name);
        BeanC instance = BeanC.getInstance();
        return instance;
    }

}
