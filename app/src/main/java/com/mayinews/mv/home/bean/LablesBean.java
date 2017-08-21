package com.mayinews.mv.home.bean;

/**
 * Created by Administrator on 2017/8/11.
 */

public class LablesBean {


    /**
     * level : 0
     * name : 萌娃萌宠
     * num : 0
     * tid : 89
     */

    private String level;
    private String name;
    private String num;
    private String tid;

    public LablesBean(String level, String name, String num, String tid) {
        this.level = level;
        this.name = name;
        this.num = num;
        this.tid = tid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

}
