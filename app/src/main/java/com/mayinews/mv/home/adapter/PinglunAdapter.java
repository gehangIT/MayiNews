package com.mayinews.mv.home.adapter;

import android.app.Service;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mayinews.mv.R;
import com.mayinews.mv.home.bean.CommentBean;
import com.mayinews.mv.utils.StringUtil;

import java.util.List;

/**
 * Created by gary on 2017/6/26 0026.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class PinglunAdapter extends BaseAdapter {
    private Context context;
    private List<CommentBean> commentBeens;


    public PinglunAdapter(Context context, List<CommentBean> commentBeens) {
        this.context = context;
        this.commentBeens = commentBeens;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
////        CommentBean commentBean = commentBeens.get(position);
//
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pinglun, parent, false);
//            viewHolder=new ViewHolder();
//            viewHolder.userName= (TextView) convertView.findViewById(R.id.authorName);
//            viewHolder.userSign= (TextView) convertView.findViewById(R.id.authorSign);
//            viewHolder.userComment= (TextView) convertView.findViewById(R.id.authorComment);
//            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.userName.setText(commentBean.getUserName());
//        viewHolder.userSign.setText(commentBean.getUserSignature());
//        viewHolder.userComment.setText(commentBean.getUserComment());

        return convertView;
    }

    class ViewHolder {
        TextView userName;
        TextView userSign;
        TextView userComment;

    }

}
