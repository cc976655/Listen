package com.cuje.myapplication.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by CUJE on 2017/4/17.
 */
public class CircleViewPager extends ViewPager implements Runnable {

    private boolean autoRunFlag = false;//初始化自动循环播放为false

    private ViewPagerDots mViewPagerDots;//点提示；

    public CircleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postDelayed(this, 3000);//启动自动循环线程
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) {
                height = h;
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        MyOnPageChangeListener mListener = null;
        if(listener != null){
            mListener = new MyOnPageChangeListener(listener);
        }
        super.setOnPageChangeListener(mListener);
    }



    //重写OnPageChangeListener
    class MyOnPageChangeListener implements OnPageChangeListener {
        private OnPageChangeListener listener;
        public MyOnPageChangeListener(OnPageChangeListener listener){
            this.listener = listener;
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            listener.onPageScrolled(position,positionOffset,positionOffsetPixels);
        }


        @Override
        public void onPageSelected(int position) {
            //Log.e("提示信息：", "我在这里position" + getTrueCurrentItem());
            listener.onPageSelected(position);
        }

        //此为关键步骤，实现循环，所以在实例化CircleViewPager时一定要调用setOnPageChangeListener方法才可实现
        @Override
        public void onPageScrollStateChanged(int state) {
            //Log.e("提示信息：", "状态显示：" + state);
            if(state == 0) {//状态经过顺序为 1、2、0
                if (getCurrentItem() == 0) {//在位置0时，调到倒数第二位
                    setCurrentItem(getAdapter().getCount() - 2, false);
                } else if (getCurrentItem() == getAdapter().getCount() - 1) {//在位置最后一位时，调到第二位
                    setCurrentItem(1, false);
                }
                if(mViewPagerDots != null){mViewPagerDots.setDot(getTrueCurrentItem());}//设置提示点的变化
            }
            listener.onPageScrollStateChanged(state);
        }
    }

    //自动播放设置
    @Override
    public void run() {
        if(autoRunFlag) {
            if (getCurrentItem() != 0 && getCurrentItem() != getAdapter().getCount() - 1) {
                setCurrentItem(getCurrentItem() + 1, true);
            }
            //Log.e("提示信息：", "我在这里position" + getCurrentItem());
        }
        postDelayed(this,3000);
    }
    //自动播放设置
    public void setAutoRun(boolean flag){
        if(autoRunFlag !=flag)
            autoRunFlag = flag;
    }


    //实际位置
    public int getTrueCurrentItem(){
        if(getCurrentItem() == 0){
            return getAdapter().getCount()-3;
        }else if(getCurrentItem() == getAdapter().getCount()-1){
            return 0;
        }
        return getCurrentItem() - 1;
    }

    public void setDefaultPagerDots(ViewPagerDots mViewPagerDots){
        this.mViewPagerDots = mViewPagerDots == null ? null:mViewPagerDots;
    }
    //实现点接口
    public interface ViewPagerDots{
        void setDot(int index);
    }
}


