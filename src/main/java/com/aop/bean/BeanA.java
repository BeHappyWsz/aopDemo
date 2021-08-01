package com.aop.bean;

/**
 * @author wsz
 * @date 2020/8/19  15:11
 */

public class BeanA {

    private BeanA () {
        System.out.println("BeanA constructor");
    }

    private final static BeanA beanA = new BeanA();

    public static BeanA getInstance() {
        return beanA;
    }
}
