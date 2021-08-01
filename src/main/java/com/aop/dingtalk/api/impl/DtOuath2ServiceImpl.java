package com.aop.dingtalk.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.aop.dingtalk.api.DingTalkService;
import com.aop.dingtalk.api.DtOuath2Service;
import com.aop.dingtalk.bean.DtOuath2UserInfo;
import com.aop.dingtalk.common.DtErrorException;
import lombok.RequiredArgsConstructor;

import static com.aop.dingtalk.common.DingTalkApiPathConsts.OAuth2.GET_USER_INFO;

/**
 * @author wsz
 * @date 2020/8/26  16:40
 */
@RequiredArgsConstructor
public class DtOuath2ServiceImpl implements DtOuath2Service {

    private final DingTalkService mainService;

    @Override
    public DtOuath2UserInfo getUserInfo(String code) throws DtErrorException {
        String url = String.format(
                this.mainService.getDingTalkConfigStorage().getApiUrl(GET_USER_INFO),
                code
        );
        JSONObject jsonObject = this.mainService.get(url, null);

        return DtOuath2UserInfo.initUserInfo(jsonObject);
    }
}
