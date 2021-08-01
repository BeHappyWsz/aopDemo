package com.aop.dingtalk.api;

import com.aop.dingtalk.bean.DtUser;
import com.aop.dingtalk.common.DtErrorException;

/**
 * @author wsz
 * @date 2020/8/26  16:35
 */
public interface DtUserService {

    DtUser getById(String userid) throws DtErrorException;
}
