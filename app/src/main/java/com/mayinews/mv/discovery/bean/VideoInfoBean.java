package com.mayinews.mv.discovery.bean;

import java.util.List;

/**
 * Created by gary on 2017/7/11 0011.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class VideoInfoBean {

    public VideoInfoBean(List<DataBean> data) {
        this.data = data;
    }

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        public DataBean(String name, long size) {
            this.name = name;
            this.size = size;
        }

        /**
         * name : VID_20170705_103915.3gp
         * size : 683193
         */

        private String name;
        private long size;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
