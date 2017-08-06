package com.mayinews.mv.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.common.utils.ToastUtil;
import com.mayinews.mv.R;
import com.mayinews.mv.user.fragment.UserFragment;
import com.mayinews.mv.utils.DataCleanManager;
import com.mayinews.mv.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingActivity extends Activity {

    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_aboutWe)
    LinearLayout tvAboutWe;
    @BindView(R.id.tv_cacheSize)
    TextView tvCacheSize;
    @BindView(R.id.tv_clearCache)
    LinearLayout tvClearCache;
    @BindView(R.id.tv_shared)
    TextView tvShared;
    @BindView(R.id.tv_versionNumber)
    TextView tvVersionNumber;
    @BindView(R.id.tv_loginOrLogout)
    TextView tvLoginOrLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        if(UserFragment.isLogin){

            tvLoginOrLogout.setText("注销");

        }else{

            tvLoginOrLogout.setText("登录");
        }

        //获取app的缓存大小
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            Log.i("TAG", "缓存大小为" + totalCacheSize);
            tvCacheSize.setText(totalCacheSize); //设置缓存大小
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //点击事件
    @OnClick({R.id.tv_aboutWe, R.id.tv_back, R.id.tv_cacheSize, R.id.tv_clearCache,
            R.id.tv_loginOrLogout, R.id.tv_shared})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_back:

                finish();
                break;
            case R.id.tv_aboutWe:

                break;
            case  R.id.tv_cacheSize:

                break;
            case R.id.tv_clearCache:   //清理缓存
              DataCleanManager.clearAllCache(this);
                tvCacheSize.setText("0M");
                ToastUtil.showToast(this,"成功清理缓存");
                break;
            case R.id.tv_loginOrLogout:
                 if(UserFragment.isLogin){
                    //注销
                     //清除SP，和数据，到登录界面
                     SPUtils.clear(this);
                     finish();
                     UserFragment.isLogin=false;

                 }else{
                     //登录  跳转到登录界面
                     Intent intent=new Intent(this,LoginActivity.class);
                     startActivity(intent);


                 }
                break;
            case R.id.tv_shared:

                break;



        }


    }

}
