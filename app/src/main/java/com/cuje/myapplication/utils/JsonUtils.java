package com.cuje.myapplication.utils;

import android.util.Log;

import com.cuje.myapplication.beans.DayWeatherInfo;
import com.cuje.myapplication.beans.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class JsonUtils {


    private String city;//城市名
    private String aqi;//城市id
    private String wendu;//温度
    private String ganmao;//风向
    private String forecast;//各星期数据
    private String yesterday;//昨天数据

    public static WeatherInfo getWeatherInfo(String key, String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject jsonWeather = jsonObject.getJSONObject(key);

            Log.i("Weather",jsonObject.toString());
            WeatherInfo weatherInfo = new WeatherInfo();
            weatherInfo.setCity(jsonWeather.getString("city"));
            //weatherInfo.setAqi(jsonWeather.getString("aqi"));//有些没有此项数据
            weatherInfo.setGanmao(jsonWeather.getString("ganmao"));
            weatherInfo.setWendu(jsonWeather.getString("wendu"));

            JSONArray jsonDayWeahers = jsonWeather.getJSONArray("forecast");
            List<DayWeatherInfo> dayWeatherInfos = new ArrayList<DayWeatherInfo>();
            for(int i=0;i<jsonDayWeahers.length();i++){
                JSONObject obj = jsonDayWeahers.getJSONObject(i);
                DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
                /* [{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 15℃","type":"阴","low":"低温 9℃","date":"19日星期二"},*/
                dayWeatherInfo.setFengxiang(obj.getString("fengxiang"));
                dayWeatherInfo.setFengli(obj.getString("fengli"));
                dayWeatherInfo.setHigh(obj.getString("high"));
                dayWeatherInfo.setLow(obj.getString("low"));
                dayWeatherInfo.setType(obj.getString("type"));
                dayWeatherInfo.setDate(obj.getString("date"));
                dayWeatherInfos.add(dayWeatherInfo);
            }
            weatherInfo.setForecast(dayWeatherInfos);

            JSONObject jsonObject1 = jsonWeather.getJSONObject("yesterday");
            /*{"fl":"3-4级","fx":"北风","high":"高温 21℃","type":"晴","low":"低温 8℃","date":"18日星期一"},*/
            DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
            dayWeatherInfo.setFengxiang(jsonObject1.getString("fx"));
            dayWeatherInfo.setFengli(jsonObject1.getString("fl"));
            dayWeatherInfo.setHigh(jsonObject1.getString("high"));
            dayWeatherInfo.setLow(jsonObject1.getString("low"));
            dayWeatherInfo.setType(jsonObject1.getString("type"));
            dayWeatherInfo.setDate(jsonObject1.getString("date"));
            weatherInfo.setYesterday(dayWeatherInfo);

            return weatherInfo;
        }catch (Exception e){
            e.printStackTrace();
            Log.e(e.toString(),"获取Json数据失败");
            return null;
        }
    }
}
