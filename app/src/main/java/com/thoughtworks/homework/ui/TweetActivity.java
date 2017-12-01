package com.thoughtworks.homework.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.thoughtworks.homework.R;
import com.thoughtworks.homework.adapter.TweetListAdapter;
import com.thoughtworks.homework.appsupport.mvp.BaseActivity;
import com.thoughtworks.homework.appsupport.net.VolleySingleton;
import com.thoughtworks.homework.appsupport.utils.AppToast;
import com.thoughtworks.homework.bean.TweetBean;
import com.thoughtworks.homework.bean.UserBean;
import com.thoughtworks.homework.presenter.TweetPresenter;

import java.util.List;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-11-30
 * 朋友圈的列表浏览
 */
public class TweetActivity extends BaseActivity<TweetPresenter> {

    private ListView mTweetListView;
    private SwipeRefreshLayout mTweetrefreshLayout;
    private View headerView;
    private TweetListAdapter mTweetListAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        fetchData();
    }

    //初始化控件
    private void initView() {
        mTweetListView = $(R.id.tweet_listview);
        mTweetrefreshLayout = $(R.id.tweet_refresh_layout);
        mTweetrefreshLayout.setOnRefreshListener(mRefreshListener);
        if (headerView == null) {
            headerView = LayoutInflater.from(this).inflate(R.layout.tweet_header, null);
            mTweetListView.addHeaderView(headerView);
            mTweetListView.setHeaderDividersEnabled(false);
        }
        mTweetListAdapter=new TweetListAdapter(this);
        mTweetListView.setAdapter(mTweetListAdapter);
    }
    SwipeRefreshLayout.OnRefreshListener mRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            fetchData();
        }
    };
    public void showError(VolleyError error)
    {
        AppToast.toast(this,error.toString());
        mTweetrefreshLayout.setRefreshing(false);
    }

    //添加header到tweet列表中
    private void addHeaderToList(UserBean user) {

        TextView mHeaderNameTxt = $(headerView, R.id.header_name_txt);
        ImageView mHeaderImg = $(headerView, R.id.header_headerimg_img);
    //    ImageView mCoverImg = $(headerView, R.id.header_cover_img);
        VolleySingleton.getVolleySingleton(this).displayImage(mHeaderImg, user.getAvatar());
        mHeaderNameTxt.setText(user.getNick());
      //  VolleySingleton.getVolleySingleton(this).displayImage(mCoverImg, user.getProfileimage());
    }

    //成功获取userinfo数据
    public void showUserInfo(UserBean userBean) {
        mTweetrefreshLayout.setRefreshing(false);
        addHeaderToList(userBean);
    }

    public void showTweets(List<TweetBean> list)
    {
         mTweetrefreshLayout.setRefreshing(false);
         mTweetListAdapter.notifyAll(list);
    }


    //从网络获取数据
    private void fetchData() {
        getP().getUserInfo();
        getP().getTweets();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tweet;
    }

    @Override
    public TweetPresenter newP() {
        return new TweetPresenter();
    }
}
