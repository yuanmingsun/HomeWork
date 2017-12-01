package com.thoughtworks.homework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtworks.homework.R;
import com.thoughtworks.homework.appsupport.net.VolleySingleton;
import com.thoughtworks.homework.bean.TweetBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-12-01
 * tweet列表
 */
public class TweetListAdapter extends BaseAdapter {
    private Context mContext;
    private List<TweetBean> list;

    public TweetListAdapter(Context context,List<TweetBean> list)
    {
        this.mContext=context;
        notifyAll(list);
    }
    public TweetListAdapter(Context context)
    {
        this.mContext=context;
        list=new ArrayList<>();
    }

    public void notifyAll(List<TweetBean> list)
    {
        this.list=list;
        notifyDataSetChanged();
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
        ViewHolder holder;
        TweetBean tweetBean=list.get(position);
        if(convertView==null)
        {
            holder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.tweet_item,null);
            holder.headerImgView=(ImageView) convertView.findViewById(R.id.item_header_img);
            holder.nameTextView=(TextView)convertView.findViewById(R.id.item_name_text);
            holder.contentTextView=(TextView)convertView.findViewById(R.id.item_content_text);
            holder.mGridView=(GridView)convertView.findViewById(R.id.item_img_grid);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder) convertView.getTag();
        }
        TweetBean.SenderBean senderBean=tweetBean.getSender();
        if(senderBean!=null)
        {
            VolleySingleton.getVolleySingleton(mContext).displayImage(holder.headerImgView,senderBean.getAvatar());
            holder.headerImgView.setTag(senderBean.getAvatar());
            holder.nameTextView.setText(senderBean.getNick());
        }
        else{
            holder.headerImgView.setImageResource(R.mipmap.avatar_default);
            holder.nameTextView.setText("");
        }
        if(tweetBean.getContent()!=null)
        {
            holder.contentTextView.setText(tweetBean.getContent());
        }
        else{
            holder.contentTextView.setText("");
        }
       if(tweetBean.getImages()!=null&&tweetBean.getImages().size()>0){
        holder.mGridView.setVisibility(View.VISIBLE);
        holder.mGridView.setAdapter(new GridAdapter(mContext,tweetBean.getImages()));}
        else
       {
           holder.mGridView.setVisibility(View.GONE);
       }

        return convertView;
    }



    class ViewHolder{
        ImageView headerImgView;
        TextView  nameTextView;
        TextView contentTextView;
        GridView mGridView;
    }
}
