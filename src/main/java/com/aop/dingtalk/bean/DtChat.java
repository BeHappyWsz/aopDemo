package com.aop.dingtalk.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 群消息
 * @author wsz
 * @date 2020/8/26  19:18
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtChat {

    private String chatid;

    private String name;

    private String owner;

    private String[] useridlist;
    // 是否可以搜索群名 0 不可以 1可以搜索
    private Integer searchable = 0;
    // 入群需群主或群管理员同意 0不需要 1需要
    private Integer validationType = 0;
    // 仅群主和管理员可@所有人 0 否 1 是
    private Integer mentionAllAuthority = 0;
    // 仅群主和群管理员可管理 0否 1 是
    private Integer managementType = 0;
    // 新成员可查看聊天历史 0否 1是
    private Integer showHistoryType = 1;

    public static DtChat initChat(JSONObject result) {
        JSONObject data = (JSONObject) result.get("chat_info");

        JSONArray objects = data.getJSONArray("useridlist");
        String[] useridlist = new String[objects.size()];
        int i = 0;
        for (Object obj : objects) {
            useridlist[i++] = (String) obj;
        }

        return DtChat.builder()
                .chatid(data.getString("chatid"))
                .name(data.getString("name"))
                .owner(data.getString("owner"))
                .useridlist(useridlist)
                .searchable(data.getInteger("searchable"))
                .validationType(data.getInteger("validationType"))
                .mentionAllAuthority(data.getInteger("mentionAllAuthority"))
                .showHistoryType(data.getInteger("showHistoryType"))
                .build();
    }

    public static JSONObject toJSON (DtChat chat) {
        JSONObject obj = new JSONObject();

        if (chat.getChatid() != null) obj.put("chatid", chat.getChatid());
        if (chat.getName() != null) obj.put("name", chat.getName());
        if (chat.getOwner() != null) obj.put("owner", chat.getOwner());
        if (chat.getUseridlist() != null && chat.getUseridlist().length > 0) obj.put("useridlist", chat.getUseridlist());
        if (chat.getSearchable() != null) obj.put("searchable", chat.getOwner());
        if (chat.getValidationType() != null) obj.put("validationType", chat.getValidationType());
        if (chat.getMentionAllAuthority() != null) obj.put("mentionAllAuthority", chat.getMentionAllAuthority());
        if (chat.getManagementType() != null) obj.put("managementType", chat.getManagementType());
        if (chat.getShowHistoryType() != null) obj.put("showHistoryType", chat.getShowHistoryType());

        return obj;
    }
}
