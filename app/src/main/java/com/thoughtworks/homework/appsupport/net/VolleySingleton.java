package com.thoughtworks.homework.appsupport.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.thoughtworks.homework.R;
import com.thoughtworks.homework.appsupport.Config;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-11-30
 * volley框架的封装，http请求，图片加载
 */
public class VolleySingleton {
    private static VolleySingleton volleySingleton;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;
    public VolleySingleton(Context context) {
        this.mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache(){
                    private final LruCache<String,Bitmap> cache = new LruCache<>(20);
                    @Override
                    public Bitmap getBitmap(String url){
                        return cache.get(url);
                    }
                    @Override
                    public void putBitmap(String url,Bitmap bitmap){
                        cache.put(url,bitmap);
                    }
                });
    }
    public static synchronized VolleySingleton getVolleySingleton(Context context){
        if(volleySingleton == null){
            volleySingleton = new VolleySingleton(context);
        }
        return volleySingleton;
    }
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        req.setRetryPolicy(new DefaultRetryPolicy(Config.TIMEOUT,Config.RETRYNUM, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public <T extends ImageView> void displayImage(final T img,String url )
    {
       ImageLoader.ImageListener listener= getImageLoader().getImageListener(img,R.mipmap.avatar_default,R.mipmap.avatar_default);
        getImageLoader().get(url, listener);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
