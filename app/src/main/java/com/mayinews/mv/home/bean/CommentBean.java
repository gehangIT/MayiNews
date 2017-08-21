package com.mayinews.mv.home.bean;

/**
 * Created by 盖航_ on 2017/8/17.
 */

public class CommentBean {
    private String userIconUri;//用户头像
    private String userName;   //用户名字
    private String userSignature;  //用户签名
    private String userComment;//用户评论

    public CommentBean(String userIconUri, String userName, String userSignature, String userComment) {
        this.userIconUri = userIconUri;
        this.userName = userName;
        this.userSignature = userSignature;
        this.userComment = userComment;
    }

    public String getUserIconUri() {
        return userIconUri;
    }

    public void setUserIconUri(String userIconUri) {
        this.userIconUri = userIconUri;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
