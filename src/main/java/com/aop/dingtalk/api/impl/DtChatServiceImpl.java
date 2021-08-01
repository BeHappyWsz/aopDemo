package com.aop.dingtalk.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.aop.dingtalk.api.DingTalkService;
import com.aop.dingtalk.api.DtChatService;
import com.aop.dingtalk.bean.DtChat;
import com.aop.dingtalk.common.DtErrorException;
import lombok.RequiredArgsConstructor;

import static com.aop.dingtalk.common.DingTalkApiPathConsts.Chat.*;

/**
 * 群消息管理
 * @author wsz
 * @date 2020/8/26  19:29
 */
@RequiredArgsConstructor
public class DtChatServiceImpl implements DtChatService {

    private final DingTalkService mainService;

    @Override
    public String createChat(DtChat chat) throws DtErrorException {
        return doCreateChat(DtChat.toJSON(chat));
    }

    @Override
    public String createChat(String name, String owner, String[] useridList) throws DtErrorException {
        return createChat(name, owner, useridList, 1, 1);
    }

    @Override
    public String createChat(String name, String owner, String[] useridList, Integer showHistoryType) throws DtErrorException {
        return createChat(name, owner, useridList, showHistoryType < 0 ? 0 : 1, 1);
    }

    @Override
    public String createChat(String name, String owner, String[] useridList, Integer showHistoryType, Integer searchable) throws DtErrorException {
        JSONObject postData = new JSONObject();
        postData.put("name", name);
        postData.put("owner", owner);
        postData.put("useridlist", useridList);
        postData.put("showHistoryType", showHistoryType < 0 ? 0 : 1);
        postData.put("searchable", searchable < 0 ? 0 : 1);

        return doCreateChat(postData);
    }

    private String doCreateChat (JSONObject postData) throws DtErrorException {
        String url = this.mainService.getDingTalkConfigStorage().getApiUrl(CHAT_CREATE);
        JSONObject result = this.mainService.post(url, postData.toString());
        return result.getString("chatid");
    }

    @Override
    public DtChat getChat(String chatid) throws DtErrorException {
        String url = this.mainService.getDingTalkConfigStorage().getApiUrl(CHAT_GET + chatid);
        JSONObject result = this.mainService.get(url, null);
        return DtChat.initChat(result);
    }

    @Override
    public void updateChat(String chatid, String[] add_useridlist) throws DtErrorException {
        updateChat(chatid, add_useridlist);
    }

    @Override
    public void updateChat(String chatid, String[] add_useridlist, String[] del_useridlist) throws DtErrorException {
        String url = this.mainService.getDingTalkConfigStorage().getApiUrl(CHAT_UPDATE);

        JSONObject postData = new JSONObject();

        postData.put("chatid", chatid);

        if (add_useridlist != null && add_useridlist.length > 0) {
            postData.put("add_useridlist", add_useridlist);
        }

        if (del_useridlist != null && del_useridlist.length > 0) {
            postData.put("del_useridlist", del_useridlist);
        }

        this.mainService.post(url, postData.toString());
    }

    @Override
    public String sendMessage(String chatid, JSONObject message) throws DtErrorException {
        String url = this.mainService.getDingTalkConfigStorage().getApiUrl(CHAT_SEND);

        JSONObject postData = new JSONObject();
        postData.put("chatid", chatid);
        postData.put("msg", message);

        JSONObject post = this.mainService.post(url, postData.toString());
        return post.getString("messageId");
    }


}
