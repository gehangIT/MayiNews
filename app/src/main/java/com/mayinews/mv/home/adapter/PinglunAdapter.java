package com.mayinews.mv.home.adapter;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mayinews.mv.R;
import com.mayinews.mv.home.bean.CommentBean;
import com.mayinews.mv.home.bean.CommentLists;
import com.mayinews.mv.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gary on 2017/6/26 0026.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class PinglunAdapter extends BaseAdapter {
    private Context context;
    private List<CommentLists.ResultBean> result;


    public PinglunAdapter(Context context, List<CommentLists.ResultBean> result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public int getCount() {
        return (null!=result)?result.size():0;
    }

    @Override
    public Object getItem(int position) {
        return (null!=result)?result.get(position):"";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;


        CommentLists.ResultBean resultBean = result.get(position);

//
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pinglun, parent, false);
            viewHolder=new ViewHolder();
            viewHolder.authorIcon= (ImageView) convertView.findViewById(R.id.authorIcon);
            viewHolder.userName= (TextView) convertView.findViewById(R.id.authorName);
            viewHolder.userCreateTime= (TextView) convertView.findViewById(R.id.userCreateTime);
            viewHolder.userComment= (TextView) convertView.findViewById(R.id.authorComment);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if(resultBean.getAvatar().equals("")){
            Bitmap bitmap = BitmapFactory.decodeFile(context.getExternalCacheDir().getAbsolutePath() + "/head.jpeg");


            if(bitmap!=null){
                viewHolder.authorIcon.setImageBitmap(bitmap);
            }
        }else{
            Glide.with(context).load(buildGlideUrl(resultBean.getAvatar())).into(viewHolder.authorIcon);
        }



        viewHolder.userComment.setText(resultBean.getComment());
        viewHolder.userName.setText(resultBean.getNickname());
        viewHolder.userCreateTime.setText(resultBean.getCreate_time());
        return convertView;
    }

    class ViewHolder {
        ImageView authorIcon;
        TextView userName;
        TextView userCreateTime;
        TextView userComment;

    }
    private GlideUrl buildGlideUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Referer", "http://www.mayinews.com").build());
        }
    }
}
