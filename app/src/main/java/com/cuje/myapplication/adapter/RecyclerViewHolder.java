package com.cuje.myapplication.adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cuje.myapplication.R;
import com.cuje.myapplication.beans.NewsBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by CUJE on 2017/2/26.
 */

public class RecyclerViewHolder extends BaseViewHolder<NewsBean> {

    private ImageView news_pic;
    private TextView news_title;
    private TextView news_time;


    public RecyclerViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_newslist);
        news_pic = $(R.id.news_pic);
        news_title = $(R.id.news_title);
        news_time = $(R.id.news_time);
    }


    //设置数据
    @Override
    public void setData(NewsBean data) {
        Log.i("TAG",data.getTitle());
        String title = data.getTitle();
        if (title.equals("")){          //如果获取的title为空，则设置content的前二十的字符作为标题
            news_title.setText("标题不见啦！");
        }else {
            news_title.setText(data.getTitle());
        }
        news_time.setText(data.getPubDate());
        Glide.with(getContext()).load(data.getImageurls()).placeholder(R.mipmap.ic_launcher).centerCrop().into(news_pic);
    }
}