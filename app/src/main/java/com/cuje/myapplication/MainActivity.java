package com.cuje.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cuje.myapplication.adapter.ViewPagerAdapter;
import com.cuje.myapplication.fragment.news.view.NewsFragment;
import com.cuje.myapplication.fragment.settings.view.SettingsFragment;
import com.cuje.myapplication.fragment.weather.view.WeatherFragment;
import com.cuje.myapplication.view.CustomViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;        //顶部TOOLBAR
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;      //viewpager框架
    @BindView(R.id.navigation)
    BottomNavigationView navigation;        //底部切换按钮

    private Context context;        //当前上下文
    private ViewPagerAdapter viewPagerAdapter;      //viewpager 的适配器
    private MenuItem menuItem;      //菜单选项

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_weather:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_settings:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        setSupportActionBar(toolbar);
        toolbar.setTitle(navigation.getMenu().getItem(0).getTitle());

        setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null){
                    menuItem.setChecked(false);
                }else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                navigation.getMenu().getItem(position).setChecked(true);
                menuItem = navigation.getMenu().getItem(position);
                toolbar.setTitle(navigation.getMenu().getItem(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //向ViewPager中添加Fragment
    private void setViewPager(CustomViewPager viewPager){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new NewsFragment());
        viewPagerAdapter.addFragment(new WeatherFragment());
        viewPagerAdapter.addFragment(new SettingsFragment());
        viewPager.setAdapter(viewPagerAdapter);
    }
}
