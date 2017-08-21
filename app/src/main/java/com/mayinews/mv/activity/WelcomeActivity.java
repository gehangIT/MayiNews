package com.mayinews.mv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.MotionEvent;

import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.home.bean.LablesBean;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


public class WelcomeActivity extends Activity {
    private boolean isStartMain = false;
    Handler handler=new Handler();

    private final int MESSAGE_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


         //请求主分类的标签
        OkHttpUtils.get().url(Constants.VIDEOLABELS).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                String o = (String) SPUtils.get(WelcomeActivity.this, MyApplication.LABLES, "");

                int res = response.hashCode();
                if(o.equals("")){
                    SPUtils.put(WelcomeActivity.this,MyApplication.LABLES,response);
                }

                if(!(res==o.hashCode())){
                    SPUtils.put(WelcomeActivity.this,MyApplication.LABLES,response);
                }

            }
        });

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
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        //把所有的消息和回调移除
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
