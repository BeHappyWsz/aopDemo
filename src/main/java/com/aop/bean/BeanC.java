package com.aop.bean;

/**
 * @author wsz
 * @date 2020/8/19  17:21
 */
public class BeanC {

    private BeanC () {
        System.out.println("BeanC constructor");
    }

    private static BeanC beanC;

    public static String name;

    public static BeanC getInstance() {
        name = "BeanC";
        if (beanC == null) {
            beanC = new BeanC();
        }
        return beanC;
    }
}
