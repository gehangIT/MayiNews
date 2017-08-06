package com.mayinews.mv.discovery.bean;

/**
 * Created by gary on 2017/6/24 0024.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class HotAuthorItemBean {

    private String imageUrl;
    private String authorName;
    private String authorSign;

    public HotAuthorItemBean(String imageUrl, String authorName, String authorSign) {
        this.imageUrl = imageUrl;
        this.authorName = authorName;
        this.authorSign = authorSign;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSign() {
        return authorSign;
    }

    public void setAuthorSign(String authorSign) {
        this.authorSign = authorSign;
    }
}
