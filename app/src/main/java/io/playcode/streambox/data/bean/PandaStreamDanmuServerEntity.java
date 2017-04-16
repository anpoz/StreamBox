package io.playcode.streambox.data.bean;

import java.util.List;

/**
 * Created by anpoz on 2017/4/14.
 */

public class PandaStreamDanmuServerEntity {

    /**
     * errno : 0
     * errmsg :
     * data : {"appid":"134224728","rid":3174618,"sign":"201effba6855f95a69098e7891a25abe","authType":"4","ts":1492139948000,"chat_addr_list":["115.159.247.243:3389","115.159.247.170:3389","115.159.247.231:3389"]}
     */

    private int errno;
    private String errmsg;
    private DataEntity data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * appid : 134224728
         * rid : 3174618
         * sign : 201effba6855f95a69098e7891a25abe
         * authType : 4
         * ts : 1492139948000
         * chat_addr_list : ["115.159.247.243:3389","115.159.247.170:3389","115.159.247.231:3389"]
         */

        private String appid;
        private int rid;
        private String sign;
        private String authType;
        private long ts;
        private List<String> chat_addr_list;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAuthType() {
            return authType;
        }

        public void setAuthType(String authType) {
            this.authType = authType;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(long ts) {
            this.ts = ts;
        }

        public List<String> getChat_addr_list() {
            return chat_addr_list;
        }

        public void setChat_addr_list(List<String> chat_addr_list) {
            this.chat_addr_list = chat_addr_list;
        }
    }
}
