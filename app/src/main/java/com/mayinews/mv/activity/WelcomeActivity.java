package com.mayinews.mv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.alibaba.fastjson.JSON;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.home.bean.LablesBean;
import com.mayinews.mv.home.bean.VideoTag;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;


public class WelcomeActivity extends Activity {
    private boolean isStartMain = false;
    Handler handler=new Handler();

    private final int MESSAGE_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);




        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //两秒后才执行到这里
                //执行在主线程中
                startMainActivity();

            }
        }, 2000);



    }


    /**
     * 跳转到主页面，并且把当前页面关闭掉
     */
    private void startMainActivity() {
        if(!isStartMain){
            isStartMain = true;
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            //关闭当前页面
            finish();
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        startMainActivity();
        return true;
    }

    @Override
    protected void onDestroy() {
        //把所有的消息和回调移除
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
