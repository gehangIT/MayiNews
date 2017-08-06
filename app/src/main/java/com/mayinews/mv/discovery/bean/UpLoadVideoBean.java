package com.mayinews.mv.discovery.bean;

/**
 * Created by gary on 2017/7/11 0011.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class UpLoadVideoBean {
    private String name;
    private int size;

    public UpLoadVideoBean(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
