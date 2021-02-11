package com.cuje.myapplication.view;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * Created by Administrator on 2016/4/17.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;

    public ViewPagerAdapter(List<View> views){
        this.views = views;
    }

    //改变adapter的Views
    public void changeViews(List<View> views){
        this.views = views;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(views !=null){
            return views.size();
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = views.get(position);
        ((ViewPager)container).removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        ((ViewPager)container).addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
