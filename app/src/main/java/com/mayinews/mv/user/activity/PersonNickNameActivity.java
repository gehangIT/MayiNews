package com.mayinews.mv.user.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.mayinews.mv.JNI;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;
import com.mayinews.mv.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class PersonNickNameActivity extends AppCompatActivity implements View.OnClickListener {


   private EditText et_nickName;
    private ImageView iv_clear;
    private ImageView iv_back;
    private TextView tv_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_nick_name);
        initView();
        setOnListener();
    }

    private void setOnListener() {
        iv_back.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
        tv_save.setOnClickListener(this);

    }

    private void initView() {
        iv_back = (ImageView)findViewById(R.id.iv_back);
        et_nickName = (EditText) findViewById(R.id.et_nickName);
        iv_clear = (ImageView)findViewById(R.id.iv_clear);
        tv_save = (TextView)findViewById(R.id.tv_save);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String nickName= (String) SPUtils.get(this, MyApplication.NICKNAME, "");
        if(!StringUtil.isEmpty(nickName)){

            et_nickName.setText(nickName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.tv_save:
                final String s = et_nickName.getText().toString();
                if(!StringUtil.isEmpty(s)){
                    String uid = (String) SPUtils.get(this, MyApplication.USERUID, "");
                    String key = new JNI().getString();
                    String token = (String) SPUtils.get(this,key, "");
                    //请求借口保存
                    OkHttpUtils.post().url(Constants.SETNICKNAME)
                            .addHeader("authorization","Bearer "+token).addParams("nickname",s)
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            finish();
                            Toast.makeText(PersonNickNameActivity.this, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status = jsonObject.optInt("status");
                                if(status==200){
                                    SPUtils.put(PersonNickNameActivity.this,MyApplication.NICKNAME,s);
                                    Toast.makeText(PersonNickNameActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });


                }else{
                    //
                    Toast.makeText(PersonNickNameActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_clear:

                et_nickName.setText("");
                break;
            case R.id.iv_back:

                finish();
                break;
        }
    }
}
