package com.mayinews.mv.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.base.BaseFragment;
import com.mayinews.mv.discovery.activity.PublishActivity;
import com.mayinews.mv.user.activity.LoginActivity;
import com.mayinews.mv.user.activity.PersonDataActivity;
import com.mayinews.mv.user.activity.SettingActivity;
import com.mayinews.mv.user.activity.SubActivity;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;


/**
 * Created by gary on 2017/6/14 0014.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class UserFragment extends BaseFragment implements View.OnClickListener {

    public static  boolean isLogin=true;//标记是否登录
    private static final int USERINFO = 1;
    private LinearLayout iv_login;//可以点击登录的LinearLayout

    private TextView tv_userName;  //用户名
    private TextView tv_signature;  //用户个性签名
    private ImageView ivHead;   //头像
    private TextView tv_subscribe;  //订阅的数量
    private TextView tv_fans;  //粉丝的数量
    private LinearLayout ll_publishVideo;  //发布视频
    private LinearLayout ll_publishRecord;  //发布记录
    private LinearLayout ll_collectRecord;  // 收藏记录
    private LinearLayout ll_watchRecord;  //观看记录
    private ImageView iv_setting;
    private ImageView iv_gender;

    private LinearLayout ll_subscription;
    private LinearLayout ll_fans;
    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_fragment, null, false);
        iv_login= (LinearLayout) view.findViewById(R.id.iv_login);
        ivHead= (ImageView) view.findViewById(R.id.profile_image);
        tv_userName = (TextView) view.findViewById(R.id.tv_userName);
        tv_subscribe = (TextView) view.findViewById(R.id.tv_sub);
        tv_fans = (TextView) view.findViewById(R.id.tv_fans);
        ll_publishVideo = (LinearLayout) view.findViewById(R.id.ll_publishVideo);
        ll_publishRecord = (LinearLayout) view.findViewById(R.id.ll_publishRecord);
        ll_collectRecord = (LinearLayout) view.findViewById(R.id.ll_collectRecord);
        ll_watchRecord = (LinearLayout) view.findViewById(R.id.ll_watchRecord);
        iv_gender = (ImageView) view.findViewById(R.id.iv_gender);
        tv_signature = (TextView) view.findViewById(R.id.tv_signature);
        ll_subscription = (LinearLayout) view.findViewById(R.id.ll_subscription);
        ll_fans = (LinearLayout) view.findViewById(R.id.ll_fans);

        //设置监听
        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到登录的状态
                String loginStatues= (String) SPUtils.get(getActivity(), MyApplication.LOGINSTATUES, "0");
                Log.i("TAG","登录状态="+loginStatues);
                if(!(loginStatues.equals("1"))){
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }else {
                    //进入个人资料页面
                    Intent intent=new Intent(getActivity(),PersonDataActivity.class);
                    startActivity(intent);


                }


            }
        });



        iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        ll_publishVideo.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_subscription:
                //打开订阅界面
                Intent subIntent = new Intent(getActivity(), SubActivity.class);

                break;
            case R.id.ll_fans:


                //打开粉丝界面

                break;
            case  R.id.iv_setting:
                //打开设置页面
                 Intent intent=new Intent(getActivity(),SettingActivity.class);
                  startActivity(intent);
                break;

            case R.id.ll_publishVideo:
               //发布视频
                Intent intent2=new Intent(getActivity(),PublishActivity.class);
                startActivity(intent2);
                break;
        }
    }






    @Override
    public void onResume() {
        super.onResume();
        //得到登录的状态
        String loginStatues= (String) SPUtils.get(getActivity(), MyApplication.LOGINSTATUES, "0");
        Log.i("TAG","登录状态="+loginStatues);
        if(!(loginStatues.equals("1"))){

            tv_userName.setText("点击登录");
            tv_signature.setText("登录之后更精彩");
            tv_subscribe.setText(0+"");
            tv_fans.setText(0+"");
        }else{

            String nickName= (String) SPUtils.get(getActivity(), MyApplication.NICKNAME, "");
            String gener= (String) SPUtils.get(getActivity(), MyApplication.GENDER, "");
            String signature= (String) SPUtils.get(getActivity(), MyApplication.SIGNATURE, "");
            if(!StringUtil.isEmpty(nickName)){

                tv_userName.setText(nickName);
            }else{
                String phoneNumber = (String) SPUtils.get(getActivity(),MyApplication.PHONENUMBER,"0");
                String substring = phoneNumber.substring(7);
                tv_userName.setText("用户"+substring);
            }
            if(gener.equals("0")){

                iv_gender.setImageResource(R.drawable.me_icon_men);
            }
            if(gener.equals("1")){

                iv_gender.setImageResource(R.drawable.me_icon_women);
            }
            if(signature!=null){
                tv_signature.setText(signature);
            }


            //设置头像
            Bitmap bitmap = BitmapFactory.decodeFile(getActivity().getExternalCacheDir().getAbsolutePath()+"/head.jpeg");
            Log.i("TAG","bitmap"+bitmap);
            if(bitmap!=null){

                ivHead.setImageBitmap(bitmap);
            }

        }
    }
}
