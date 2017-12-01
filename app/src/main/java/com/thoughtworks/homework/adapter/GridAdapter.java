package com.thoughtworks.homework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.thoughtworks.homework.R;
import com.thoughtworks.homework.appsupport.net.VolleySingleton;
import com.thoughtworks.homework.bean.TweetBean;

import java.util.List;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-12-01
 */
public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private List<TweetBean.ImagesBean> list;

    public GridAdapter(Context context,List<TweetBean.ImagesBean> list)
    {
        this.mContext=context;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url=list.get(position).getUrl();
        convertView= LayoutInflater.from(mContext).inflate(R.layout.grid_item,null);
        ImageView gridImg=(ImageView)convertView.findViewById(R.id.grid_img);
        VolleySingleton.getVolleySingleton(mContext).displayImage(gridImg,url);
        return convertView;
    }
}
