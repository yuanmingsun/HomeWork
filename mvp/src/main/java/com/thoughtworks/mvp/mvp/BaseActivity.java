package com.thoughtworks.mvp.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-11-30
 * 基类activity,所有的activity必须继承自基类activity
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView<P> {

    //持有Prenster
    private P p;

    private Activity context;

    //缓存view
    private HashMap<Integer, View> idViews = new LinkedHashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (getLayoutId() > 0) {
            View v = LayoutInflater.from(context).inflate(getLayoutId(), null);
            setContentView(v);
            bindUI(v);
            bindEvent();
        }
        initData(savedInstanceState);
    }

    @Override
    public void bindUI(View rootView) {
        //暂不实现，用于第三方控件如bufferknife等的初始化操作
    }

    //通过id获取view
    protected <W extends View> W $(int id) {
        if (idViews.containsKey(id)) {
            return (W) idViews.get(id);
        }
        View v = findViewById(id);
        idViews.put(id, v);
        return (W) v;
    }
    protected P getP() {
        if (p == null) {
            p = newP();
            if (p != null) {
                p.attachView(this);
            }
        }
        return p;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getOptionsMenuId() > 0) {
            getMenuInflater().inflate(getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public void bindEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放掉对p持有的引用
        if (p != null) {
            p.detachView();
        }
    }
}
