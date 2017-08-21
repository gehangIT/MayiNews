package com.mayinews.mv.home.adapter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.common.project.Text;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.discovery.view.MyListView;
import com.mayinews.mv.home.activity.NoskinVpActivity;
import com.mayinews.mv.home.bean.CommentBean;
import com.mayinews.mv.home.bean.VideoLists;
import com.mayinews.mv.utils.Number2Thousand;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gary on 2017/6/26 0026.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class VideoDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private VideoLists.ResultBean dataBean;
    private EditText et_com;
    private List<VideoLists.ResultBean> guessLikeVideos; //猜你喜欢相关的视频
    private  List<CommentBean> commentBeans;  //评论集合
    private PinglunAdapter pinglunAdapter;   //评论listView的适配器

     /*
         一下定义Adapter的类型
      */

    public VideoDetailAdapter(Context context, VideoLists.ResultBean dataBean,List<VideoLists.ResultBean> guessLikeVideos) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataBean = dataBean;
        this.guessLikeVideos=guessLikeVideos;
    }

    private static final int VIDEO_INFO = 0;  //视频的相关信息 包括作者信息
    //    private static final int  VIDEO_AUTHOR=1;  //视频的作者
    private static final int GUESS_LIKE = 1;  //猜你喜欢
    private static final int SPLENDID_COMMENT = 2;  //精彩的评论

    private int currentType;   //当前的item类型

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIDEO_INFO) {
            //创建视频信息的ViewHolder
            View view = inflater.inflate(R.layout.video_detail_info, parent, false);
            return new VideoInfoViewHolder(context, view);
        } else if (viewType == GUESS_LIKE) {

            View view = inflater.inflate(R.layout.guess_author_item, parent, false);
            return new GuessAuthorViewHolder(context, view);
        } else if (viewType == SPLENDID_COMMENT) {
            //精彩评论
            View view = inflater.inflate(R.layout.pinglun_author_item, parent, false);
            return new PinglunViewHolder(context, view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIDEO_INFO) {
            VideoInfoViewHolder vv = (VideoInfoViewHolder) holder;
            vv.setData();

        } else if (getItemViewType(position) == GUESS_LIKE) {

            GuessAuthorViewHolder gv = (GuessAuthorViewHolder) holder;
            gv.setData();
        } else if (getItemViewType(position) == SPLENDID_COMMENT) {

            PinglunViewHolder pv = (PinglunViewHolder) holder;
            pv.setData();
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case VIDEO_INFO:
                currentType = VIDEO_INFO;
                break;

            case GUESS_LIKE:
                currentType = GUESS_LIKE;
                break;
            case SPLENDID_COMMENT:
                currentType = SPLENDID_COMMENT;
                break;

        }
        return currentType;
    }


    class VideoInfoViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        TextView videoTitle;
        TextView playTime;
        TextView playTimes;
        CheckBox videoCollection;
        TextView videoShared;
        TextView videoDownload;
        TextView tv_userName;
        TextView tv_atten;


        public VideoInfoViewHolder(final Context context, View itemView) {
            super(itemView);
            this.context = context;
            videoTitle = (TextView) itemView.findViewById(R.id.videoTitle);
            playTime = (TextView) itemView.findViewById(R.id.play_time);
            playTimes = (TextView) itemView.findViewById(R.id.play_times);
            videoCollection = (CheckBox) itemView.findViewById(R.id.collection);
            videoShared = (TextView) itemView.findViewById(R.id.shared);
            videoDownload = (TextView) itemView.findViewById(R.id.download);
            tv_userName = (TextView) itemView.findViewById(R.id.tv_userName);
            tv_atten = (TextView) itemView.findViewById(R.id.tv_atten);
            tv_atten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "关注", Toast.LENGTH_SHORT).show();
                }
            });
            videoCollection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //发送接口
                }
            });

        }

        //绑定数据
        public void setData() {

            videoTitle.setText(dataBean.getTitle());
            playTimes.setText(Number2Thousand.number2Thousand(dataBean.getView()));
            tv_userName.setText(dataBean.getUser());

        }
    }


    class GuessAuthorViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private MyListView listview;

        public GuessAuthorViewHolder(final Context context, View itemView) {
            super(itemView);
            this.context = context;
            listview = (MyListView) itemView.findViewById(R.id.guess_listView);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //进入播放视频的页面
                    VideoLists.ResultBean dataBean = guessLikeVideos.get(position);
                    Intent intent = new Intent(context, NoskinVpActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("dataBean", dataBean);
                    intent.putExtra("bundle", bundle);
                    context.startActivity(intent);
                    ((Activity)context).overridePendingTransition(R.anim.activity_open, R.anim.activity_backgroud);
                    ((Activity)context).finish();
                }
            });
        }


        public void setData() {
            GuessAuthorAdapter adapter = new GuessAuthorAdapter(context,guessLikeVideos);
            listview.setAdapter(adapter);
        }
    }


    class PinglunViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private MyListView listView;

        public PinglunViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            listView = (MyListView) itemView.findViewById(R.id.pinglun_listView);

        }

        public void setData() {

//
            commentBeans = new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                CommentBean bean = new CommentBean("", "用户名" + i, "我是" + "用户名" + i, "这个视频也太搞笑了吧");
//                commentBeans.add(bean);
//            }
//
//            if(pinglunAdapter==null){
                pinglunAdapter = new PinglunAdapter(context, commentBeans);
//            }
            listView.setAdapter(pinglunAdapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    showPopwindows(commentBeans.get(position),position);
//                }
//            });
        }

    }

    private void showPopwindows(CommentBean bean ,int position) {
        View inflate = View.inflate(context, R.layout.popup_comment, null);
        et_com = (EditText) inflate.findViewById(R.id.et_com);
        final TextView pop_fontCount = (TextView) inflate.findViewById(R.id.pop_fontCount);
        TextView pop_cancle = (TextView) inflate.findViewById(R.id.pop_cancle);
        final TextView pop_publish = (TextView) inflate.findViewById(R.id.pop_publish);
        et_com.setHint("回复 :"+bean.getUserName());

        popupInputMethodWindow(); //显示输入法
        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(inflate);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        backgroundAlpha(0.5f);//当pop弹出时改变窗口的背景色
        popupWindow.setBackgroundDrawable((new BitmapDrawable()));
        View view = View.inflate(context, R.layout.activity_noskin_vp, null);
        //让popupwindow不被输入法隐藏
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
   popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
       @Override
       public void onDismiss() {
           backgroundAlpha(1);
       }
   });
        pop_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布回复
                String comment = et_com.getText().toString();
                if (!StringUtil.isEmpty(comment)) {
                    String author = (String) SPUtils.get(context, MyApplication.NICKNAME, "");
                    String sign = (String) SPUtils.get(context, MyApplication.SIGNATURE, "");
                    CommentBean commentBean = new CommentBean("", author, sign, comment);
                    commentBeans.add(commentBean);
                    pinglunAdapter.notifyDataSetChanged();
                    popupWindow.dismiss();

                } else {
                    Toast.makeText(context, "请输入你回复的内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_com.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {

                    pop_publish.setTextColor(context.getResources().getColor(R.color.yellow_publish));
                    pop_fontCount.setText(s.length() + "");
                } else {
                    pop_publish.setTextColor(context.getResources().getColor(R.color.gray_9a9a9a));
                }
            }
        });

    }

    /**
     * 弹出输入法窗口
     */
    private void popupInputMethodWindow() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputMethodManager systemService = (InputMethodManager) et_com.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
                systemService.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }).start();
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
              lp.alpha = bgAlpha; //0.0-1.0
        ((Activity)context).getWindow().setAttributes(lp);
    }

}
