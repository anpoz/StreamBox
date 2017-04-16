package io.playcode.streambox.data.bean;

import java.util.List;

/**
 * Created by anpoz on 2017/4/16.
 */

public class CommonStreamEntity {

    /**
     * msg :
     * result : {"enable":1,"game_type":"lol","is_followed":0,"live_id":"56040","live_img":"https://rpic.douyucdn.cn/a1704/16/09/56040_170416091651.jpg","live_name":"douyu","live_nickname":"主播油条","live_online":221390,"live_title":"油条：俄罗斯第一绝地求生玩家牛B条","live_type":"douyu","live_userimg":"","offline_time":"1492300808.3127","online_time":"1492305132.2509","push_time":"1492265401.9044","sort_num":221390,"stream_list":[{"type":"超清","url":"http://hdl3a.douyutv.com/live/56040rdE3glRTHoy.flv?wsSecret=4a7b08440f47198623e7cb6542b7438a&wsTime=1454396745"},{"type":"普清","url":"http://hdl3a.douyutv.com/live/56040rdE3glRTHoy_550.flv?wsSecret=8c8d7dd2f30ad206512327a84271f9f3&wsTime=1454396745"}]}
     * status : ok
     */

    private String msg;
    private ResultEntity result;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultEntity {
        /**
         * enable : 1
         * game_type : lol
         * is_followed : 0
         * live_id : 56040
         * live_img : https://rpic.douyucdn.cn/a1704/16/09/56040_170416091651.jpg
         * live_name : douyu
         * live_nickname : 主播油条
         * live_online : 221390
         * live_title : 油条：俄罗斯第一绝地求生玩家牛B条
         * live_type : douyu
         * live_userimg :
         * offline_time : 1492300808.3127
         * online_time : 1492305132.2509
         * push_time : 1492265401.9044
         * sort_num : 221390
         * stream_list : [{"type":"超清","url":"http://hdl3a.douyutv.com/live/56040rdE3glRTHoy.flv?wsSecret=4a7b08440f47198623e7cb6542b7438a&wsTime=1454396745"},{"type":"普清","url":"http://hdl3a.douyutv.com/live/56040rdE3glRTHoy_550.flv?wsSecret=8c8d7dd2f30ad206512327a84271f9f3&wsTime=1454396745"}]
         */

        private int enable;
        private String game_type;
        private int is_followed;
        private String live_id;
        private String live_img;
        private String live_name;
        private String live_nickname;
        private int live_online;
        private String live_title;
        private String live_type;
        private String live_userimg;
        private String offline_time;
        private String online_time;
        private String push_time;
        private int sort_num;
        private List<StreamListEntity> stream_list;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public String getGame_type() {
            return game_type;
        }

        public void setGame_type(String game_type) {
            this.game_type = game_type;
        }

        public int getIs_followed() {
            return is_followed;
        }

        public void setIs_followed(int is_followed) {
            this.is_followed = is_followed;
        }

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

        public String getLive_name() {
            return live_name;
        }

        public void setLive_name(String live_name) {
            this.live_name = live_name;
        }

        public String getLive_nickname() {
            return live_nickname;
        }

        public void setLive_nickname(String live_nickname) {
            this.live_nickname = live_nickname;
        }

        public int getLive_online() {
            return live_online;
        }

        public void setLive_online(int live_online) {
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

        public String getLive_userimg() {
            return live_userimg;
        }

        public void setLive_userimg(String live_userimg) {
            this.live_userimg = live_userimg;
        }

        public String getOffline_time() {
            return offline_time;
        }

        public void setOffline_time(String offline_time) {
            this.offline_time = offline_time;
        }

        public String getOnline_time() {
            return online_time;
        }

        public void setOnline_time(String online_time) {
            this.online_time = online_time;
        }

        public String getPush_time() {
            return push_time;
        }

        public void setPush_time(String push_time) {
            this.push_time = push_time;
        }

        public int getSort_num() {
            return sort_num;
        }

        public void setSort_num(int sort_num) {
            this.sort_num = sort_num;
        }

        public List<StreamListEntity> getStream_list() {
            return stream_list;
        }

        public void setStream_list(List<StreamListEntity> stream_list) {
            this.stream_list = stream_list;
        }

        public static class StreamListEntity {
            /**
             * type : 超清
             * url : http://hdl3a.douyutv.com/live/56040rdE3glRTHoy.flv?wsSecret=4a7b08440f47198623e7cb6542b7438a&wsTime=1454396745
             */

            private String type;
            private String url;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
