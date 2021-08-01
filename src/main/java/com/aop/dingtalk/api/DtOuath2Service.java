package com.aop.dingtalk.api;

import com.aop.dingtalk.bean.DtOuath2UserInfo;
import com.aop.dingtalk.common.DtErrorException;

/**
 * @author wsz
 * @date 2020/8/26  16:37
 */
public interface DtOuath2Service {

    DtOuath2UserInfo getUserInfo (String code) throws DtErrorException;

}
