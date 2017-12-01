package com.thoughtworks.homework.appsupport.mvp;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-11-30
 */
public interface IPresenter<V> {
    //绑定V层
    void attachView(V v);
    //解绑V层
    void detachView();
}
