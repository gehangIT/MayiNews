package com.mayinews.mv.home.bean;

/**
 * Created by gary on 2017/6/15 0015.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class NewestMediaItem {
    private String url;   //图片的URL
    private String titleName;   //视频的标题
    private int width;

    private int height;

    public NewestMediaItem(String url, String titleName,int width,int height) {
        this.url = url;
        this.titleName = titleName;
        this.width=width;
        this.height=height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
