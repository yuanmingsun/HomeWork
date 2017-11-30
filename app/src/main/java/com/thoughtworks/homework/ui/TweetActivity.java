package com.thoughtworks.homework.ui;

import android.os.Bundle;

import com.thoughtworks.homework.R;
import com.thoughtworks.homework.presenter.TweetPresenter;
import com.thoughtworks.mvp.mvp.BaseActivity;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-11-30
 * 朋友圈的列表浏览
 */
public class TweetActivity extends BaseActivity<TweetPresenter> {

    @Override
    public void initData(Bundle savedInstanceState) {

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
