package com.thoughtworks.homework.appsupport.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-12-01
 */
public class AppToast {

    public static  void toast(Context context,String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
