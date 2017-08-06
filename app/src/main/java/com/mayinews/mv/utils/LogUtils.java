package com.mayinews.mv.utils;

import android.util.Log;

/**
 *  大语音日志工具类
 */
public class LogUtils {
    private static final String TAG = "TAG";
    private static boolean enableLog;

    public LogUtils() {
    }
    /*
       允许打印日志
     */
    public static void enableLog() {
        enableLog = true;
    }
    /*
           禁止打印日志
         */
    public static void disableLog() {
        enableLog = false;
    }

    public static boolean isEnableLog() {
        return enableLog;
    }

    public static void logI(String msg) {
        if(enableLog) {
            Log.i("OSS-Android-SDK", msg);
        }

    }

    public static void logV(String msg) {
        if(enableLog) {
            Log.v("OSS-Android-SDK", msg);
        }

    }

    public static void logW(String msg) {
        if(enableLog) {
            Log.w("OSS-Android-SDK", msg);
        }

    }

    public static void logD(String msg) {
        if(enableLog) {
            Log.d("OSS-Android-SDK", msg);
        }

    }

    public static void logE(String msg) {
        if(enableLog) {
            Log.e("OSS-Android-SDK", msg);
        }

    }
}