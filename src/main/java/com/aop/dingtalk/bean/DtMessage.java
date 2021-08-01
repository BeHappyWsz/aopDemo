package com.aop.dingtalk.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * 消息格式
 * @author wsz
 * @date 2020/8/26  20:58
 */
public class DtMessage {


    /**
     * 文本信息
     */
    public static DtMessage.Text TEXT() {
        return new DtMessage.Text();
    }
    public static class Text {
        private String content;
        public Text content (String content) {
            this.content = content;
            return this;
        }
        public JSONObject build() {
            JSONObject msg = new JSONObject();

            JSONObject text = new JSONObject();
            text.put("content", this.content);

            msg.put("text", text);
            msg.put("msgtype", "text");
            return msg;
        }
    }

    /**
     * 图片信息
     */
    public static DtMessage.Image IMAGE() {
        return new DtMessage.Image();
    }
    public static class Image {
        private String media_id;
        public Image media_id (String media_id) {
            this.media_id = media_id;
            return this;
        }
        public JSONObject build() {
            JSONObject msg = new JSONObject();

            JSONObject image = new JSONObject();
            image.put("media_id", this.media_id);

            msg.put("image", image);
            msg.put("msgtype", "image");
            return msg;
        }
    }

}
