package com.aop.dingtalk.common;

import java.util.Random;

/**
 * @author wsz
 * @date 2020/8/27  10:51
 */
public class RandomUtils {
    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new Random();

    public RandomUtils() {
    }

    public static String getRandomStr() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 16; ++i) {
            sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(RANDOM.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length())));
        }

        return sb.toString();
    }
}
