package com.aop.bean;

/**
 * @author wsz
 * @date 2020/8/20  15:22
 */
public class BeanD {
    private BeanD () {
        System.out.println("BeanD constructor");
    }

    private static BeanD beanD;

    public static String name;

    public static BeanD getInstance() {
        name = "BeanD";
        if (beanD == null) {
            beanD = new BeanD();
        }
        return beanD;
    }
}
