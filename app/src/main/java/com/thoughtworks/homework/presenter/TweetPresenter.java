package com.thoughtworks.homework.presenter;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.thoughtworks.homework.appsupport.mvp.BasePresenter;
import com.thoughtworks.homework.appsupport.net.JsonRequest;
import com.thoughtworks.homework.appsupport.net.VolleySingleton;
import com.thoughtworks.homework.appsupport.utils.AppLog;
import com.thoughtworks.homework.bean.TweetBean;
import com.thoughtworks.homework.bean.UserBean;
import com.thoughtworks.homework.datasource.API;
import com.thoughtworks.homework.ui.TweetActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-11-30
 */
public class TweetPresenter extends BasePresenter<TweetActivity> {


    Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            AppLog.e(error.toString());
            getV().showError(error);

        }
    };

    //获取用户信息
    public void getUserInfo() {
        final JsonRequest<UserBean> userRequest = new JsonRequest<>(API.USERINFO_URL, mErrorListener, new Response.Listener<UserBean>() {
            @Override
            public void onResponse(UserBean userBean) {
                if (userBean != null) {
                    getV().showUserInfo(userBean);
                }
            }
        }, UserBean.class);
        VolleySingleton.getVolleySingleton(getV()).addToRequestQueue(userRequest);
    }


    public  List<TweetBean> dealWrongData(String data) {
        AppLog.i(data);
        Gson gson=new Gson();
        List<TweetBean> list=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(data);
            if(jsonArray.length()>0)
            {
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    if(!TextUtils.isEmpty(jsonObject.optString("error"))||!TextUtils.isEmpty(jsonObject.optString("unknown error")))
                        continue;


                    TweetBean bean=gson.fromJson(jsonObject.toString(), TweetBean.class);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            return list;
        }
    }


    //获取所有的tweet数据
    public void getTweets() {
        StringRequest tweetRequest = new StringRequest(API.TWEETLIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String data) {
                List<TweetBean> list=dealWrongData(data);
                if(list.size()>0)
                {
                    getV().showTweets(list);
                }
            }
        }, mErrorListener);
        VolleySingleton.getVolleySingleton(getV()).addToRequestQueue(tweetRequest);
    }
}
