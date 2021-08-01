package com.aop.dingtalk.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.aop.dingtalk.api.DingTalkService;
import com.aop.dingtalk.api.DtUserService;
import com.aop.dingtalk.bean.DtUser;
import com.aop.dingtalk.common.DtErrorException;
import lombok.RequiredArgsConstructor;

import static com.aop.dingtalk.common.DingTalkApiPathConsts.User.USER_GET;

/**
 * @author wsz
 * @date 2020/8/26  17:14
 */
@RequiredArgsConstructor
public class DtUserServiceImpl implements DtUserService {

    private final DingTalkService mainService;

    @Override
    public DtUser getById(String userid) throws DtErrorException {
        String url = this.mainService.getDingTalkConfigStorage().getApiUrl(USER_GET + userid);
        JSONObject jsonObject = this.mainService.get(url, null);

        return DtUser.initUser(jsonObject);
    }
}
