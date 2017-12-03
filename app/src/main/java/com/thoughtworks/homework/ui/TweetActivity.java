package com.thoughtworks.homework.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.thoughtworks.homework.R;
import com.thoughtworks.homework.adapter.TweetListAdapter;
import com.thoughtworks.homework.appsupport.mvp.BaseActivity;
import com.thoughtworks.homework.appsupport.net.VolleySingleton;
import com.thoughtworks.homework.bean.TweetBean;
import com.thoughtworks.homework.bean.UserBean;
import com.thoughtworks.homework.presenter.TweetPresenter;
import com.thoughtworks.homework.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-11-30
 * 朋友圈的列表浏览
 */
public class TweetActivity extends BaseActivity<TweetPresenter> {

    private LoadMoreListView mTweetListView;
    private SwipeRefreshLayout mTweetrefreshLayout;
    private View headerView;
    private TweetListAdapter mTweetListAdapter;
    private List<TweetBean> mTweetBeanList=new ArrayList<>();
    private int page = 0;

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
        mTweetListAdapter = new TweetListAdapter(this);
        mTweetListView.setAdapter(mTweetListAdapter);
        mTweetListView.setOnLoadMoreListener(mOnLoadMoreListener);
    }


    private synchronized void  loadMore() {
        int startIndex = page * 5;
        int endIndex = page * 5 + 5;
        if (endIndex >= mTweetBeanList.size()) {
            mTweetListAdapter.addData(subList(mTweetBeanList,startIndex, mTweetBeanList.size()));
            mTweetListView.setLoadCompleted();
        } else {
            mTweetListAdapter.addData(subList(mTweetBeanList,startIndex, endIndex));
        }
        page++;
    }

    public <T> List<T> subList(List<T> fromList,int startIndex,int endIndex) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < fromList.size(); i++)
        {
            if(i>=startIndex&&i<endIndex)
            {
                list.add(fromList.get(i));
            }
        }
        return list;
    }

    LoadMoreListView.OnLoadMoreListener mOnLoadMoreListener = new LoadMoreListView.OnLoadMoreListener() {
        @Override
        public void onloadMore() {
           loadMore();
        }
    };
    SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            fetchData();
        }
    };

    public void showError(VolleyError error) {
        mTweetrefreshLayout.setRefreshing(false);
    }

    //添加header到tweet列表中
    private void addHeaderToList(UserBean user) {

        TextView mHeaderNameTxt = $(headerView, R.id.header_name_txt);
        ImageView mHeaderImg = $(headerView, R.id.header_headerimg_img);
        //    ImageView mCoverImg = $(headerView, R.id.header_cover_img);
        VolleySingleton.getVolleySingleton(this).displayImage(mHeaderImg, user.getAvatar());
        mHeaderNameTxt.setText(user.getNick());
    }

    //成功获取userinfo数据
    public void showUserInfo(UserBean userBean) {
        mTweetrefreshLayout.setRefreshing(false);
        addHeaderToList(userBean);
    }

    public void showTweets(List<TweetBean> list) {
        page = 0;
        mTweetBeanList = list;
        mTweetrefreshLayout.setRefreshing(false);
        //  mTweetListView.setLoading(true);
        if (5 >= list.size()) {

            mTweetListAdapter.notifyAll(subList(mTweetBeanList,0, list.size()));
            mTweetListView.setLoadCompleted();
        } else {
            mTweetListAdapter.notifyAll(subList(mTweetBeanList,0, 5));
        }

        page++;
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
