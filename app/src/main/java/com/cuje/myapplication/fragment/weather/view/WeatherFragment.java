package com.cuje.myapplication.fragment.weather.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cuje.myapplication.R;
import com.cuje.myapplication.TtsUtil.TtsUtil;
import com.cuje.myapplication.activity.OptionActivity;
import com.cuje.myapplication.beans.DayWeatherInfo;
import com.cuje.myapplication.beans.WeatherInfo;
import com.cuje.myapplication.utils.HttpUtils;
import com.cuje.myapplication.utils.JsonUtils;
import com.cuje.myapplication.utils.SharedUtils;
import com.cuje.myapplication.view.CircleViewPager;
import com.cuje.myapplication.view.MyScrollView;
import com.cuje.myapplication.view.MyViewPagerDots;
import com.cuje.myapplication.view.NoScrollListView;
import com.cuje.myapplication.view.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by CUJE on 2017/4/10.
 */

public class WeatherFragment extends Fragment implements View.OnClickListener {

    public static List<String> allCityNameList;
    @BindView(R.id.scaleAnimBackLL)
    FrameLayout scaleAnimBackLL;        //返回区域
    @BindView(R.id.myViewPager)
    CircleViewPager myViewPager;        //天气的VIEWPAGER
    @BindView(R.id.myScrollView)
    MyScrollView myScrollView;      //整个ScrollView
    @BindView(R.id.animCloud1)
    ImageView animCloud1;       //云彩的图像
    @BindView(R.id.animCloud2)
    ImageView animCloud2;       //云彩的图像
    @BindView(R.id.scaleAnimLL)
    FrameLayout scaleAnimLL;        //
    @BindView(R.id.cityName)
    TextView cityName;          //城市的TV
    @BindView(R.id.cityType)
    TextView cityType;      //城市的温度
    @BindView(R.id.cityTemp)
    TextView cityTemp;
    @BindView(R.id.scaleLL)
    LinearLayout scaleLL;
    @BindView(R.id.myViewPagerDots)
    MyViewPagerDots myViewPagerDots;        //用于显示viewpager当前界面的点
    @BindView(R.id.optionImage)
    ImageView optionImage;      //选项按钮
    @BindView(R.id.shareImage)
    ImageView shareImage;       //分享按钮

    Unbinder unbinder;
    //播放按钮
    @BindView(R.id.fab_tts)
    ImageView fabTts;


    private ViewPagerAdapter myPagerAdapter;

    private Map<String, Integer> typeMap = getTypeMap();

