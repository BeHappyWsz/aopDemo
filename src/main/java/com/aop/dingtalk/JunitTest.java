package com.aop.dingtalk;

import com.alibaba.fastjson.JSONObject;
import com.aop.dingtalk.api.impl.BaseDingTalkServiceImpl;
import com.aop.dingtalk.bean.*;
import com.aop.dingtalk.config.DingTalkDefaultConfigImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wsz
 * @date 2020/8/26  14:15
 */
public class JunitTest {
    BaseDingTalkServiceImpl talkService;

    @Before
    public void init() {
        talkService = new BaseDingTalkServiceImpl();
        DingTalkDefaultConfigImpl storage = new DingTalkDefaultConfigImpl();
        storage.setAgentId(862684800);
        storage.setAppKey("dingvqyfzy9klyhir8v7");
        storage.setAppSecret("Mvvtl0IinDhwNM1s_mHatoG5fcj7mNj_BSq5yhkfHJa8k5_ywL8n-CeUzIVAX_rW");
        talkService.setDingTalkConfigStorage(storage);
    }

    @Test
    public void getToken() throws Exception {
        String token = talkService.getAccessToken();
        System.out.println(token);
    }

    @Test
    public void getJsapiTicket () throws Exception {
        String jsapiTicket = talkService.getJsapiTicket();
        System.out.println(jsapiTicket);
    }

    @Test
    public void getJsapiSignature () throws Exception {
        DtJsapiSignature jsapiSignature = talkService.createJsapiSignature("localhost:8080/");

        System.out.println(jsapiSignature);
    }

    @Test
    public void getUserInfo () throws Exception {
        DtOuath2UserInfo userInfo = talkService.getOuath2Service().getUserInfo("aaasd");
        System.out.println(userInfo);
    }

    @Test
    public void getById () throws Exception {
        DtUser user = talkService.getUserService().getById("manager4261");
        System.out.println(user);
    }

    @Test
    public void createChat () throws Exception {
        String chatid = talkService.getChatService().createChat(
                "ttt",
                "manager4261",
                new String[]{"manager4261"});
        // chat0892c750830905f6bbb18baaf6bef8c7
        System.out.println(chatid);
    }

    @Test
    public void getChat () throws Exception {
        // chat0892c750830905f6bbb18baaf6bef8c7
        DtChat chat = talkService.getChatService().getChat("chat0892c750830905f6bbb18baaf6bef8c7");
        System.out.println(chat);
    }

    @Test
    public void updateChat () throws Exception {
        // chat0892c750830905f6bbb18baaf6bef8c7
        talkService.getChatService().updateChat("chat0892c750830905f6bbb18baaf6bef8c7", new String[]{"manager4261"});
    }

    @Test
    public void sendMessage () throws Exception {
        // chat0892c750830905f6bbb18baaf6bef8c7

        JSONObject text = DtMessage.TEXT().content("hahah111").build();

        talkService.getChatService().sendMessage("chat0892c750830905f6bbb18baaf6bef8c7", text);
    }

    @Test
    public void sendImage () throws Exception {
        // chat0892c750830905f6bbb18baaf6bef8c7
        JSONObject image = DtMessage.IMAGE().media_id("hahah111").build();
        talkService.getChatService().sendMessage("chat0892c750830905f6bbb18baaf6bef8c7", image);
    }
}
