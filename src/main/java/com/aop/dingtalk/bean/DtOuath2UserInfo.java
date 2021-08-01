package com.aop.dingtalk.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wsz
 * @date 2020/8/26  16:38
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtOuath2UserInfo {

    private String userid;

    private int sys_level;

    private boolean is_sys;

    public static DtOuath2UserInfo initUserInfo (JSONObject jsonObject) {
        return DtOuath2UserInfo.builder()
                .userid(jsonObject.getString("userid"))
                .is_sys(jsonObject.getBooleanValue("is_sys"))
                .sys_level(jsonObject.getIntValue("sys_level"))
                .build();
    }
}
