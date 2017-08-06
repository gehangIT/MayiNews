package com.mayinews.mv.attention.bean;

/**
 * Created by gary on 2017/6/22 0022.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class AuthorsDateBean {
    private String authorIconUrl;
    private String  authorName;

    public AuthorsDateBean(String authorIconUrl, String authorName) {
        this.authorIconUrl = authorIconUrl;
        this.authorName = authorName;
    }

    public String getAuthorIconUrl() {
        return authorIconUrl;
    }

    public void setAuthorIconUrl(String authorIconUrl) {
        this.authorIconUrl = authorIconUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
