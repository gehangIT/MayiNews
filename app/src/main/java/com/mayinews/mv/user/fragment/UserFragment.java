package com.mayinews.mv.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.mv.JNI;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.attention.activity.PersonVideosActivity;
import com.mayinews.mv.attention.bean.SubscribeLists;
import com.mayinews.mv.base.BaseFragment;
import com.mayinews.mv.discovery.activity.PublishActivity;
import com.mayinews.mv.user.activity.FansActivity;
import com.mayinews.mv.user.activity.LoginActivity;
import com.mayinews.mv.user.activity.PersonDataActivity;
import com.mayinews.mv.user.activity.SettingActivity;
import com.mayinews.mv.user.activity.SubActivity;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


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
    private LinearLayout ll_publishRecord;  //我的视频
    private LinearLayout ll_collectRecord;  // 收藏记录
    private LinearLayout ll_watchRecord;  //观看记录
    private ImageView iv_setting;
    private ImageView iv_gender;

    private LinearLayout ll_subscription;
    private LinearLayout ll_fans;
    private String loginStatues;  //登录状态
    private String token;

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
        ll_subscription.setOnClickListener(this);
        ll_fans.setOnClickListener(this);
        ll_publishRecord.setOnClickListener(this);

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


                if(!loginStatues.equals("1")){
                    // 跳转到登录页面
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else{
                    Intent subIntent = new Intent(getActivity(), SubActivity.class);
                    startActivity(subIntent);
                }


                break;
            case R.id.ll_fans:
                if(!loginStatues.equals("1")){
                    // 跳转到登录页面
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else{
                    //打开粉丝界面
                    Intent fansIntent = new Intent(getActivity(), FansActivity.class);
                    startActivity(fansIntent);
                }





                break;
            case  R.id.iv_setting:
                //打开设置页面
                 Intent intent=new Intent(getActivity(),SettingActivity.class);
                  startActivity(intent);
                break;

            case R.id.ll_publishVideo:
               //发布视频

                if(loginStatues.equals("1")){
                    Intent intent2=new Intent(getActivity(),PublishActivity.class);
                    startActivity(intent2);
                }else{


                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }


                break;
            case R.id.ll_publishRecord:

                if(!loginStatues.equals("1")){
                    // 跳转到登录页面
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else{
                    startPersonVideoActivity();
                }



                break;
        }
    }






    @Override
    public void onResume() {
        super.onResume();
        JNI jni = new JNI();
        String key = jni.getString();
        token = (String) SPUtils.get(getActivity(),key,"");
        Log.i("TAG","666");

        //得到登录的状态
         loginStatues= (String) SPUtils.get(getActivity(), MyApplication.LOGINSTATUES, "0");

        Log.i("TAG","登录状态="+loginStatues);
        if(!(loginStatues.equals("1"))){
            ivHead.setImageDrawable(getResources().getDrawable(R.drawable.default_icon));
            tv_userName.setText("点击登录");
            tv_signature.setText("登录之后更精彩");
            tv_subscribe.setText(0+"");
            tv_fans.setText(0+"");
        }else{

            String nickName= (String) SPUtils.get(getActivity(), MyApplication.NICKNAME, "");
            String gener= (String) SPUtils.get(getActivity(), MyApplication.GENDER, "");
            String signature= (String) SPUtils.get(getActivity(), MyApplication.SIGNATURE, "");
            String iconurl= (String) SPUtils.get(getActivity(), MyApplication.USERICON, "");
            if(!StringUtil.isEmpty(nickName)){

                tv_userName.setText(nickName);
            }
            if(gener.equals("0")){

                iv_gender.setImageResource(R.drawable.me_icon_men);
            }
            if(gener.equals("1")){

                iv_gender.setImageResource(R.drawable.me_icon_women);
            }
            if(!StringUtil.isEmpty(signature)){
                tv_signature.setText(signature);
            }else{
                tv_signature.setText("设置个性签名");
            }


            Bitmap bitmap = BitmapFactory.decodeFile(getActivity().getExternalCacheDir().getAbsolutePath()+"/head.jpeg");
            Log.i("TAG","bitmap"+bitmap);
            if(bitmap!=null){

                ivHead.setImageBitmap(bitmap);
            }else{
                if(!iconurl.equals("")){
                    Glide.with(getActivity()).load(buildGlideUrl(iconurl)).into(ivHead);
                }


            }



            getSubAndFans(token);


        }
    }

    private void getSubAndFans(String token) {
        //请求获取订阅数
        OkHttpUtils.get().url(Constants.GETSUBSCRIBE).addHeader("Authorization","Bearer "+token)
                         .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int total = jsonObject.optInt("total");
                    tv_subscribe.setText(total+"");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //请求获取粉丝数
        OkHttpUtils.get().url(Constants.GETFANS).addHeader("Authorization","Bearer "+token)
                         .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int total = jsonObject.optInt("total");
                    tv_fans.setText(total+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //打开个人视频界面
    private void startPersonVideoActivity() {
       String uid = (String) SPUtils.get(mContext,MyApplication.USERUID,"");
       String name = (String) SPUtils.get(mContext,MyApplication.NICKNAME,"");

        Intent intent=new Intent(getActivity(),PersonVideosActivity.class);

        intent.putExtra("uid",uid);
        intent.putExtra("nickName",name);
        Log.i("tag","uid="+uid);
        startActivity(intent);

    }
    @Override
    public void onHiddenChanged(boolean hidden) {

        if(!hidden){
            //再次请求订阅数和粉丝数
            getSubAndFans(token);
        }
    }

    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://www.mayinews.com").build());
        }
    }


}
