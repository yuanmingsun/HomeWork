package com.thoughtworks.homework.appsupport.utils;


import android.util.Log;

import static com.android.volley.VolleyLog.TAG;
import static com.thoughtworks.homework.appsupport.Config.DEBUG;

//打印
public class AppLog {


    //打印info信息
    public static void i(String info){
        if(DEBUG){
            Log.i(TAG,info);
        }
    }
    //打印错误信息
    public static void e(String error){
        if (DEBUG){
            Log.i(TAG,error);
        }
    }
}
