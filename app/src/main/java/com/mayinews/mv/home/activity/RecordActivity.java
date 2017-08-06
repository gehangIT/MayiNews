package com.mayinews.mv.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aliyun.demo.crop.AliyunVideoCrop;
import com.aliyun.demo.recorder.AliyunVideoRecorder;
import com.aliyun.struct.common.ScaleMode;
import com.aliyun.struct.common.VideoQuality;
import com.aliyun.struct.recorder.CameraType;
import com.aliyun.struct.recorder.FlashType;
import com.aliyun.struct.snap.AliyunSnapVideoParam;
import com.mayinews.mv.R;

public class RecordActivity extends Activity {
    private static final int REQUEST_RECORD = 1;
    private Button startRecord;
    AliyunSnapVideoParam videoParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        startRecord = (Button)findViewById(R.id.startRecord);
        initPaarams();//初始化参数

                //拉起录制
//                AliyunVideoRecorder.startRecordForResult(RecordActivity.this,REQUEST_RECORD,videoParam);

    }

    private void initPaarams() {

//        videoParam = new AliyunSnapVideoParam.Builder()
//                .setResulutionMode(AliyunSnapVideoParam.RESOLUTION_360P)
//                .setRatioMode(AliyunSnapVideoParam.RATIO_MODE_3_4)
//                .setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)
////                       .setFilterList()
//                .setBeautyLevel(80)
//                .setBeautyStatus(true)
//                .setCameraType(CameraType.BACK)//设置前后摄像头
//                .setFlashType(FlashType.OFF)//设置闪光灯模式
//                .setNeedClip(true)//设置是否支持片段录制
////                        .setMaxDuration()  设置最大录制时长
////                          .setMinDuration()  设置最短录制时长
//                .setVideQuality(VideoQuality.SD)  //设置录制视频质量
////                            .setGop()   设置关键帧间隔
//                .build();





    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==  REQUEST_RECORD){
            if(resultCode == Activity.RESULT_OK && data!= null){
                int type = data.getIntExtra(AliyunVideoRecorder.RESULT_TYPE,0);
                if(type ==  AliyunVideoRecorder.RESULT_TYPE_CROP){
                    String path = data.getStringExtra(AliyunVideoCrop.RESULT_KEY_CROP_PATH);
                    Toast.makeText(this,"文件路径为 "+ path + " 时长为 " +
                            data.getLongExtra(AliyunVideoCrop.RESULT_KEY_DURATION,0),Toast.LENGTH_SHORT).show();
                }else if(type ==  AliyunVideoRecorder.RESULT_TYPE_RECORD){
                    Toast.makeText(this,"文件路径为 "+
                            data.getStringExtra(AliyunVideoRecorder.OUTPUT_PATH),Toast.LENGTH_SHORT).show();
                }
            }else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"用户取消录制",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
