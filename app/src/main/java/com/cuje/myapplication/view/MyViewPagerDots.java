package com.cuje.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cuje.myapplication.R;

/**
 * Created by Administrator on 2016/4/18.
 */
public class MyViewPagerDots extends LinearLayout implements CircleViewPager.ViewPagerDots{

    private int dotsIndex = 0;//选中点

    private int dots_selectedColor = R.drawable.my_circle_whiteback;//选中状态点颜色
    private int dots_normalColor = R.drawable.my_circle_grayback;//默认状态点颜色
    private int dotsCount;
    private Context context;
    public MyViewPagerDots(Context context) {
        super(context);
        this.context = context;
    }
    public MyViewPagerDots(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
    }

    public void initDotsView(int count) {

        //Log.e("提示：", "getChildCount："+this.getChildCount());
        //删除所有已存在的dots
        if(this.getChildCount()>0) {
            /*for (int i = 0; i < this.getChildCount(); i++) {
                this.removeView(this.getChildAt(i));
            }*/
            this.removeAllViews();
        }
        setCount(count);
        //Log.e("提示：", "count：" + count);
        //初始化
        dotsIndex = 0;//初始化选中点
        for(int i=0;i<count;i++) {
            ImageView dotsView = new ImageView(this.context);
            if(i!=0) {
                dotsView.setBackgroundDrawable(getResources().getDrawable(dots_normalColor));
            }else{
                dotsView.setBackgroundDrawable(getResources().getDrawable(dots_selectedColor));
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(8, 8);
            lp.setMargins(5, 5, 5, 5);
            dotsView.setLayoutParams(lp);
            this.addView(dotsView);
        }
    }

    public int getCount() {
        return dotsCount;
    }

    public void setCount(int count) {
        this.dotsCount = count;
    }

    @Override
    public void setDot(int index) {
        //Log.e("提示：", "index："+index);
        this.getChildAt(dotsIndex).setBackgroundDrawable(getResources().getDrawable(dots_normalColor));
        this.getChildAt(index).setBackgroundDrawable(getResources().getDrawable(dots_selectedColor));
        dotsIndex = index;
    }
}
