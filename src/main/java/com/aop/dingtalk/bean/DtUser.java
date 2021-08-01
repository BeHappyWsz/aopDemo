package com.aop.dingtalk.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author wsz
 * @date 2020/8/26  17:11
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtUser {
    private String unionid;

    private String userid;

    private String mobile;

    private JSONObject orderInDepts;

    private Integer[] department;

    private String jobnumber;

    private List<Map> roles = Lists.newArrayList();

    public static DtUser initUser (JSONObject data) {
        // department
        JSONArray objects = data.getJSONArray("department");
        Integer[] department = new Integer[objects.size()];
        int i =0;
        for (Object dep: objects) {
            department[i++] = (Integer) dep;
        }

        // roles
        JSONArray roleArr = data.getJSONArray("roles");
        List<Map> roles = Lists.newArrayList();
        for (Object role: roleArr) {
            roles.add((Map) role);
        }

        return DtUser.builder()
                .unionid(data.getString("unionid"))
                .userid(data.getString("userid"))
                .mobile(data.getString("mobile"))
                .orderInDepts(JSONObject.parseObject(data.getString("orderInDepts")))
                .department(department)
                .jobnumber(data.getString("jobnumber"))
                .roles(roles)
                .build();
    }
}
