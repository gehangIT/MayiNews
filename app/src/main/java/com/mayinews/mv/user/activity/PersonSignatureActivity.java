package com.mayinews.mv.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyun.common.utils.ToastUtil;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;

public class PersonSignatureActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_save;
    private EditText et_signature;
    private TextView font_conunt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_signature);
        initView();
        setOnListener();
        et_signature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                font_conunt.setText( s.length()+"");

            }
        });
    }

    private void setOnListener() {
        iv_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);

    }

    private void initView() {
        iv_back = (ImageView)findViewById(R.id.iv_back);
        tv_save = (TextView)findViewById(R.id.tv_save);
        et_signature = (EditText)findViewById(R.id.et_signature);
        font_conunt = (TextView)findViewById(R.id.font_conunt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();

                break;
            case R.id.tv_save :
                String s = et_signature.getText().toString();
                if(!StringUtil.isEmpty(s)){
                    //请求借口
                    SPUtils.put(this, MyApplication.SIGNATURE,s);

                    finish();

                }else{
                    ToastUtil.showToast(this,"签名不能为空");

                }


                break;
        }
    }
}
