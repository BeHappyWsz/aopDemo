package com.aop.dingtalk.common;

/**
 * @author wsz
 * @date 2020/8/26  9:58
 */
public final class DingTalkApiPathConsts {

    public static final String DEFAULT_DD_BASE_URL = "https://oapi.dingtalk.com";

    public static final String GET_TOKEN = "/gettoken?appkey=%s&appsecret=%s";

    public static final String GET_JSAPI_TICKET = "/get_jsapi_ticket";

    public static class OAuth2 {
        public static final String GET_USER_INFO = "/user/getuserinfo?code=%s";
    }

    public static class User {
        public static final String USER_GET = "/user/get?userid=";
    }

    public static class Chat {
        public static final String CHAT_CREATE = "/chat/create";

        public static final String CHAT_GET = "/chat/get?chatid=";

        public static final String CHAT_UPDATE = "/chat/update";

        public static final String CHAT_SEND = "/chat/send";
    }
}
