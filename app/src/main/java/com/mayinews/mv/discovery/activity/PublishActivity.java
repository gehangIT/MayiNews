package com.mayinews.mv.discovery.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.aliyun.common.utils.StorageUtils;
import com.aliyun.common.utils.ToastUtil;
import com.aliyun.demo.crop.AliyunVideoCrop;
import com.aliyun.demo.recorder.AliyunVideoRecorder;
import com.aliyun.struct.common.VideoQuality;
import com.aliyun.struct.recorder.CameraType;
import com.aliyun.struct.recorder.FlashType;
import com.aliyun.struct.snap.AliyunSnapVideoParam;
import com.mayinews.mv.R;
import com.mayinews.mv.discovery.bean.UpLoadAuthInfo;
import com.mayinews.mv.discovery.bean.VideoInfoBean;
import com.mayinews.mv.utils.Constants;
import com.mayinews.mv.utils.LogUtils;
import com.mayinews.mv.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class PublishActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_VIDEO_CODE = 3;

    private static final int REQUEST_RECORD = 1;
    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private String bucket = "";
    private static final int REQUEST_CROP = 2;
    private ImageView back;
    private TextView affirm_Publish;
    private EditText title;
    private ImageView add;
    private LinearLayout parent;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    AliyunSnapVideoParam videoParam;
    private View view;
    private String[] eff_dirs;
    VODUploadClient uploader;
    private List<VideoInfoBean.DataBean> datas = new ArrayList<>();
    private long Videosize;//上传选择视频的大小
    //    private String Videoname;
    String VideoName;//上传视频的名字
    private String uploadAddress;
    private String uploadAuth;
    private RelativeLayout add_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        // 打开日志。
        LogUtils.enableLog();
        initView();
        initAssetPath();
        initParams();
        copyAssets();
        setListener();

         uploader = new VODUploadClientImpl(getApplicationContext());
        VODUploadCallback callback = new VODUploadCallback() {
            @Override
            public void onUploadSucceed(UploadFileInfo info) {
                Log.i("TAG", "上传成功");
                finish();
            }

            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {

            }

            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {

            }

            @Override
            public void onUploadTokenExpired() {
                // 实现时，从新获取UploadAuth
                OkHttpUtils
                        .post()
                        .url(Constants.GETUPLOADAUTH)
                        .addParams("data[0][name]", VideoName + ".mp4")
                        .addParams("data[0][size]", String.valueOf(Videosize))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.i("TAG", "请求上传出错=" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.i("TAG", "请求上传response=" + response);
                                List<UpLoadAuthInfo> upLoadAuthInfo = JSON.parseArray(response, UpLoadAuthInfo.class);
                                UpLoadAuthInfo authInfo = upLoadAuthInfo.get(0);
                                uploadAddress = authInfo.getUploadAddress();
                                uploadAuth = authInfo.getUploadAuth();
                                Log.i("TAG", "uploadAddress=" + uploadAddress + "uploadAddress=" + uploadAddress);
                                uploader.resumeWithToken(uploadAuth, "", "", "");
                            }
                        });
            }

            @Override
            public void onUploadRetry(String code, String message) {

            }

            @Override
            public void onUploadRetryResume() {

            }

            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                Log.i("TAG", "uploadFileInfo=" + uploadFileInfo);
                Log.i("TAG", "file path:" + uploadFileInfo.getFilePath() +
                        ", endpoint: " + uploadFileInfo.getEndpoint() +
                        ", bucket:" + uploadFileInfo.getBucket() +
                        ", object:" + uploadFileInfo.getObject() +
                        ", status:" + uploadFileInfo.getStatus());
                uploader.setUploadAuthAndAddress(uploadFileInfo, uploadAuth, uploadAddress);
            }




        };

        uploader.init(callback);

    }
    private void setListener() {

        affirm_Publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoName=title.getText().toString();
                if(!(uploader.listFiles().size()>0)){

                    Toast.makeText(PublishActivity.this,"请添加上传的视频文件",Toast.LENGTH_SHORT).show();



                }else if( null==VideoName  ||  VideoName.trim().equals("")){
                    Toast.makeText(PublishActivity.this,"上传视频的标题不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    OkHttpUtils
                            .post()
                            .url(Constants.GETUPLOADAUTH)
                            .addParams("data[0][name]", VideoName+".mp4")
                            .addParams("data[0][size]", String.valueOf(Videosize))
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.i("TAG","请求上传出错="+e.getMessage());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.i("TAG","请求上传response="+response);
                                    List<UpLoadAuthInfo> upLoadAuthInfo = JSON.parseArray(response, UpLoadAuthInfo.class);
                                    UpLoadAuthInfo authInfo = upLoadAuthInfo.get(0);
                                    uploadAddress = authInfo.getUploadAddress();
                                    uploadAuth = authInfo.getUploadAuth();
                                    Log.i("TAG","uploadAddress="+uploadAddress+"uploadAddress="+uploadAddress);
                                    uploader.start();
                                }
                            });


                }





            }
        });
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if(s.length()>15) {
                   Toast.makeText(PublishActivity.this, "标题做多20个字符!", Toast.LENGTH_SHORT).show();
               }
            }
        });
        add_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示popwindo ws
                showAlertDialog();


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void initParams() {



        videoParam = new AliyunSnapVideoParam.Builder()
                .setResulutionMode(AliyunSnapVideoParam.RESOLUTION_720P)
                .setRatioMode(AliyunSnapVideoParam.RATIO_MODE_9_16)
                .setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)
                .setFilterList(eff_dirs)
                .setBeautyLevel(80)
                .setBeautyStatus(true)
                .setCameraType(CameraType.BACK)
                .setFlashType(FlashType.AUTO)
                .setNeedClip(true)
                .setMaxDuration(60000*10)
                .setMinDuration(10000)
                .setVideQuality(VideoQuality.SSD)
                .setGop(5)


                .build();



    }

    //设置滤镜列表
    private void initAssetPath(){
        String path = StorageUtils.getCacheDirectory(this).getAbsolutePath() + File.separator+ Utils.QU_NAME + File.separator;
        eff_dirs = new String[]{
                null,
                path + "filter/chihuang",
                path + "filter/fentao",
                path + "filter/hailan",
                path + "filter/hongrun",
                path + "filter/huibai",
                path + "filter/jingdian",
                path + "filter/maicha",
                path + "filter/nonglie",
                path + "filter/rourou",
                path + "filter/shanyao",
                path + "filter/xianguo",
                path + "filter/xueli",
                path + "filter/yangguang",
                path + "filter/youya",
                path + "filter/zhaoyang"
        };
    }

    private void copyAssets() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                Utils.copyAll(getApplicationContext());
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
//                startRecordTxt.setEnabled(true);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private void showAlertDialog() {

         if(builder==null){
             builder=new AlertDialog.Builder(PublishActivity.this);
             view=LayoutInflater.from(PublishActivity.this).inflate(R.layout.alertdialog,null);
             dialog=builder.create();
             Window window = dialog.getWindow();
             dialog.setView(view);
             window.setWindowAnimations(R.style.dialog_style);

             window.setGravity(Gravity.BOTTOM);//底部出现
             window.setBackgroundDrawableResource(android.R.color.transparent);

         }

         if(dialog.isShowing()){
             dialog.dismiss();
         }else{
             dialog.show();
             WindowManager windowManager = getWindowManager();
             Display display = windowManager.getDefaultDisplay();
             WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
             lp.width = (int)(display.getWidth()); //设置宽度
             dialog.getWindow().setAttributes(lp);
         }

        TextView chose_picture = (TextView) view.findViewById(R.id.chose_picture);
        TextView record = (TextView) view.findViewById(R.id.record);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        chose_picture.setOnClickListener(this);
        record.setOnClickListener(this);
        cancle.setOnClickListener(this);

    }

    private void initView() {
        back= (ImageView) findViewById(R.id.back);
        affirm_Publish = (TextView) findViewById(R.id.affirm_Publish);
        title = (EditText)findViewById(R.id.title);
        add = (ImageView) findViewById(R.id.add);
        parent = (LinearLayout)findViewById(R.id.parent);
        add_rl = (RelativeLayout)findViewById(R.id.add_rl);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.chose_picture:{
                Toast.makeText(this,"chose_picture",Toast.LENGTH_SHORT).show();
                //调用相册

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, REQUEST_VIDEO_CODE);
                dialog.dismiss();
            }
            break;
            case R.id.record:{
                Toast.makeText(this,"record",Toast.LENGTH_SHORT).show();
                //拉起录制
                AliyunVideoRecorder.startRecordForResult(PublishActivity.this,REQUEST_RECORD,videoParam);
                dialog.dismiss();
            }
            break;
            case R.id.cancle:{
                Toast.makeText(this,"cancle",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            break;
        }
    }
    //录制完成的监听回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==  REQUEST_RECORD){
            if(resultCode == Activity.RESULT_OK && data!= null){
                int type = data.getIntExtra(AliyunVideoRecorder.RESULT_TYPE,0);
                if(type ==  AliyunVideoRecorder.RESULT_TYPE_CROP){
                    String path = data.getStringExtra(AliyunVideoCrop.RESULT_KEY_CROP_PATH);
                    Toast.makeText(this,"文件路径为 "+ path + " 时长为 " +
                            data.getLongExtra(AliyunVideoCrop.RESULT_KEY_DURATION,0),Toast.LENGTH_SHORT).show();
                    if(path.endsWith(".3gp") || path.endsWith(".mp4")) {
                        Bitmap bitmap = getVideoThumbnail(path);

//                    add.setImageBitmap(bitmap);
                        add.setVisibility(View.GONE);
                        add_rl.setBackground(new BitmapDrawable(bitmap));
                        uploader.clearFiles();   //先清除上传列表，因为是单个上传
                        uploader.addFile(path, getVodInfo());
                    }else{

                        ToastUtil.showToast(this,"请选择正确的视频文件");
                    }
                }else if(type ==  AliyunVideoRecorder.RESULT_TYPE_RECORD){
                    Bitmap bitmap = getVideoThumbnail(data.getStringExtra(AliyunVideoRecorder.OUTPUT_PATH));

//                    add.setImageBitmap(bitmap);
                    add.setVisibility(View.GONE);
                    add_rl.setBackground(new BitmapDrawable(bitmap));
                   String FilePath=data.getStringExtra(AliyunVideoRecorder.OUTPUT_PATH);

                    uploader.clearFiles();   //先清除上传列表，因为是单个上传
                    uploader.addFile(FilePath,getVodInfo());
                    Toast.makeText(this,"文件路径为 "+
                            data.getStringExtra(AliyunVideoRecorder.OUTPUT_PATH),Toast.LENGTH_SHORT).show();
                    File file = new File(FilePath);
                    Videosize = file.length();
                }
            }else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"用户取消录制",Toast.LENGTH_SHORT).show();
            }
        }

        if(requestCode==REQUEST_VIDEO_CODE){
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                /** 数据库查询操作。
                 * 第一个参数 uri：为要查询的数据库+表的名称。
                 * 第二个参数 projection ： 要查询的列。
                 * 第三个参数 selection ： 查询的条件，相当于SQL where。
                 * 第三个参数 selectionArgs ： 查询条件的参数，相当于 ？。
                 * 第四个参数 sortOrder ： 结果排序。
                 */
                Cursor cursor = cr.query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        // 视频ID:MediaStore.Audio.Media._ID
//                        int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                        // 视频名称：MediaStore.Audio.Media.TITLE
                        String VideoTitle = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                         //得到视频的类型
//                        String videoType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));


                        if(VideoTitle.endsWith(".mp4") || VideoTitle.endsWith(".3gp")){

                            // 视频路径：MediaStore.Audio.Media.DATA
                            String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                            // 视频时长：MediaStore.Audio.Media.DURATION
                            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                            // 视频大小：MediaStore.Audio.Media.SIZE
                            Videosize = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                            // 视频缩略图路径：MediaStore.Images.Media.DATA
                            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                            // 缩略图ID:MediaStore.Audio.Media._ID
                            int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));

                            // 方法一 Thumbnails 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                            // 第一个参数为 ContentResolver，第二个参数为视频缩略图ID， 第三个参数kind有两种为：MICRO_KIND和MINI_KIND 字面意思理解为微型和迷你两种缩略模式，前者分辨率更低一些。
