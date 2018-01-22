package com.cuje.myapplication.beans;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class WeatherInfo {


/*
    {"desc":"OK","status":1000,"data":
    {"wendu":"14","ganmao":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。","forecast":
    [{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 15℃","type":"阴","low":"低温 9℃","date":"19日星期二"},
    {"fengxiang":"无持续风向","fengli":"微风级","high":"高温 26℃","type":"晴","low":"低温 11℃","date":"20日星期三"},
    {"fengxiang":"无持续风向","fengli":"3-4级","high":"高温 29℃","type":"晴","low":"低温 15℃","date":"21日星期四"},
    {"fengxiang":"北风","fengli":"3-4级","high":"高温 25℃","type":"晴","low":"低温 11℃","date":"22日星期五"},
    {"fengxiang":"无持续风向","fengli":"微风级","high":"高温 24℃","type":"晴","low":"低温 10℃","date":"23日星期六"}],
    "yesterday":
    {"fl":"3-4级","fx":"北风","high":"高温 21℃","type":"晴","low":"低温 8℃","date":"18日星期一"},
    "aqi":"97","city":"北京"}}
*/
    private String city;//城市名
    private String aqi;//城市id
    private String wendu;//温度
    private String ganmao;//风向
    private List<DayWeatherInfo> forecast;//各星期数据
    private DayWeatherInfo yesterday;//昨天数据

    @Override
    public String toString(){
        return "city =" + city +", "+
                "aqi =" + aqi +", "+
                "wendu =" + wendu +", "+
                "ganmao =" + ganmao +", "+
                "forecast =" + forecast +", "+
                "yesterday =" + yesterday;
    }

    public DayWeatherInfo getYesterday() {
        return yesterday;
    }

    public void setYesterday(DayWeatherInfo yesterday) {
        this.yesterday = yesterday;
    }

    public List<DayWeatherInfo> getForecast() {

        return forecast;
    }

    public void setForecast(List<DayWeatherInfo> forecast) {
        this.forecast = forecast;
    }

    public String getGanmao() {

        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public String getWendu() {

        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getAqi() {

        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
