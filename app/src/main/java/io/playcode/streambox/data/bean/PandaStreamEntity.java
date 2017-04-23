package io.playcode.streambox.data.bean;

import java.util.List;

/**
 * Created by anpoz on 2017/4/14.
 */

public class PandaStreamEntity {

    /**
     * errno : 0
     * errmsg :
     * data : {"info":{"hostinfo":{"rid":27636760,"name":"主播方正","avatar":"http://i8.pdim.gs/4cde730a75bb4a12287ed033793b7d98.jpg","bamboos":"12981781"},"roominfo":{"id":"769965","name":"北京工体滴滴","type":"1","classification":"户外直播","cate":"hwzb","bulletin":"水友qq2群605101608，2个龙虾上房管，房管Q私聊进微信群，微博正哥帅过十个吴彦祖，合作qq736320073","details":"","person_num":"49413","fans":"57192","pictures":{"img":"http://i6.pdim.gs/90/7a8bf37f7eff0977fc91062b6e03d5de/w338/h190.jpg"},"display_type":"1","start_time":"1492958978","end_time":"1492891053","room_type":"1","status":"2","style_type":"1","remind_content":"","remind_time":"0","remind_status":"0"},"userinfo":{"rid":0},"videoinfo":{"name":"dota","time":"14602","stream_addr":{"HD":"0","OD":"0","SD":"1"},"room_key":"370daaa1e9ec694600b3dfe962fcab04","plflag":"11_21","status":"2","sign":"38153f226814ed9969edd0419d0d30ee","ts":"&ts=58fcf80c&rid=-66778604","hardware":2,"scheme":"http","slaveflag":["2_4"],"watermark":"1"}}}
     * authseq :
     */

    private int errno;
    private String errmsg;
    private DataEntity data;
    private String authseq;

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

    public String getAuthseq() {
        return authseq;
    }

    public void setAuthseq(String authseq) {
        this.authseq = authseq;
    }

    public static class DataEntity {
        /**
         * info : {"hostinfo":{"rid":27636760,"name":"主播方正","avatar":"http://i8.pdim.gs/4cde730a75bb4a12287ed033793b7d98.jpg","bamboos":"12981781"},"roominfo":{"id":"769965","name":"北京工体滴滴","type":"1","classification":"户外直播","cate":"hwzb","bulletin":"水友qq2群605101608，2个龙虾上房管，房管Q私聊进微信群，微博正哥帅过十个吴彦祖，合作qq736320073","details":"","person_num":"49413","fans":"57192","pictures":{"img":"http://i6.pdim.gs/90/7a8bf37f7eff0977fc91062b6e03d5de/w338/h190.jpg"},"display_type":"1","start_time":"1492958978","end_time":"1492891053","room_type":"1","status":"2","style_type":"1","remind_content":"","remind_time":"0","remind_status":"0"},"userinfo":{"rid":0},"videoinfo":{"name":"dota","time":"14602","stream_addr":{"HD":"0","OD":"0","SD":"1"},"room_key":"370daaa1e9ec694600b3dfe962fcab04","plflag":"11_21","status":"2","sign":"38153f226814ed9969edd0419d0d30ee","ts":"&ts=58fcf80c&rid=-66778604","hardware":2,"scheme":"http","slaveflag":["2_4"],"watermark":"1"}}
         */

        private InfoEntity info;

        public InfoEntity getInfo() {
            return info;
        }

        public void setInfo(InfoEntity info) {
            this.info = info;
        }

        public static class InfoEntity {
            /**
             * hostinfo : {"rid":27636760,"name":"主播方正","avatar":"http://i8.pdim.gs/4cde730a75bb4a12287ed033793b7d98.jpg","bamboos":"12981781"}
             * roominfo : {"id":"769965","name":"北京工体滴滴","type":"1","classification":"户外直播","cate":"hwzb","bulletin":"水友qq2群605101608，2个龙虾上房管，房管Q私聊进微信群，微博正哥帅过十个吴彦祖，合作qq736320073","details":"","person_num":"49413","fans":"57192","pictures":{"img":"http://i6.pdim.gs/90/7a8bf37f7eff0977fc91062b6e03d5de/w338/h190.jpg"},"display_type":"1","start_time":"1492958978","end_time":"1492891053","room_type":"1","status":"2","style_type":"1","remind_content":"","remind_time":"0","remind_status":"0"}
             * userinfo : {"rid":0}
             * videoinfo : {"name":"dota","time":"14602","stream_addr":{"HD":"0","OD":"0","SD":"1"},"room_key":"370daaa1e9ec694600b3dfe962fcab04","plflag":"11_21","status":"2","sign":"38153f226814ed9969edd0419d0d30ee","ts":"&ts=58fcf80c&rid=-66778604","hardware":2,"scheme":"http","slaveflag":["2_4"],"watermark":"1"}
             */

            private HostinfoEntity hostinfo;
            private RoominfoEntity roominfo;
            private UserinfoEntity userinfo;
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

            public UserinfoEntity getUserinfo() {
                return userinfo;
            }

            public void setUserinfo(UserinfoEntity userinfo) {
                this.userinfo = userinfo;
            }

            public VideoinfoEntity getVideoinfo() {
                return videoinfo;
            }

            public void setVideoinfo(VideoinfoEntity videoinfo) {
                this.videoinfo = videoinfo;
            }

