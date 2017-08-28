package com.mayinews.mv;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import android.util.Log;

import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.mayinews.mv.home.bean.LablesBean;
import com.mayinews.mv.user.activity.LoginActivity;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gary on 2017/6/23 0023.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class MyApplication extends  Application{

    public static  final String  NICKNAME= "nickName";//用户名的昵称
    public static final String GENDER = "gender";   //用户名的性别  ,0男，1 女
    public static final String LOGINSTATUES = "loginstatues";
    public static final String SIGNATURE = "signature";  //用户的签名
    public static final String USERICON = "usericon";  //用户的头像地址
    public static final String LABLES = "lables";  //用户的头像地址
    public static final String USERUID = "userUid";
    public static final String PHONENUMBER = "phoneNumber";
    public static final String VIDEOTAGSJSON = "videoTagsJson";


    @Override
    public void onCreate() {
        super.onCreate();
        System.loadLibrary("QuCore-ThirdParty");
        System.loadLibrary("QuCore");
        QupaiHttpFinal.getInstance().initOkHttpFinal();
//设置保存密码。此密码如果更换，则之前保存的视频无法播放
        AliyunDownloadManager.getInstance(this).setDownloadPassword("123456789");
        //设置保存路径。请确保有SD卡访问权限。
        AliyunDownloadManager.getInstance(this).setDownloadPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save");

            Log.i("TAG","app签名="+ StringUtil.getSign(getApplicationContext()));
            Log.i("TAG","UID="+ SPUtils.get(this,MyApplication.USERUID,"123"));


    }


}
