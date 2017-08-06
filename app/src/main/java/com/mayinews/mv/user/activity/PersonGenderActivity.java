package com.mayinews.mv.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonGenderActivity extends AppCompatActivity {
 private String gender="0"; //0代表男，1代表女
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.iv_selected_male)
    ImageView ivSelectedMale;
    @BindView(R.id.rl_gener_male)
    RelativeLayout rlGenerMale;
    @BindView(R.id.iv_selected_female)
    ImageView ivSelectedFemale;
    @BindView(R.id.rl_gener_female)
    RelativeLayout rlGenerFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_gender);
        ButterKnife.bind(this);
        Intent intent = getIntent();


    }

    @OnClick({R.id.tv_back,R.id.tv_save,R.id.rl_gener_male,R.id.rl_gener_female})
    public void OnclickEvent(View v){
       switch (v.getId()){
           case R.id.tv_back:
               finish();
               break;
           case R.id.tv_save:
               //请求网络 保存性别
               SPUtils.put(this,MyApplication.GENDER,gender);
               finish();
               break;
           case R.id.rl_gener_male:
               //指定性别为男
               gender="0";
               ivSelectedMale.setVisibility(View.VISIBLE);
               ivSelectedFemale.setVisibility(View.GONE);
               break;
           case R.id.rl_gener_female:
               //指定性别为女
               gender="1";
               ivSelectedMale.setVisibility(View.GONE);
               ivSelectedFemale.setVisibility(View.VISIBLE);
               break;
       }

    }

    @Override
    protected void onResume() {
        super.onResume();
        String  gender = (String) SPUtils.get(this, MyApplication.GENDER, "");
        if(gender.equals("0")){
            ivSelectedMale.setVisibility(View.VISIBLE);
            ivSelectedFemale.setVisibility(View.GONE);
        }
        if(gender.equals("1")){
            ivSelectedMale.setVisibility(View.GONE);
            ivSelectedFemale.setVisibility(View.VISIBLE);
        }
    }
}
