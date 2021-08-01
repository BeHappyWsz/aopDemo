package com.aop.dingtalk.http;

import com.aop.dingtalk.common.DtErrorException;

import java.io.IOException;

/**
 * @author wsz
 * @date 2020/8/26  13:40
 */
public interface RequestExecutor<T, E> {

    T execute(String var1, E var2) throws DtErrorException, IOException;

}
