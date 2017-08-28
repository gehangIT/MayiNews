package com.mayinews.mv.home.bean;

import java.util.List;

/**
 * Created by 盖航_ on 2017/8/23.
 * 获取视频分类
 */

public class VideoTag {


    /**
     * status : 1
     * result : [{"tid":"89","name":"萌娃萌宠","level":"0","num":"0"},{"tid":"101","name":"搞笑","level":"0","num":"0"},{"tid":"102","name":"现场","level":"0","num":"0"},{"tid":"103","name":"福利","level":"0","num":"0"},{"tid":"105","name":"生活","level":"0","num":"0"},{"tid":"106","name":"影视","level":"0","num":"0"},{"tid":"108","name":"美食","level":"0","num":"0"},{"tid":"109","name":"音乐","level":"0","num":"0"},{"tid":"110","name":"游戏","level":"0","num":"0"},{"tid":"111","name":"二次元","level":"0","num":"0"},{"tid":"116","name":"舞蹈","level":"0","num":"0"},{"tid":"129","name":"涨姿势","level":"0","num":"0"},{"tid":"130","name":"明星综艺","level":"0","num":"0"}]
     */

    private int status;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * tid : 89
         * name : 萌娃萌宠
         * level : 0
         * num : 0
         */

        private String tid;
        private String name;
        private String level;
        private String num;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
