package com.cuje.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CUJE on 2017/4/16.
 */

public class SettingEditor {

    /*
    name :发音人
    speed：语速
    volume：音量
     */
    public void saveSetting(Context context, String name, String speed , String volume){

        SharedPreferences sp = context.getSharedPreferences("Settings",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",name);
        editor.putString("speed",speed);
        editor.putString("volume",volume);
        editor.commit();
    }


    public Map<String,String> getSettings(Context context){
        SharedPreferences sp = context.getSharedPreferences("Settings",Context.MODE_PRIVATE);
        String name = sp.getString("name",null);
        String speed = sp.getString("speed",null);
        String volume = sp.getString("volume",null);
        Map<String ,String> settings = new HashMap<String,String>();
        if (name == null)name = "0";
        if (speed == null)speed = "50";
        if (volume == null)volume = "50";
        settings.put("name",name);
        settings.put("speed",speed);
        settings.put("volume",volume);
        return settings;
    }
}
