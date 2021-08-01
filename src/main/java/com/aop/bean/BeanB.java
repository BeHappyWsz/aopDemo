package com.aop.bean;

/**
 * @author wsz
 * @date 2020/8/19  15:12
 */
public class BeanB {

    private BeanB () {
        System.out.println("BeanB constructor");
    }

    private final static BeanB beanB = new BeanB();

    public static BeanB getInstance() {
        return beanB;
    }
}
