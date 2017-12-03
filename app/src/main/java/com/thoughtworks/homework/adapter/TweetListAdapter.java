package com.thoughtworks.homework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtworks.homework.R;
import com.thoughtworks.homework.appsupport.net.VolleySingleton;
import com.thoughtworks.homework.bean.TweetBean;
import com.thoughtworks.homework.view.NineGridView;

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

    public TweetListAdapter(Context context, List<TweetBean> list) {
        this.mContext = context;
        notifyAll(list);
    }

    public TweetListAdapter(Context context) {
        this.mContext = context;
        list = new ArrayList<>();
    }

    public void notifyAll(List<TweetBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addData(List<TweetBean> list)
    {
        this.list.addAll(list);
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
        TweetBean tweetBean = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tweet_item, null);
            holder.headerImgView = (ImageView) convertView.findViewById(R.id.item_header_img);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.item_name_text);
            holder.contentTextView = (TextView) convertView.findViewById(R.id.item_content_text);
            holder.mNineGridView=(NineGridView) convertView.findViewById(R.id.item_img_grid);
            holder.discussLayout=(LinearLayout)convertView.findViewById(R.id.item_discuss_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TweetBean.SenderBean senderBean = tweetBean.getSender();
        holder.headerImgView.setImageBitmap(null);
        if (senderBean != null) {
            VolleySingleton.getVolleySingleton(mContext).displayImage(holder.headerImgView,senderBean.getAvatar());
            holder.nameTextView.setText(senderBean.getNick());
        } else {
            holder.nameTextView.setText("");
        }
        if (tweetBean.getContent() != null) {
            holder.contentTextView.setText(tweetBean.getContent());
        } else {
            holder.contentTextView.setText("");
        }
        if(tweetBean.getImages()!=null&&tweetBean.getImages().size()>0){
            holder.mNineGridView.setVisibility(View.VISIBLE);
            holder.mNineGridView.setAdapter(new GridAdapter(mContext,tweetBean.getImages()));}
        else
        {
            holder.mNineGridView.setVisibility(View.GONE);
        }
        holder.discussLayout.removeAllViews();
        if(tweetBean.getComments()!=null&&tweetBean.getComments().size()>0)
        {
            for(TweetBean.CommentsBean comment :tweetBean.getComments())
            {
                holder.discussLayout.addView(createDiscussView(comment));
            }
        }
        else
        {
            holder.discussLayout.removeAllViews();
        }


        return convertView;
    }

    private View createDiscussView(TweetBean.CommentsBean comment)
    {
       View discussView= LayoutInflater.from(mContext).inflate(R.layout.discuss_item,null);
        TextView discussName=(TextView)discussView.findViewById(R.id.discuss_name_textview);
        TextView discussContent=(TextView)discussView.findViewById(R.id.discuss_content_textview);
        discussName.setText(comment.getSender().getNick()+":");
        discussContent.setText(comment.getContent());
        return discussView;
    }




    class ViewHolder {
        ImageView headerImgView;
        TextView nameTextView;
        TextView contentTextView;
        NineGridView mNineGridView;
        LinearLayout discussLayout;
    }
}
