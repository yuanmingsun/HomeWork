package com.thoughtworks.homework.appsupport;

import com.thoughtworks.homework.R;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-12-01
 */
public class Config {
    public static final boolean DEBUG=true;
    public static final String TAG="homework_log";

    //volley超时时间
    public static final int TIMEOUT=10000;
    //volley重连次数
    public static final int RETRYNUM=2;

    // #imageloader
    public static final int IL_LOADING_RES = R.mipmap.avatar_default;//默认的 加载中 图片资源
    public static final int IL_ERROR_RES =R.mipmap.avatar_default;//默认的 加载失败 图片资源

}
