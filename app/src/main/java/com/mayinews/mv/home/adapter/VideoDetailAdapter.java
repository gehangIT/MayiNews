package com.mayinews.mv.home.adapter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.common.project.Text;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.mayinews.mv.JNI;
import com.mayinews.mv.MyApplication;
import com.mayinews.mv.R;
import com.mayinews.mv.discovery.view.MyListView;
import com.mayinews.mv.home.activity.NoskinVpActivity;
import com.mayinews.mv.home.bean.CommentBean;
import com.mayinews.mv.home.bean.CommentLists;
import com.mayinews.mv.home.bean.VideoLists;
import com.mayinews.mv.user.activity.LoginActivity;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.Number2Thousand;
import com.mayinews.mv.utils.SPUtils;
import com.mayinews.mv.utils.StringUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;


/**
 * Created by gary on 2017/6/26 0026.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class VideoDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private boolean isSubscribe=false;  //标记是否订阅
    private boolean isCollection=false; //标记是否收藏

    private Context context;
    private LayoutInflater inflater;
    private VideoLists.ResultBean dataBean;
    private EditText et_com;
    private List<VideoLists.ResultBean> guessLikeVideos; //猜你喜欢相关的视频
    private  List<CommentBean> commentBeans;  //评论集合
    private PinglunAdapter pinglunAdapter;   //评论listView的适配器
    private  List<CommentLists.ResultBean> result;// 评论

     /*
         一下定义Adapter的类型
      */

    public VideoDetailAdapter(Context context, VideoLists.ResultBean dataBean,List<VideoLists.ResultBean> guessLikeVideos,List<CommentLists.ResultBean> result) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataBean = dataBean;
        this.guessLikeVideos=guessLikeVideos;
        this.result=result;
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
            Log.i("tag","guessLikeVideos"+guessLikeVideos);
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
        CheckBox tv_atten;


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
            tv_atten = (CheckBox) itemView.findViewById(R.id.tv_atten);
            tv_atten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String status = (String) SPUtils.get(context, MyApplication.LOGINSTATUES, "0");
                    if(status.equals("1")){

                        //获取token
                        JNI jni = new JNI();
                        String key = jni.getString();
                        String token = (String) SPUtils.get(context,key,"");
                        //获取uid
                        String uid = (String) SPUtils.get(context, MyApplication.USERUID, "");
                        //希望订阅的用户id
                        String uid1 = dataBean.getUid();
                        //关注

                        OkHttpUtils.post().url(Constants.POSTSUBSCRIBE+uid+".html").addHeader("Authorization","Bearer "+ token)
                                .addParams("follow",uid1).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.i("TAG","error="+e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int status = jsonObject.optInt("status");
                                    Log.i("TAG","status="+status);
                                    if(status==200){
                                        if(!isSubscribe){
                                            tv_atten.setText("已订阅");

                                            isSubscribe=true;
                                        }else{
                                            tv_atten.setText("+订阅");
                                            Toast.makeText(context, "取消订阅成功", Toast.LENGTH_SHORT).show();
                                            isSubscribe=false;
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }else{

                        tv_atten.setChecked(false);
                        ((Activity)context).startActivity(new Intent(context,LoginActivity.class));

                    }
                }
            });




            videoCollection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

                    String status = (String) SPUtils.get(context, MyApplication.LOGINSTATUES, "0");

                    if(status.equals("1")){
                        //发送接口
                        JNI jni = new JNI();
                        String key = jni.getString();
                        String token = (String) SPUtils.get(context,key,"");
                        String uid = (String) SPUtils.get(context, MyApplication.USERUID, "");
                        OkHttpUtils.post().url(Constants.COLLECTIONVIDEO+uid+".html").addHeader("Authorization","Bearer "+token).addParams("vid",dataBean.getVid()).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int status = jsonObject.optInt("status");
                                    if(status==200){
                                        if(!isCollection){
                                            isCollection=true;
                                            Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                                        }else{
                                            isCollection=false;
                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }else{
                        videoCollection.setChecked(false);
                        ((Activity)context).startActivity(new Intent(context,LoginActivity.class));
                    }


                }
            });

        }

        //绑定数据
        public void setData() {

            videoTitle.setText(dataBean.getTitle());
            SimpleDateFormat sdf =   new SimpleDateFormat( "MM-dd HH:mm" );
            double v = Double.parseDouble(dataBean.getCreate_time())*1.0;
            String format = sdf.format(v);
            playTime.setText(format);
            playTimes.setText(Number2Thousand.number2Thousand(dataBean.getView()));
            tv_userName.setText(dataBean.getNickname());

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
                    ( (Activity)context).finish();
                    //进入播放视频的页面
                    VideoLists.ResultBean dataBean = guessLikeVideos.get(position);
                    Intent intent = new Intent(context, NoskinVpActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("dataBean", dataBean);
                    intent.putExtra("bundle", bundle);
                    context.startActivity(intent);
                    ((Activity)context).overridePendingTransition(R.anim.activity_open, R.anim.activity_backgroud);
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
        private RelativeLayout nocomments;

        public PinglunViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            listView = (MyListView) itemView.findViewById(R.id.pinglun_listView);
            nocomments = (RelativeLayout) itemView.findViewById(R.id.nocomments);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     //显示评论框

                    showPopwindows(result,position);
                }
            });
        }

        public void setData() {


             if(result!=null) {
                 nocomments.setVisibility(View.GONE);

                 pinglunAdapter = new PinglunAdapter(context, result);

                 listView.setAdapter(pinglunAdapter);
             }else{
                 //显示没有评论
                 nocomments.setVisibility(View.VISIBLE);
                 listView.setVisibility(View.GONE);
             }

        }

    }

    private void showPopwindows(final List<CommentLists.ResultBean> bean , final int position) {
        final String nickname = bean.get(position).getNickname();
        View inflate = View.inflate(context, R.layout.popup_comment, null);
        et_com = (EditText) inflate.findViewById(R.id.et_com);
        final TextView pop_fontCount = (TextView) inflate.findViewById(R.id.pop_fontCount);
        final TextView pop_cancle = (TextView) inflate.findViewById(R.id.pop_cancle);
        final TextView pop_publish = (TextView) inflate.findViewById(R.id.pop_publish);
        et_com.setHint("回复 :"+nickname);

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
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd HH:mm" );
                    String time = simpleDateFormat.format(new Date());
                    CommentLists.ResultBean bean1 = new CommentLists.ResultBean("",author + "回复" + nickname + ": "+comment, time, author);

                    bean.add(position+1,bean1);
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
