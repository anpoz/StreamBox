package io.playcode.streambox.data.bean;

/**
 * Created by anpoz on 2017/4/14.
 */

public class PandaStreamEntity {

    /**
     * errno : 0
     * errmsg :
     * data : {"hostinfo":{"rid":"29544914","name":"小马AAAAAA","avatar":"http://i9.pdim.gs/fab3da6d679bbd75c45a92639a6ffa9a.jpeg","bamboos":"8931979"},"roominfo":{"id":"16688","name":"今天提前溜。要去LSPL现场助阵！","classification":"英雄联盟","cate":"lol","bulletin":"直播时间12点-6点 有时候加班！\n ","person_num":"230072","fans":"0","pictures":{"img":"http://i5.pdim.gs/90/f1428c8c77436b26a808c7ff4989f6c3/w338/h190.jpg"},"display_type":"1","start_time":"1492142299","end_time":"1492080996","room_type":"1","status":"2"},"videoinfo":{"address":"http://pl-hls3.live.panda.tv/live_panda/7d9bdfd8beca4be796bc4b757503decd_small.m3u8","watermark":"1"}}
     * authseq :
     */

    private String errno;
    private String errmsg;
    private DataEntity data;
    private String authseq;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
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

    public String getAuthseq() {
        return authseq;
    }

    public void setAuthseq(String authseq) {
        this.authseq = authseq;
    }

    public static class DataEntity {
        /**
         * hostinfo : {"rid":"29544914","name":"小马AAAAAA","avatar":"http://i9.pdim.gs/fab3da6d679bbd75c45a92639a6ffa9a.jpeg","bamboos":"8931979"}
         * roominfo : {"id":"16688","name":"今天提前溜。要去LSPL现场助阵！","classification":"英雄联盟","cate":"lol","bulletin":"直播时间12点-6点 有时候加班！\n ","person_num":"230072","fans":"0","pictures":{"img":"http://i5.pdim.gs/90/f1428c8c77436b26a808c7ff4989f6c3/w338/h190.jpg"},"display_type":"1","start_time":"1492142299","end_time":"1492080996","room_type":"1","status":"2"}
         * videoinfo : {"address":"http://pl-hls3.live.panda.tv/live_panda/7d9bdfd8beca4be796bc4b757503decd_small.m3u8","watermark":"1"}
         */

        private HostinfoEntity hostinfo;
        private RoominfoEntity roominfo;
        private VideoinfoEntity videoinfo;

        public HostinfoEntity getHostinfo() {
            return hostinfo;
        }

        public void setHostinfo(HostinfoEntity hostinfo) {
            this.hostinfo = hostinfo;
        }

        public RoominfoEntity getRoominfo() {
            return roominfo;
        }

        public void setRoominfo(RoominfoEntity roominfo) {
            this.roominfo = roominfo;
        }

        public VideoinfoEntity getVideoinfo() {
            return videoinfo;
        }

        public void setVideoinfo(VideoinfoEntity videoinfo) {
            this.videoinfo = videoinfo;
        }

        public static class HostinfoEntity {
            /**
             * rid : 29544914
             * name : 小马AAAAAA
             * avatar : http://i9.pdim.gs/fab3da6d679bbd75c45a92639a6ffa9a.jpeg
             * bamboos : 8931979
             */

            private String rid;
            private String name;
            private String avatar;
            private String bamboos;

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getBamboos() {
                return bamboos;
            }

            public void setBamboos(String bamboos) {
                this.bamboos = bamboos;
            }
        }

        public static class RoominfoEntity {
            /**
             * id : 16688
             * name : 今天提前溜。要去LSPL现场助阵！
             * classification : 英雄联盟
             * cate : lol
             * bulletin : 直播时间12点-6点 有时候加班！

             * person_num : 230072
             * fans : 0
             * pictures : {"img":"http://i5.pdim.gs/90/f1428c8c77436b26a808c7ff4989f6c3/w338/h190.jpg"}
             * display_type : 1
             * start_time : 1492142299
             * end_time : 1492080996
             * room_type : 1
             * status : 2
             */

            private String id;
            private String name;
            private String classification;
            private String cate;
            private String bulletin;
            private String person_num;
            private String fans;
            private PicturesEntity pictures;
            private String display_type;
            private String start_time;
            private String end_time;
            private String room_type;
            private String status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getClassification() {
                return classification;
            }

            public void setClassification(String classification) {
                this.classification = classification;
            }

            public String getCate() {
                return cate;
            }

            public void setCate(String cate) {
                this.cate = cate;
            }

            public String getBulletin() {
                return bulletin;
            }

            public void setBulletin(String bulletin) {
                this.bulletin = bulletin;
            }

            public String getPerson_num() {
                return person_num;
            }

            public void setPerson_num(String person_num) {
                this.person_num = person_num;
            }

            public String getFans() {
                return fans;
            }

            public void setFans(String fans) {
                this.fans = fans;
            }

            public PicturesEntity getPictures() {
                return pictures;
            }

            public void setPictures(PicturesEntity pictures) {
                this.pictures = pictures;
            }

            public String getDisplay_type() {
                return display_type;
            }

            public void setDisplay_type(String display_type) {
                this.display_type = display_type;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getRoom_type() {
                return room_type;
            }

            public void setRoom_type(String room_type) {
                this.room_type = room_type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public static class PicturesEntity {
                /**
                 * img : http://i5.pdim.gs/90/f1428c8c77436b26a808c7ff4989f6c3/w338/h190.jpg
                 */

                private String img;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }
        }

        public static class VideoinfoEntity {
            /**
             * address : http://pl-hls3.live.panda.tv/live_panda/7d9bdfd8beca4be796bc4b757503decd_small.m3u8
             * watermark : 1
             */

            private String address;
            private String watermark;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getWatermark() {
                return watermark;
            }

            public void setWatermark(String watermark) {
                this.watermark = watermark;
            }
        }
    }
}
