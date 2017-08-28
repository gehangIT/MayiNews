package com.mayinews.mv.attention.bean;

import java.util.List;

/**
 * Created by 盖航_ on 2017/8/24.
 */

public class SubscribeLists {


    /**
     * status : 200
     * count : 18
     * result : [{"id":"21","username":"我的爱豆","nickname":"我的爱豆","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"27","username":"小仙女爱追剧","nickname":"小仙女爱追剧","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"26","username":"跟我看世界","nickname":"跟我看世界","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"20","username":"我爱拉斯","nickname":"我爱拉斯","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"14","username":"爆笑小母猪","nickname":"爆笑小母猪","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"18","username":"我们一起看综艺","nickname":"我们一起看综艺","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"22","username":"糗百大调查","nickname":"糗百大调查","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"29","username":"知识搬运工","nickname":"知识搬运工","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"24","username":"相信科学","nickname":"相信科学","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"19","username":"我们都爱听音乐","nickname":"我们都爱听音乐","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"13","username":"深夜食堂","nickname":"深夜食堂","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"16","username":"铲屎达人","nickname":"铲屎达人","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"15","username":"长歌是大腿","nickname":"长歌是大腿","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"25","username":"笑出双下巴","nickname":"笑出双下巴","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"23","username":"一禅小和尚","nickname":"一禅小和尚","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"28","username":"韩国思密达","nickname":"韩国思密达","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"17","username":"舞林萌主","nickname":"舞林萌主","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"},{"id":"30","username":"震撼视觉冲击力","nickname":"震撼视觉冲击力","avatar":"http://static.mayinews.com/Uploads/Picture/video_avatar.png"}]
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
         * id : 21
         * username : 我的爱豆
         * nickname : 我的爱豆
         * avatar : http://static.mayinews.com/Uploads/Picture/video_avatar.png
         */

        private String uid;
        private String nickname;
        private String desc;
        private String avatar;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }


        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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