            public static class HostinfoEntity {
                /**
                 * rid : 27636760
                 * name : 主播方正
                 * avatar : http://i8.pdim.gs/4cde730a75bb4a12287ed033793b7d98.jpg
                 * bamboos : 12981781
                 */

                private int rid;
                private String name;
                private String avatar;
                private String bamboos;

                public int getRid() {
                    return rid;
                }

                public void setRid(int rid) {
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
                 * id : 769965
                 * name : 北京工体滴滴
                 * type : 1
                 * classification : 户外直播
                 * cate : hwzb
                 * bulletin : 水友qq2群605101608，2个龙虾上房管，房管Q私聊进微信群，微博正哥帅过十个吴彦祖，合作qq736320073
                 * details :
                 * person_num : 49413
                 * fans : 57192
                 * pictures : {"img":"http://i6.pdim.gs/90/7a8bf37f7eff0977fc91062b6e03d5de/w338/h190.jpg"}
                 * display_type : 1
                 * start_time : 1492958978
                 * end_time : 1492891053
                 * room_type : 1
                 * status : 2
                 * style_type : 1
                 * remind_content :
                 * remind_time : 0
                 * remind_status : 0
                 */

                private String id;
                private String name;
                private String type;
                private String classification;
                private String cate;
                private String bulletin;
                private String details;
                private String person_num;
                private String fans;
                private PicturesEntity pictures;
                private String display_type;
                private String start_time;
                private String end_time;
                private String room_type;
                private String status;
                private String style_type;
                private String remind_content;
                private String remind_time;
                private String remind_status;

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

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
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

                public String getDetails() {
                    return details;
                }

                public void setDetails(String details) {
                    this.details = details;
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

                public String getStyle_type() {
                    return style_type;
                }

                public void setStyle_type(String style_type) {
                    this.style_type = style_type;
                }

                public String getRemind_content() {
                    return remind_content;
                }

                public void setRemind_content(String remind_content) {
                    this.remind_content = remind_content;
                }

                public String getRemind_time() {
                    return remind_time;
                }

                public void setRemind_time(String remind_time) {
                    this.remind_time = remind_time;
                }

                public String getRemind_status() {
                    return remind_status;
                }

                public void setRemind_status(String remind_status) {
                    this.remind_status = remind_status;
                }

                public static class PicturesEntity {
                    /**
                     * img : http://i6.pdim.gs/90/7a8bf37f7eff0977fc91062b6e03d5de/w338/h190.jpg
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

            public static class UserinfoEntity {
                /**
                 * rid : 0
                 */

                private int rid;

                public int getRid() {
                    return rid;
                }

                public void setRid(int rid) {
                    this.rid = rid;
                }
            }

            public static class VideoinfoEntity {
                /**
                 * name : dota
                 * time : 14602
                 * stream_addr : {"HD":"0","OD":"0","SD":"1"}
                 * room_key : 370daaa1e9ec694600b3dfe962fcab04
                 * plflag : 11_21
                 * status : 2
                 * sign : 38153f226814ed9969edd0419d0d30ee
                 * ts : &ts=58fcf80c&rid=-66778604
                 * hardware : 2
                 * scheme : http
                 * slaveflag : ["2_4"]
                 * watermark : 1
                 */

                private String name;
                private String time;
                private StreamAddrEntity stream_addr;
                private String room_key;
                private String plflag;
                private String status;
                private String sign;
                private String ts;
                private int hardware;
                private String scheme;
                private String watermark;
                private List<String> slaveflag;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public StreamAddrEntity getStream_addr() {
                    return stream_addr;
                }

                public void setStream_addr(StreamAddrEntity stream_addr) {
                    this.stream_addr = stream_addr;
                }

                public String getRoom_key() {
                    return room_key;
                }

                public void setRoom_key(String room_key) {
                    this.room_key = room_key;
                }

                public String getPlflag() {
                    return plflag;
                }

                public void setPlflag(String plflag) {
                    this.plflag = plflag;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }

                public String getTs() {
                    return ts;
                }

                public void setTs(String ts) {
                    this.ts = ts;
                }

                public int getHardware() {
                    return hardware;
                }

                public void setHardware(int hardware) {
                    this.hardware = hardware;
                }

                public String getScheme() {
                    return scheme;
                }

                public void setScheme(String scheme) {
                    this.scheme = scheme;
                }

                public String getWatermark() {
                    return watermark;
                }

                public void setWatermark(String watermark) {
                    this.watermark = watermark;
                }

                public List<String> getSlaveflag() {
                    return slaveflag;
                }

                public void setSlaveflag(List<String> slaveflag) {
                    this.slaveflag = slaveflag;
                }

                public static class StreamAddrEntity {
                    /**
                     * HD : 0
                     * OD : 0
                     * SD : 1
                     */

                    private String HD;
                    private String OD;
                    private String SD;

                    public String getHD() {
                        return HD;
                    }

                    public void setHD(String HD) {
                        this.HD = HD;
                    }

                    public String getOD() {
                        return OD;
                    }

                    public void setOD(String OD) {
                        this.OD = OD;
                    }

                    public String getSD() {
                        return SD;
                    }

                    public void setSD(String SD) {
                        this.SD = SD;
                    }
                }
            }
        }
    }
}
