package io.playcode.streambox.data.bean;

/**
 * Created by anpoz on 2017/4/16.
 */

public class StreamInfoEntity {
    private String live_id;
    private String live_img;
    private String live_nickname;
    private String live_online;
    private String live_title;
    private String live_type;
    private String push_time;

    public String getLive_id() {
        return live_id;
    }

    public void setLive_id(String live_id) {
        this.live_id = live_id;
    }

    public String getLive_img() {
        return live_img;
    }

    public void setLive_img(String live_img) {
        this.live_img = live_img;
    }

    public String getLive_nickname() {
        return live_nickname;
    }

    public void setLive_nickname(String live_nickname) {
        this.live_nickname = live_nickname;
    }

    public String getLive_online() {
        return live_online;
    }

    public void setLive_online(String live_online) {
        this.live_online = live_online;
    }

    public String getLive_title() {
        return live_title;
    }

    public void setLive_title(String live_title) {
        this.live_title = live_title;
    }

    public String getLive_type() {
        return live_type;
    }

    public void setLive_type(String live_type) {
        this.live_type = live_type;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }
}
