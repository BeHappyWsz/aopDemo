package com.aop.dingtalk.api;

import com.alibaba.fastjson.JSONObject;
import com.aop.dingtalk.bean.DtChat;
import com.aop.dingtalk.common.DtErrorException;

/**
 * 群消息管理
 * @author wsz
 * @date 2020/8/26  19:21
 */
public interface DtChatService {

    /**
     * 创建群组
     * @param chat
     * @return
     * @throws DtErrorException
     */
    String createChat (DtChat chat) throws DtErrorException;

    /**
     * 创建群组
     * @param name 群名称，长度限制为1~20个字符
     * @param owner 群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一
     * @param useridList 群成员列表，每次最多支持40人，群人数上限为1000
     * @return String 群会话的id
     */
    String createChat(String name, String owner, String[] useridList) throws DtErrorException;

    /**
     * 创建群组
     * @param name 群名称，长度限制为1~20个字符
     * @param owner 群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一
     * @param useridList 群成员列表，每次最多支持40人，群人数上限为1000
     * @param showHistoryType 新成员是否可查看聊天历史消息（新成员入群是否可查看最近100条聊天记录）， 0代表否；1代表是；不传默认为否
     * @return String 群会话的id
     */
    String createChat(String name, String owner, String[] useridList, Integer showHistoryType) throws DtErrorException;

    /**
     * 创建群组
     * @param name 群名称，长度限制为1~20个字符
     * @param owner 群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一
     * @param useridList 群成员列表，每次最多支持40人，群人数上限为1000
     * @param showHistoryType 新成员是否可查看聊天历史消息（新成员入群是否可查看最近100条聊天记录）， 0代表否；1代表是；不传默认为否
     * @param searchable 群可搜索，0-默认，不可搜索，1-可搜索
     * @return String 群会话的id
     */
    String createChat(String name, String owner, String[] useridList, Integer showHistoryType, Integer searchable) throws DtErrorException;

    /**
     * 获取会话
     * @param chatid 群会话的id。该chatid仅支持通过调用服务端“创建会话”接口获取的chatid，不支持通过调用前端JSAPI获取的chatid。
     * @return
     * @throws DtErrorException
     */
    DtChat getChat(String chatid) throws DtErrorException;

    /**
     * 更新群会话
     * @param chatid
     * @param add_useridlist 添加成员列表，每次最多支持40人，群人数上限为1000
     * @throws DtErrorException
     */
    void updateChat (String chatid, String[] add_useridlist) throws DtErrorException;

    /**
     * 更新群会话
     * @param chatid
     * @param add_useridlist 添加成员列表，每次最多支持40人，群人数上限为1000
     * @param del_useridlist 删除成员列表，每次最多支持40人，群人数上限为1000
     * @throws DtErrorException
     */
    void updateChat (String chatid, String[] add_useridlist, String[] del_useridlist) throws DtErrorException;

    /**
     * 发送群会话消息
     * @param chatid
     * @param message
     * @return 加密的消息id
     * @throws DtErrorException
     */
    String sendMessage (String chatid, JSONObject message) throws DtErrorException;
}