    private Map<String, Integer> getTypeMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("晴", R.drawable.ww0);
        map.put("多云", R.drawable.ww1);
        map.put("阴", R.drawable.ww2);
        map.put("阵雨", R.drawable.ww3);
        map.put("晴", R.drawable.ww4);
        map.put("晴", R.drawable.ww5);
        map.put("晴", R.drawable.ww6);
        map.put("小雨", R.drawable.ww7);
        map.put("中雨", R.drawable.ww8);
        map.put("大雨", R.drawable.ww9);
        map.put("暴雨", R.drawable.ww10);
        map.put("晴", R.drawable.ww13);
        map.put("小雪", R.drawable.ww14);
        map.put("晴", R.drawable.ww15);
        map.put("晴", R.drawable.ww16);
        map.put("晴", R.drawable.ww17);
        map.put("晴", R.drawable.ww18);
        map.put("晴", R.drawable.ww19);
        map.put("晴", R.drawable.ww20);
        map.put("晴", R.drawable.ww29);
        map.put("晴", R.drawable.ww30);
        map.put("晴", R.drawable.ww31);
        map.put("晴", R.drawable.ww32);
        map.put("晴", R.drawable.ww33);
        map.put("晴", R.drawable.ww34);
        map.put("晴", R.drawable.ww35);
        map.put("晴", R.drawable.ww36);
        map.put("晴", R.drawable.ww45);
        map.put("晴", R.drawable.ww0);
        return map;
    }
    private int myScrollViewPosY = 0;
    private int myScrollViewDownPosY = 0;
    private int myScrollViewUpPosY = 0;
    private int scaleMaxHeight;
    private int scaleMinHeight = 250;
    private int scaleMaxTempSize;
    private int scaleMinTempSize = 80;
    private int scaleMaxTypeSize;
    private int scaleMinTypeSize = 0;
    private int scaleMaxCitySize;
    private int scaleMinCitySize = 40;
    private String weatherTip = null;

    private List<Map<String, String>> todayWeatherInfos;

    public static final String HOST_NAME = "http://wthrcdn.etouch.cn/weather_mini?citykey=";

    public static final int UPDATE = 0;
    public static final int FAIL_TO_CONECTION = 1;


    //开辟新线程更新天气
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE) {//更新天气数据
                todayWeatherInfos = getTodayWeatherInfos((List<WeatherInfo>) msg.obj);//储存各城市今天的信息信息更新
                updateAllViews((List<WeatherInfo>) msg.obj);
                Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
            } else if (msg.what == FAIL_TO_CONECTION) {
                Toast.makeText(getActivity(), "网络异常，连接失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否是第一次运行
        firstInfoSet();
        //获取城市名称列表
        getAllCityNameList();
    }

    //更新数据主操作
    private void updateAllViews(List<WeatherInfo> weatherInfos) {
        updateViewPager(weatherInfos);
        weatherTip = weatherInfos.get(myViewPager.getTrueCurrentItem()).getGanmao();
    }

    //更新viewpager
    private void updateViewPager(List<WeatherInfo> weatherInfos) {
        if (myViewPager.getAdapter() == null) {
            myPagerAdapter = new ViewPagerAdapter(getViews(weatherInfos));
            myViewPager.setAdapter(myPagerAdapter);
            myViewPager.setOnPageChangeListener(myOnPageChangeListener);//此句话必须执行
            myViewPager.setCurrentItem(1);//设置默认页；必须执行，不然初始显示为最后一页
            myViewPager.setDefaultPagerDots(myViewPagerDots);//设置默认的dots点
            myViewPagerDots.initDotsView(myViewPager.getAdapter().getCount() - 2);//初始化dotsview
        } else {
            myPagerAdapter.changeViews(getViews(weatherInfos));
            myPagerAdapter.notifyDataSetChanged();
            myViewPagerDots.initDotsView(myPagerAdapter.getCount() - 2);//初始化dotsview
        }
        cityName.setText(weatherInfos.get(myViewPager.getTrueCurrentItem()).getCity());
    }

    private ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {//改变主页面 城市名，天气类型，温度显示
            cityName.setText(todayWeatherInfos.get(myViewPager.getTrueCurrentItem()).get("cityName"));
            cityTemp.setText(todayWeatherInfos.get(myViewPager.getTrueCurrentItem()).get("cityTemp"));
            cityType.setText(todayWeatherInfos.get(myViewPager.getTrueCurrentItem()).get("cityType"));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    //创建PagerAdapter的View 列表，重点在于添加头尾两个部分
    public List<View> getViews(List<WeatherInfo> listInfos) {
        List<View> views = new ArrayList<View>();
        int size = listInfos.size();
        for (int i = 0; i < size + 2; i++) {
            View view = null;
            if (i == 0) {
                int tempI = size - 1;
                view = instantiateView(listInfos.get(tempI));//实例化一个View，添加到ViewPager
            } else if (i == size + 1) {
                int tempI = 0;
                view = instantiateView(listInfos.get(tempI));//实例化一个View，添加到ViewPager
            } else {
                int tempI = i - 1;
                view = instantiateView(listInfos.get(tempI));//实例化一个View，添加到ViewPager
            }
            views.add(view);
        }
        return views;
    }

    //实例化一个View，添加到ViewPager
    public View instantiateView(WeatherInfo weatherInfo) {
        View view = View.inflate(getActivity(), R.layout.weather_weekinfo, null);

        GridView myGridView = (GridView) view.findViewById(R.id.myGridView);
        TextView noticeWeather = (TextView) view.findViewById(R.id.noticeWeather);

        //设置头部多余的高度
        LinearLayout scaleBackLL = (LinearLayout) view.findViewById(R.id.scaleBackLL);
        ViewGroup.LayoutParams params = scaleBackLL.getLayoutParams();
        params.height = scaleMaxHeight;
        scaleBackLL.setLayoutParams(params);
        //weekinfo
        List<DayWeatherInfo> dayWeatherInfos = new ArrayList<DayWeatherInfo>();
        dayWeatherInfos.add(weatherInfo.getYesterday());
        dayWeatherInfos.addAll(weatherInfo.getForecast());
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), getData(dayWeatherInfos), R.layout.item_weekweather,
                new String[]{"weekendText", "weekendImage", "weekendTemp"},
                new int[]{R.id.weekendText, R.id.weekendImage, R.id.weekendTemp});
        myGridView.setAdapter(adapter);

        //感冒提示部分

        noticeWeather.setText(weatherInfo.getGanmao());

        //风力风向
        NoScrollListView myWindList = (NoScrollListView) view.findViewById(R.id.myWindList);
        SimpleAdapter adapter1 = new SimpleAdapter(getActivity(), getWindData(weatherInfo.getForecast()), R.layout.item_windlist,
                new String[]{"windDirection", "windForce"}, new int[]{R.id.windDirection, R.id.windForce});
        myWindList.setAdapter(adapter1);
        return view;
    }

    //windlist
    private List<Map<String, Object>> getWindData(List<DayWeatherInfo> dayWeatherInfos) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (DayWeatherInfo info : dayWeatherInfos) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("windDirection", info.getFengxiang());
            map.put("windForce", info.getFengli());
            list.add(map);
        }
        return list;
    }

    //GridView item设置
    private List<Map<String, Object>> getData(List<DayWeatherInfo> dayWeatherInfos) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (DayWeatherInfo info : dayWeatherInfos) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("weekendText", info.getDate());
            Integer typeImageId = typeMap.get(info.getType());
            if (typeImageId != null) {
                map.put("weekendImage", typeImageId);//info.getType()
            } else {
                map.put("weekendImage", R.drawable.wna);
            }
            map.put("weekendTemp", info.getLow() + "/" + info.getHigh());
            list.add(map);
        }
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, null);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        //初始化时开始获取数据 更新数据
        getCityWeatherInfosToHandler();
        return view;
    }

    //读取城市id和名称
    private void firstInfoSet() {
        new Thread() {
            public void run() {
                SharedPreferences sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                int isFirstRun = sp.getInt("isFirstRun", -1);
                if (isFirstRun == -1) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("isFirstRun", 0);
                    editor.commit();
                    SharedUtils.getCityInfoToShared(getActivity());//获取城市名称id保存到“cityInfo”中
                }
            }
        }.start();
    }

    //
    private void getAllCityNameList() {
        new Thread() {
            public void run() {
                allCityNameList = SharedUtils.getCityNameList(getActivity());
            }
        }.start();
    }

    //初始化界面//findViewById全部放这里执行
    private void initViews() {

        //获取最大的缩放高度
        scaleMaxHeight = getScaleHeight();


        ViewGroup.LayoutParams params = scaleLL.getLayoutParams();
        params.height = scaleMaxHeight;
        scaleLL.setLayoutParams(params);

        ViewGroup.LayoutParams paramsAnim = scaleAnimLL.getLayoutParams();
        paramsAnim.height = scaleMaxHeight;
        scaleAnimLL.setLayoutParams(paramsAnim);

        shareImage.setOnClickListener(this);
        optionImage.setOnClickListener(this);

        scaleMaxTempSize = (int) cityTemp.getTextSize();
        scaleMaxTypeSize = (int) cityType.getTextSize();
        scaleMaxCitySize = (int) cityName.getTextSize();

        myScrollView.setOnScrollChangedListener(myOnScrollChangedListener);
        myScrollView.setOnTouchListener(myOnTouchListener);
        myScrollView.setOnScrollTouchEventListener(myOnScrollTouchEventListener);

        //动画设置

        TranslateAnimation tAnim1 = new TranslateAnimation(0f, 300f, 0f, 0f);
        tAnim1.setDuration(8000);
        tAnim1.setRepeatMode(Animation.REVERSE);
        tAnim1.setRepeatCount(Integer.MAX_VALUE);
        animCloud1.startAnimation(tAnim1);
        TranslateAnimation tAnim2 = new TranslateAnimation(-50f, 300f, 200f, 300f);
        tAnim2.setDuration(10000);
        tAnim2.setRepeatMode(Animation.REVERSE);
        tAnim2.setRepeatCount(Integer.MAX_VALUE);
        animCloud2.startAnimation(tAnim2);
    }

    private int getScaleHeight() {//获取头部初始时的高度
        View view = View.inflate(getActivity(), R.layout.weather_weekinfo, null);
        GridView myGridView = (GridView) view.findViewById(R.id.myGridView);
        Log.i("Height", String.valueOf(myGridView.getHeight()));
//        //获取屏幕高度
//        return diviceHeight - myGridView.getLayoutParams().height;
        return getActivity().getWindowManager().getDefaultDisplay().getHeight() -
                getActivity().findViewById(R.id.toolbar).getHeight() -
                getActivity().findViewById(R.id.navigation).getHeight() -
                myGridView.getLayoutParams().height;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private MyScrollView.OnScrollChangedListener myOnScrollChangedListener = new MyScrollView.OnScrollChangedListener() {
        @Override
        public void onScrollChanged(ScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            System.out.println("scrollX = " + scrollX + "scrollY = " + scrollY + "oldScrollX = " + oldScrollX + "oldScrollY = " + oldScrollY);

            myScrollViewPosY = scrollY;
            if ((scaleMaxHeight - scaleMinHeight) < scrollY) {
                myScrollView.setOnTouchListener(null);
            } else {
                myScrollView.setOnTouchListener(myOnTouchListener);
            }
            Log.e("adsfasdfasdfasd", myScrollViewPosY + "");
            //float oldSize = cityTemp.getTextSize();
            //cityTemp.setTextSize(scrollY);
            ViewGroup.LayoutParams params = scaleLL.getLayoutParams();
            ViewGroup.LayoutParams paramsAnim = scaleAnimLL.getLayoutParams();

            if ((scaleMaxHeight - scaleMinHeight) < scrollY) {
                params.height = scaleMinHeight;
                paramsAnim.height = scaleMinHeight;
                cityTemp.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleMinTempSize);
                cityName.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleMinCitySize);
                cityType.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleMinTypeSize);
            } else {
                params.height = scaleMaxHeight - scrollY;
                paramsAnim.height = scaleMaxHeight - scrollY;
                cityTemp.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleMaxTempSize - (scaleMaxTempSize - scaleMinTempSize) * scrollY / (scaleMaxHeight - scaleMinHeight));
                cityName.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleMaxCitySize - (scaleMaxCitySize - scaleMinCitySize) * scrollY / (scaleMaxHeight - scaleMinHeight));
                cityType.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleMaxTypeSize - (scaleMaxTypeSize - scaleMinTypeSize) * scrollY / (scaleMaxHeight - scaleMinHeight));
            }
            scaleLL.setLayoutParams(params);
            scaleAnimLL.setLayoutParams(paramsAnim);

        }
    };

    private MyScrollView.OnTouchListener myOnTouchListener = new MyScrollView.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    myScrollViewDownPosY = getPositionY();
                    Log.e("3333333333333333", myScrollViewUpPosY + "  " + myScrollViewDownPosY);
                    return false;
                case MotionEvent.ACTION_UP:
                    myScrollViewUpPosY = getPositionY();
                    Log.e("222222222222", myScrollViewUpPosY + "  " + myScrollViewDownPosY);
                    if (myScrollViewUpPosY < scaleMaxHeight - scaleMinHeight) {
                        if (myScrollViewUpPosY - myScrollViewDownPosY > 0) {
                            myScrollView.smoothScrollTo(0, scaleMaxHeight - scaleMinHeight);
                        } else {
                            myScrollView.smoothScrollTo(0, 0);
                        }
                    }
                    return true;
                default:
            }
            return false;
        }
    };

    private int getPositionY() {
        return myScrollViewPosY;
    }

    private List<Map<String, String>> getTodayWeatherInfos(List<WeatherInfo> weatherInfos) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (WeatherInfo weatherInfo : weatherInfos) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("cityName", weatherInfo.getCity());
            map.put("cityTemp", weatherInfo.getWendu());
            DayWeatherInfo dayWeatherInfo = weatherInfo.getForecast().get(0);
            map.put("cityType", dayWeatherInfo.getType());
            list.add(map);
        }
        return list;
    }

    private MyScrollView.OnScrollTouchEventListener myOnScrollTouchEventListener = new MyScrollView.OnScrollTouchEventListener() {

        @Override
        public void onScrollTouchEvent(MotionEvent event) {
            Log.e("", "正在点击中，正在。。。。。。。。。。。。。");
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (myScrollViewPosY < scaleMaxHeight - scaleMinHeight) {
                    myScrollView.smoothScrollTo(0, scaleMaxHeight - scaleMinHeight);
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareImage:
                getCityWeatherInfosToHandler();
                break;
            case R.id.optionImage:
                startActivity(new Intent(getActivity(), OptionActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    //获取保存的城市名称
    private String[] getCitys() {
        SharedPreferences sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        String citys = sp.getString("savedCityNames", null);
        if (citys != null) {
            return citys.split("##");
        } else {
            return new String[]{"北京", "上海", "深圳", "厦门", "福州", "霞浦", "杭州"};
        }
    }

    private String getCityId(String cityName) {
        SharedPreferences sp = getActivity().getSharedPreferences("cityInfo", Context.MODE_PRIVATE);
        return sp.getString(cityName, "");
    }

    //获取城市天气信息到主线程 更新数据
    private void getCityWeatherInfosToHandler() {
        new Thread() {
            private List<WeatherInfo> weatherInfos = new ArrayList<WeatherInfo>();

            public synchronized void run() {
                String[] citys = getCitys();
                try {
                    for (String city : citys) {
                        String cityId = getCityId(city);
                        String jsonData = HttpUtils.getWeatherJsonData(HOST_NAME + cityId);
                        Log.i("Weather", jsonData);
                        WeatherInfo weatherInfo = JsonUtils.getWeatherInfo("data", jsonData);

                        if (weatherInfo != null) {
                            weatherInfos.add(weatherInfo);
                        }
                    }
                    if (weatherInfos.size() > 0) {
                        Message msg = new Message();
                        msg.what = UPDATE;
                        msg.obj = weatherInfos;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = FAIL_TO_CONECTION;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = FAIL_TO_CONECTION;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    @OnClick(R.id.fab_tts)
    public void onClick() {
        Toast.makeText(getActivity(),"正在播放中！",Toast.LENGTH_SHORT).show();
        TtsUtil tts = new TtsUtil();
        tts.speak(getActivity(),weatherTip);
    }
}
