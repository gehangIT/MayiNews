package com.mayinews.mv.user.bean;

import java.util.List;

/**
 * Created by 盖航_ on 2017/8/25.
 */

public class FansBean {


    /**
     * status : 200
     * result : [{"nickname":"盖大爷","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png","uid":"12","desc":"还有谁，这么牛逼"},{"nickname":"韩国思密达","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png","uid":"28","sex":"0","desc":"加油,2017"}]
     * count : 2
     * total : 2
     */

    private int status;
    private int count;
    private int total;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * nickname : 盖大爷
         * avatar : http://static.mayinews.com/Uploads/Picture/video_avatar.png
         * uid : 12
         * desc : 还有谁，这么牛逼
         * sex : 0
         */

        private String nickname;
        private String avatar;
        private String uid;
        private String desc;
        private String sex;

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
