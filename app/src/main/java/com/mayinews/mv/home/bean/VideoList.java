package com.mayinews.mv.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gary on 2017/7/3 0003.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class VideoList {


    /**
     * currstatus : -1
     * data : [{"cid":"30","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-29/59547f9fd00e0.jpeg","create_time":"1497467722","desc":"三位模仿者与张学友同台献唱，歌神一开口台下沸腾了！三位模仿者与张学友同台献唱，歌神一开口台下沸腾了！","duration":"197.21","size":"11626103","status":"1","title":"三位模仿者与张学友同台献唱，歌神一开口台下沸腾了！","uid":"1","update_time":"1497612316","vid":"16042b1df0124162827e8c236e42393e","view":"0"},{"cid":"33","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-16/5943aebd33502.jpeg","create_time":"1497467717","desc":"","duration":"100.86","size":"5160521","status":"1","title":"李达康开炮！59D坦克现身非洲、驰骋战场，战狼2预告片曝光","uid":"1","update_time":"1497467888","vid":"84ed622061b64743b9560e050214ef3b","view":"0"},{"cid":"34","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-16/5943aebda7b87.jpeg","create_time":"1497467710","desc":"","duration":"175.22","size":"6699868","status":"1","title":"海贼王：巴杰斯偷袭不成惨遭秒杀，叫你在萨博面前嘲讽艾斯","uid":"1","update_time":"1497467888","vid":"64ef9b583bea4b2db1a74b78a82b5b2d","view":"0"},{"cid":"30","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-16/5943aebe28ce8.jpeg","create_time":"1497467696","desc":"","duration":"197.4","size":"15426236","status":"1","title":"哥哥去世弟弟把侄子当亲生儿子养，唱黄家驹《真的爱你》感动全场","uid":"1","update_time":"1497467888","vid":"e7d2adc754aa4dbbbfec9aebc96ceee9","view":"0"},{"cid":"32","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-16/5943aebea1a8b.jpeg","create_time":"1497467682","desc":"","duration":"205.4","size":"14342280","status":"1","title":"曾经的短跑王者！ 迈克尔约翰逊96年奥运会200米400米创","uid":"1","update_time":"1497467888","vid":"51c1a61798634f10b691fca6d8a2dc4a","view":"0"},{"cid":"30","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-16/5943aebf16a5d.jpeg","create_time":"1497463446","desc":"","duration":"465.2","size":"15338570","status":"1","title":"张国荣在告别演唱会上的这段话，还有多少人记得吗？","uid":"1","update_time":"1497467888","vid":"ea9621a6b4e4492bb41e5442979df2a3","view":"0"},{"cid":"34","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-16/5943aebf86a65.jpeg","create_time":"1497463437","desc":"","duration":"107.04","size":"9054013","status":"1","title":"宇智波鼬强力脱离秽土转生，他才是宇智波一族最强的人","uid":"1","update_time":"1497467888","vid":"534ae6c2f9e7428e890e0ac0070991a2","view":"0"},{"cid":"30","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-16/5943aebfeb95f.jpeg","create_time":"1497463423","desc":"","duration":"178.04","size":"14497151","status":"1","title":"一首《 蝴蝶泉边》超好听，醉了。","uid":"1","update_time":"1497467888","vid":"0dd0dd88ed1f40968fff5a90313c5588","view":"0"},{"cid":"30","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-16/5943aec064f96.jpeg","create_time":"1497463403","desc":"","duration":"240.51","size":"21195498","status":"1","title":"街头艺人演唱\u201c喜欢你\u201d","uid":"1","update_time":"1497467888","vid":"156ee3ed4d194b6eaf2d24e0d1dbb3b9","view":"0"},{"cid":"34","cover":"http://static.mayinews.com/Uploads/Picture/2017-06-16/5943aec0e7c70.jpeg","create_time":"1497463393","desc":"","duration":"98","size":"8892416","status":"1","title":"灰原的真面目暴露了？也许柯南的结局我们早就已经看过了！","uid":"1","update_time":"1497467889","vid":"7954fee8c1904200b395cbdd9630358d","view":"0"}]
     * message : error
     * page : 1
     * pages : 11
     * search_type : 0
     * status : 1
     */

    private int currstatus;
    private String message;
    private int page;
    private int pages;
    private int search_type;
    private int status;
    private List<DataBean> data;

    public int getCurrstatus() {
        return currstatus;
    }

    public void setCurrstatus(int currstatus) {
        this.currstatus = currstatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSearch_type() {
        return search_type;
    }

    public void setSearch_type(int search_type) {
        this.search_type = search_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean  implements Parcelable{
        /**
         * cid : 30
         * cover : http://static.mayinews.com/Uploads/Picture/2017-06-29/59547f9fd00e0.jpeg
         * create_time : 1497467722
         * desc : 三位模仿者与张学友同台献唱，歌神一开口台下沸腾了！三位模仿者与张学友同台献唱，歌神一开口台下沸腾了！
         * duration : 197.21
         * size : 11626103
         * status : 1
         * title : 三位模仿者与张学友同台献唱，歌神一开口台下沸腾了！
         * uid : 1
         * update_time : 1497612316
         * vid : 16042b1df0124162827e8c236e42393e
         * view : 0
         */

        private String cid;
        private String cover;
        private String create_time;
        private String desc;
        private String duration;
        private String size;
        private String status;
        private String title;
        private String uid;
        private String update_time;
        private String vid;
        private String view;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            cid = in.readString();
            cover = in.readString();
            create_time = in.readString();
            desc = in.readString();
            duration = in.readString();
            size = in.readString();
            status = in.readString();
            title = in.readString();
            uid = in.readString();
            update_time = in.readString();
            vid = in.readString();
            view = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(cid);
            dest.writeString(cover);
            dest.writeString(create_time);
            dest.writeString(desc);
            dest.writeString(duration);
            dest.writeString(size);
            dest.writeString(status);
            dest.writeString(title);
            dest.writeString(uid);
            dest.writeString(update_time);
            dest.writeString(vid);
            dest.writeString(view);
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "cid='" + cid + '\'' +
                    ", cover='" + cover + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", desc='" + desc + '\'' +
                    ", duration='" + duration + '\'' +
                    ", size='" + size + '\'' +
                    ", status='" + status + '\'' +
                    ", title='" + title + '\'' +
                    ", uid='" + uid + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", vid='" + vid + '\'' +
                    ", view='" + view + '\'' +
                    '}';
        }
    }
}
