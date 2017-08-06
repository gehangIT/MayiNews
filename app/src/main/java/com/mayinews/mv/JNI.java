package com.mayinews.mv;

/**
 * Created by gary on 2017/7/20 0020.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class JNI {
    static {
        System.loadLibrary("security");
    }

    public native String getString();
}
