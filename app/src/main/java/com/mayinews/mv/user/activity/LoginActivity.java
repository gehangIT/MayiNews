package com.mayinews.mv.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64DataException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.aliyun.common.utils.ToastUtil;
import com.mayinews.mv.JNI;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.user.fragment.UserFragment;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.NetUtils;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class LoginActivity extends Activity {
    private final int TIME = 1;
    private int recentTime = 59;
    private EditText et_phoneNumber;
    private TextView tv_login;
    private ImageView iv_login_closse;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    recentTime--;

                    if (recentTime > 0) {
                        tv_getAuthorCode.setText(recentTime + "秒");
                        handler.sendEmptyMessageDelayed(TIME, 1000);
                    } else {

                        tv_getAuthorCode.setEnabled(true);
                        handler.removeCallbacksAndMessages(null);
                        tv_getAuthorCode.setText("获取验证码");
                    }
                    break;
            }


        }
    };


    private TextView tv_getAuthorCode;  //获取验证码的textView
    private EditText et_auth_code;  //填写验证码editText
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_getAuthorCode = (TextView) findViewById(R.id.tv_getAuthorCode);
        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        tv_login = (TextView) findViewById(R.id.tv_login);
        iv_login_closse = (ImageView) findViewById(R.id.iv_login_closse);
        et_auth_code = (EditText) findViewById(R.id.et_auth_code);
        tv_getAuthorCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNumber = et_phoneNumber.getText().toString();
                Log.i("TAG", "phoneNumber=" + phoneNumber);
                if (!StringUtil.isEmpty(phoneNumber)) {
                    if (StringUtil.isMobile(phoneNumber)) {

                        if (NetUtils.isConnected(LoginActivity.this)) {

                            //发送消息
                            tv_getAuthorCode.setEnabled(false);
                            recentTime = 59;
                            tv_getAuthorCode.setText(recentTime + "秒");
                            handler.sendEmptyMessageDelayed(TIME, 1000);
                            /**
                             * 请求验证码
                             */
                            OkHttpUtils.post().url(Constants.GETAUTHORCODE).addParams("mobile", phoneNumber)
                                    .build().execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    //请求失败
                                    Log.i("TAG", "验证码请求失败" + e.getMessage());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    //请求成功
                                    Log.i("TAG", "验证码请求成功" + response);
                                    String substring = phoneNumber.substring(7);
                                    SPUtils.put(LoginActivity.this,MyApplication.PHONENUMBER,phoneNumber);
                                    ToastUtil.showToast(LoginActivity.this, "验证码已发送至尾号为"+substring+"的手机号");
                                }
                            });
                        } else {
                            ToastUtil.showToast(LoginActivity.this, "获取验证码失败");
                        }

                    } else {
                        ToastUtil.showToast(LoginActivity.this, "请输入正确的手机号");
                    }
                } else {
                    ToastUtil.showToast(LoginActivity.this, "手机号不能为空");
                }

            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = et_phoneNumber.getText().toString();   //获取手机号
                String number = et_auth_code.getText().toString();  //获取验证码
                if (!StringUtil.isEmpty(number)) {   //填写了验证码
                    if (!StringUtil.isEmpty(mobile)) {
                        if (StringUtil.isMobile(mobile)) {


                            //判断网络状态

                            if (NetUtils.isConnected(LoginActivity.this)) {


                                /**
                                 * 发送登录请求
                                 */
                                OkHttpUtils.post().url(Constants.LOGINREQUEST)
                                        .addParams("mobile", mobile)
                                        .addParams("number", number)
                                        .build().execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        /**
                                         * 请求失败
                                         */
                                        Log.i("TAG", "请求失败" + e.getMessage());
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        /**
                                         * 请求成功
                                         */
                                        Log.i("TAG", "请求结果" + response);
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            int status = jsonObject.optInt("status");
                                            if (status == 200) {
                                                /**
                                                 * 保存token
                                                 */
                                                ToastUtil.showToast(LoginActivity.this, "登录成功");
                                                //向sp中保存个字段，证明是登录状态.1代表登录，0代表未登录

                                                //获取token
                                                String token = jsonObject.optString("jwt");
                                                //从so文件获取key
                                                JNI jni = new JNI();
                                                String tokenKey = jni.getString();
                                                SPUtils.put(LoginActivity.this, tokenKey, token);

                                                /**
                                                 *   解析token的得到客户信息
                                                 */
                                                //1 截取token字符串，以 “，“分开
                                                String toeknString = token;
                                                Log.i("TAG", "toeknString==" + toeknString);

                                                String[] split = toeknString.split("\\.");
                                                //使用base64解析
                                                byte[] data = Base64.decode(split[1].toString(), Base64.DEFAULT);
                                                String dataJson = new String(data);
                                                JSONObject jsonObject1 = new JSONObject(dataJson);
                                                JSONObject userInfo = jsonObject1.optJSONObject("data");
                                                //解析得到userInfo的信息，并且返回
                                                String userid = userInfo.optString("userid");


                                                SPUtils.put(LoginActivity.this, MyApplication.USERUID, userid);

                                               //保存登录成功的字段
                                                SPUtils.put(LoginActivity.this,MyApplication.LOGINSTATUES,"1");
                                                //保存默认的昵称
                                                String phoneNumber = (String) SPUtils.get(LoginActivity.this,MyApplication.PHONENUMBER,"0");
                                                String substring = phoneNumber.substring(7);
                                                SPUtils.put(LoginActivity.this,MyApplication.NICKNAME,"用户"+substring);


                                                //请求头像
                                                getUserIcon(token);


                                                Log.i("TAG", "userid==" + userid);


                                            } else {

                                                handler.removeCallbacksAndMessages(null);
                                                tv_getAuthorCode.setText("获取验证码");
                                                tv_getAuthorCode.setEnabled(true);
                                                ToastUtil.showToast(LoginActivity.this, "系统错误,请稍后重试");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                            } else {


                                ToastUtil.showToast(LoginActivity.this, "获取验证码失败");
                            }

                        } else {
                            ToastUtil.showToast(LoginActivity.this, "请输入正确的手机号");
                        }
                    } else {
                        ToastUtil.showToast(LoginActivity.this, "请输入手机号");
                    }


                } else {
                    ToastUtil.showToast(LoginActivity.this, "请输入验证码");
                }
            }
        });

        iv_login_closse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //请求头像并且保存
    private void getUserIcon(String token) {

        OkHttpUtils.get().url(Constants.GETUSERICON).addHeader("authorization","Bearer "+"token")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                finish();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.optJSONObject("result");
                     avatar = result.optString("avatar");

                    SPUtils.put(LoginActivity.this,MyApplication.USERICON,avatar);
                   finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }





}
