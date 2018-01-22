package com.cuje.myapplication.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.cuje.myapplication.beans.NewsBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by CUJE on 2017/4/10.
 */

public class RecyclerViewAdapter extends RecyclerArrayAdapter<NewsBean> {
    public RecyclerViewAdapter(Context context) {
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(parent);
    }
}
