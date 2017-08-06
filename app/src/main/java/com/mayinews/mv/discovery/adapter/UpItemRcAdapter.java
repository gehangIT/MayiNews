package com.mayinews.mv.discovery.adapter;

import android.content.Context;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayinews.mv.R;

/**
 * Created by gary on 2017/6/25 0025.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class UpItemRcAdapter extends RecyclerView.Adapter <UpItemRcAdapter.MyViewHolder>{
    private Context context;

    public UpItemRcAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_upauthor_recycler, parent, false);
        return new MyViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
          private Context context;

        public MyViewHolder(Context context,View itemView) {
            super(itemView);
            this.context=context;
        }


    }
}
