package io.playcode.streambox.data.bean;

/**
 * Created by anpoz on 2017/4/15.
 */

public class PandaDanmuEntity {

    /**
     * type : 1
     * time : 1477356608
     * data : {"from":{"__plat":"android","identity":"30","level":"4","msgcolor":"","nickName":"看了还说了","rid":"45560306","sp_identity":"0","userName":""},"to":{"toroom":"15161"},"content":"我去"}
     */

    private String type;
    private int time;
    private DataEntity data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * from : {"__plat":"android","identity":"30","level":"4","msgcolor":"","nickName":"看了还说了","rid":"45560306","sp_identity":"0","userName":""}
         * to : {"toroom":"15161"}
         * content : 我去
         */

        private FromEntity from;
        private ToEntity to;
        private String content;

        public FromEntity getFrom() {
            return from;
        }

        public void setFrom(FromEntity from) {
            this.from = from;
        }

        public ToEntity getTo() {
            return to;
        }

        public void setTo(ToEntity to) {
            this.to = to;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public static class FromEntity {
            /**
             * __plat : android
             * identity : 30
             * level : 4
             * msgcolor :
             * nickName : 看了还说了
             * rid : 45560306
             * sp_identity : 0
             * userName :
             */

            private String __plat;
            private String identity;
            private String level;
            private String msgcolor;
            private String nickName;
            private String rid;
            private String sp_identity;
            private String userName;

            public String get__plat() {
                return __plat;
            }

            public void set__plat(String __plat) {
                this.__plat = __plat;
            }

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getMsgcolor() {
                return msgcolor;
            }

            public void setMsgcolor(String msgcolor) {
                this.msgcolor = msgcolor;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public String getSp_identity() {
                return sp_identity;
            }

            public void setSp_identity(String sp_identity) {
                this.sp_identity = sp_identity;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class ToEntity {
            /**
             * toroom : 15161
             */

            private String toroom;

            public String getToroom() {
                return toroom;
            }

            public void setToroom(String toroom) {
                this.toroom = toroom;
            }
        }
    }
}
