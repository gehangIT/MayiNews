package com.mayinews.mv.user.bean;

import java.util.List;

/**
 * Created by 盖航_ on 2017/8/22.
 */

public class SubscribeBean {


    /**
     * status : 200
     * result : [{"id":"1","username":"root","nickname":"root","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"}]
     * count : 1
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
         * id : 1
         * username : root
         * nickname : root
         * avatar : http://static.mayinews.com/Uploads/Picture/video_avatar.png
         */
        private String uid;
        private String desc;
        private String nickname;
        private String avatar;   //封面

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
