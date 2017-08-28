package com.mayinews.mv.utils;

/**
 * Created by gary on 2017/6/15 0015T.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class Constants {

    public static final String VIDEO_LIST = "http://mv.mayinews.com/api/lists";
    public static final String VIDEOLABELS = "http://mv.mayinews.com/api/getchannels.html";

    public static final String NET_URL = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
    //     public static final String  VIDEO_LIST="http://mv.mayinews.com/api/lists";  //原始的视频列表
    public static final String GETPLAYAUTH = "http://mv.mayinews.com/api/getvideoplayauth/vid/";
    public static final String GETUPLOADAUTH = "http://mv.mayinews.com/api/createuploadvideo.html";
    public static final String GETAUTHORCODE = "http://mv.mayinews.com/api/sendsms/";//获取验证码
    public static final String LOGINREQUEST = "http://mv.mayinews.com/api/login/";//登录请求
    public static final String PULLUPEQUEST = "http://mv.mayinews.com/api/pullup/qid/0/max/20/tag/";//上拉加载更多视频请求
    public static final String REFRESHEQUEST = "http://mv.mayinews.com/api/refresh/qid/1000/tag/0.html";//刷新视频请求
    public static final String PULLDOWNHEQUEST = "http://mv.mayinews.com/api/pulldown/qid/1000/tag/0.html";//下拉刷新视频请求
    public static final String GETCOMMENT = "http://mv.mayinews.com/api/comment/max/20/vid/";
    public static final String COLLECTIONVIDEO = "http://mv.mayinews.com/api/favor/uid/";  //收藏视频
    public static final String GETSUBSCRIBE = "http://mv.mayinews.com/api/follow/type/following/max/20/page/";   //获取订阅作者
    public static final String GETFANS = "http://mv.mayinews.com/api/follow/type/followers/max/20/page/";   //获取粉丝
    public static final String POSTCOMMENTS = "http://mv.mayinews.com/api/comment/vid/";  //提交评论
    public static final String GETSUBSCRIBELISTS = "http://mv.mayinews.com/api/getfollowlist";  // 推荐的作者列表
    public static final String GETUSERVIDEO ="http://mv.mayinews.com/api/getuservideo/max/12/uid/" ;  //获取指定用户的视频列表
    public static final String UPDOWNUSERICON = "http://mv.mayinews.com/api/setuseravatar.html"; //上传用户头小南国
    public static final String SETNICKNAME ="http://mv.mayinews.com/api/setnickname.html" ;
    public static final String SETSIGN = "http://mv.mayinews.com/api/setuserdesc/.html";  //设置签名
    public static final String GETUSERICON = "http://mv.mayinews.com/api/getuserinfo.html";  //获取头像
    public static String POSTSUBSCRIBE = "http://mv.mayinews.com/api/follow/type/followers/uid/";    //订阅
}

