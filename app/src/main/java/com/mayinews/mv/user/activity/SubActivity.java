package com.mayinews.mv.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.mayinews.mv.R;

public class SubActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private LRecyclerView lrecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        initView();
        setOnListener();
    }



    private void initView() {
        iv_back = (ImageView)findViewById(R.id.iv_back);
        lrecyclerView = (LRecyclerView)findViewById(R.id.lrecyclerView);

    }
    private void setOnListener() {
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_back:
                     finish();
                    break;

            }
    }
}
