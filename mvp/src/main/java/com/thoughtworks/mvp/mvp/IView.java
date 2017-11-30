package com.thoughtworks.mvp.mvp;

import android.os.Bundle;
import android.view.View;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-11-30
 */
public interface IView<P> {
    //绑定UI
    void bindUI(View rootView);
    //事件绑定
    void bindEvent();
    //初始化数据
    void initData(Bundle savedInstanceState);
    //获取菜单id
    int getOptionsMenuId();
    //获取布局id
    int getLayoutId();
    //实例化p层
    P newP();
}
