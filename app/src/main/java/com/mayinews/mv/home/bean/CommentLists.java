package com.mayinews.mv.home.bean;

import java.util.List;

/**
 * Created by 盖航_ on 2017/8/22.
 */

public class CommentLists {


    /**
     * status : 200
     * result : [{"vid":"4927459f52094f42b72b285905eb837c","uid":"1","tocid":"0","comment":"中文测试22","create_time":"1503481774","id":"16","nickname":"root"},{"vid":"4927459f52094f42b72b285905eb837c","uid":"1","tocid":"0","comment":"中文测试","create_time":"1503481764","id":"15","nickname":"root"},{"vid":"4927459f52094f42b72b285905eb837c","uid":"1","tocid":"0","comment":"中文测试","create_time":"1503481394","id":"10","nickname":"root"},{"vid":"4927459f52094f42b72b285905eb837c","uid":"1","tocid":"0","comment":"中文测试","create_time":"1503481127","id":"9","nickname":"root"},{"vid":"4927459f52094f42b72b285905eb837c","uid":"1","tocid":"0","comment":"中文测试","create_time":"1503477959","id":"8","nickname":"root"},{"vid":"4927459f52094f42b72b285905eb837c","uid":"1","tocid":"1","comment":"okokokokokokok12","create_time":"1503475965","id":"7","nickname":"root"},{"vid":"4927459f52094f42b72b285905eb837c","uid":"32","tocid":"0","comment":"okokookokok","create_time":"1503375149","id":"1","weight":"0","status":"1"}]
     * count : 7
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
         * vid : 4927459f52094f42b72b285905eb837c
         * uid : 1
         * tocid : 0
         * comment : 中文测试22
         * create_time : 1503481774
         * id : 16
         * nickname : root
         * weight : 0
         * status : 1
         */
        private String avatar;
        private String vid;
        private String uid;
        private String tocid;
        private String comment;
        private String create_time;
        private String id;
        private String nickname;

        public ResultBean() {
        }


        public ResultBean(String avatar, String comment, String create_time, String nickname) {

           this.avatar=avatar;
            this.comment = comment;
            this.create_time = create_time;
            this.nickname = nickname;
        }


        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

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

        public String getTocid() {
            return tocid;
        }

        public void setTocid(String tocid) {
            this.tocid = tocid;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

    }
}
