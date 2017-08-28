package com.mayinews.mv.attention.bean;

import java.util.List;

/**
 * Created by 盖航_ on 2017/8/24.
 * 指定作者对应的视频
 */

public class UserVideo {


    /**
     * status : 200
     * count : 10
     * result : [{"vid":"00dc672d6f744c52a842e808be71f1e3","uid":"18","cid":"130","title":"潘玮柏化身厨师为吴昕下厨做铁板烧","desc":"","size":"4251279","duration":"133","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-16/5993ebfe0077b.jpeg","view":"0","comment":"0","create_time":"1502866025","update_time":"1502866432","status":"1"},{"vid":"00f92b7e062045e1bbc0638c5e0503b0","uid":"18","cid":"130","title":"【#快乐大本营#】赵丽颖cut","desc":"","size":"47994773","duration":"767","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-15/5992599a86b04.jpeg","view":"0","comment":"0","create_time":"1502762509","update_time":"1502763422","status":"1"},{"vid":"01ca355cf66c48a69fb603a98222de61","uid":"18","cid":"109","title":"周杰伦早期说唱作品盘点","desc":"","size":"30449035","duration":"281","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-22/599baad6dfe0a.jpeg","view":"0","comment":"0","create_time":"1503373324","update_time":"1503374046","status":"1"},{"vid":"02531af161834c96845bc92465566482","uid":"18","cid":"109","title":"周二珂冯提莫翻唱田馥甄的《你就不要想起我》","desc":"","size":"5077221","duration":"280","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-22/599baab293263.jpeg","view":"0","comment":"0","create_time":"1503373324","update_time":"1503374004","status":"1"},{"vid":"02720439ce6c444ab9027b56c2253e98","uid":"18","cid":"130","title":"极限挑战北电和中戏的调侃","desc":"","size":"3932649","duration":"59","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-14/59915e601c17b.jpeg","view":"0","comment":"0","create_time":"1502697940","update_time":"1502699107","status":"1"},{"vid":"03a873977c664f03b9709d3106acb8d4","uid":"18","cid":"130","title":"甜馨跳舞视频","desc":"","size":"374595","duration":"16","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-21/599a5c5c18126.jpeg","view":"0","comment":"0","create_time":"1503288253","update_time":"1503288414","status":"1"},{"vid":"0762eec12c764e6a995a9b82c490e463","uid":"18","cid":"130","title":"阿拉蕾董力采访","desc":"","size":"7951094","duration":"178","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-17/599564718d58b.jpeg","view":"0","comment":"0","create_time":"1502961923","update_time":"1502962803","status":"1"},{"vid":"08c2ed39b7484ff3bf6e009f0fa897d1","uid":"18","cid":"130","title":"吴昕和潘玮柏完全不像CP，反而一起坐在沙发上聊天的时候比较好玩","desc":"","size":"3011720","duration":"118","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-11/598d7edf52196.jpeg","view":"0","comment":"0","create_time":"1502444918","update_time":"1502445280","status":"1"},{"vid":"096e3def248649b19fa7f1dbc6343a34","uid":"18","cid":"130","title":"PENTAGON初声游戏","desc":"","size":"24027563","duration":"340","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-14/59910e4fe0682.jpeg","view":"0","comment":"0","create_time":"1502678129","update_time":"1502678614","status":"1"},{"vid":"09fe22b8494f4a6c82a1902fd9b59f21","uid":"18","cid":"130","title":"《约吧大明星》第二季第4期看点：单身狗鹿晗\u201d","desc":"","size":"973431","duration":"11","cover":"http://static.mayinews.com/Uploads/Picture/2017-08-17/59950c29258ee.jpeg","view":"0","comment":"0","create_time":"1502940060","update_time":"1502940204","status":"1"}]
     */

    private int status;
    private int count;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * vid : 00dc672d6f744c52a842e808be71f1e3
         * uid : 18
         * cid : 130
         * title : 潘玮柏化身厨师为吴昕下厨做铁板烧
         * desc :
         * size : 4251279
         * duration : 133
         * cover : http://static.mayinews.com/Uploads/Picture/2017-08-16/5993ebfe0077b.jpeg
         * view : 0
         * comment : 0
         * create_time : 1502866025
         * update_time : 1502866432
         * status : 1
         */

        private String vid;
        private String uid;
        private String cid;
        private String title;
        private String desc;
        private String size;
        private String duration;
        private String cover;
        private String view;
        private String comment;
        private String create_time;
        private String update_time;
        private String status;

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