//                            Bitmap bitmap1 = MediaStore.Video.Thumbnails.getThumbnail(cr, imageId, MediaStore.Video.Thumbnails.MICRO_KIND, null);

                            // 方法二 ThumbnailUtils 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                            // 第一个参数为 视频/缩略图的位置，第二个依旧是分辨率相关的kind
                            Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(imagePath, MediaStore.Video.Thumbnails.MICRO_KIND);
                            // 如果追求更好的话可以利用 ThumbnailUtils.extractThumbnail 把缩略图转化为的制定大小
//
//                            FilePath=data.getStringExtra(videoPath);
                            uploader.clearFiles();   //先清除上传列表，因为是单个上传
                            uploader.addFile(videoPath,getVodInfo());
                            add.setVisibility(View.GONE);
                            add_rl.setBackground(new BitmapDrawable(bitmap2));
                            Log.i("TAG","相册的视频地址为"+videoPath+"listFiles"+uploader.listFiles().size());

                        }else{

                            Toast.makeText(PublishActivity.this,"请选择正确的文件",Toast.LENGTH_SHORT).show();
                        }

                    }
                    cursor.close();
                }
            }
        }

    }


    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    /**
     * 设置上传文件的信息
     * @return
     */
    private VodInfo getVodInfo() {
        VodInfo vodInfo = new VodInfo();
        vodInfo.setTitle(title.getText().toString());  //标题
        vodInfo.setDesc("描述." + 0);
        vodInfo.setCateId(1);
        vodInfo.setIsProcess(true);
//        vodInfo.setCoverUrl("http://www.taobao.com/" + index + ".jpg");
        List<String> tags = new ArrayList<>();
        tags.add("标签" + 0);
        vodInfo.setTags(tags);  //设置标签

            vodInfo.setIsShowWaterMark(false);   //是否启用水印
            vodInfo.setPriority(7);

        return vodInfo;
    }
}
