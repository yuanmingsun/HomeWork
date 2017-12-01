package com.thoughtworks.homework.appsupport.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-12-01
 * 解析json数据格式
 */
public class JsonRequest<T> extends Request<T> {
    private Class<T> classz;
    private Response.Listener<T> mListener;
    private Gson gson;

    public JsonRequest(int method, String url, Response.ErrorListener listener, Response.Listener<T> responseListener,Class<T> classz) {
        super(method, url, listener);
        this.mListener=responseListener;
        this.classz=classz;
    }


    //默认为get请求
    public JsonRequest( String url, Response.ErrorListener listener, Response.Listener<T> responseListener,Class<T> classz) {
        this(Method.GET, url, listener,responseListener,classz);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json;
        try {
            json=new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            json=new String(response.data);
        }
        T t;
        try {
            if (gson==null) {
                gson = new Gson();
            }
            t=gson.fromJson(json,classz);
            return Response.success(t,HttpHeaderParser.parseCacheHeaders(response));
        }catch (Exception e) {
            return Response.error(new ParseError());
        }

    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
