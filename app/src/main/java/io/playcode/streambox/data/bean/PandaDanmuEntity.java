package io.playcode.streambox.data.bean;

/**
 * Created by anpoz on 2017/4/15.
 */

public class PandaDanmuEntity {

    /**
     * type : 1
     * time : 1492968699
     * data : {"from":{"identity":"30","nickName":"0005丶vip打折卡","badge":"","rid":"40533958","msgcolor":"","level":"7","sp_identity":"0","__plat":"android","userName":""},"to":{"toroom":"415164"},"content":"666666666"}
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
         * from : {"identity":"30","nickName":"0005丶vip打折卡","badge":"","rid":"40533958","msgcolor":"","level":"7","sp_identity":"0","__plat":"android","userName":""}
         * to : {"toroom":"415164"}
         * content : 666666666
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
             * identity : 30
             * nickName : 0005丶vip打折卡
             * badge :
             * rid : 40533958
             * msgcolor :
             * level : 7
             * sp_identity : 0
             * __plat : android
             * userName :
             */

            private String identity;
            private String nickName;
            private String badge;
            private String rid;
            private String msgcolor;
            private String level;
            private String sp_identity;
            private String __plat;
            private String userName;

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getBadge() {
                return badge;
            }

            public void setBadge(String badge) {
                this.badge = badge;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public String getMsgcolor() {
                return msgcolor;
            }

            public void setMsgcolor(String msgcolor) {
                this.msgcolor = msgcolor;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getSp_identity() {
                return sp_identity;
            }

            public void setSp_identity(String sp_identity) {
                this.sp_identity = sp_identity;
            }

            public String get__plat() {
                return __plat;
            }

            public void set__plat(String __plat) {
                this.__plat = __plat;
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
             * toroom : 415164
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
