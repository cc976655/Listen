package com.cuje.myapplication.beans;

/**
 * Created by Administrator on 2016/4/20.
 */
public class DayWeatherInfo {

   /* [{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 15℃","type":"阴","low":"低温 9℃","date":"19日星期二"},*/

    private String fengxiang;
    private String fengli;
    private String high;
    private String type;
    private String low;
    private String date;

    @Override
    public String toString(){
        return "[fengxiang =" + fengxiang +", "+
                "fengli =" + fengli +", "+
                "high =" + high +", "+
                "type =" + type +", "+
                "low =" + low +", "+
                "yesterday =" + date +"]";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLow() {

        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHigh() {

        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getFengli() {

        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getFengxiang() {

        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }
}
