package com.cuje.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class SharedUtils {

    private static String[] letters = new String[]{
            "A","B","C","D","E","F","G",
            "H","I","J","K","L","M","N",
            "O","P","Q","R","S","T",
            "U","V","W","X","Y","Z",
    };
    private static List<String> lettersList = Arrays.asList(letters);

    //获取列表到cityInfo数据库中
    public static void getCityInfoToShared(Context context){
        BufferedReader bufr = null;
        try{
            bufr = new BufferedReader(new InputStreamReader(context.getAssets().open("city_and_id.txt")));
            String line = null;
            SharedPreferences sp = context.getSharedPreferences("cityInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            while((line = bufr.readLine())!=null){
                if(!line.equals("")){
                    String[] data = line.split(",");
                    editor.putString(data[1],data[0]);
                }
            }
            editor.commit();
        }catch (Exception e){
            Log.e(e.toString(), "加载城市列表失败");

        }finally {
            try {
                if (bufr != null)
                    bufr.close();
            }catch (Exception e){

            }
        }
    }

    //获取名称列表集合
    public static List<String> getCityNameList(Context context){
        List<String> list = new ArrayList<String>();
        BufferedReader bufr = null;
        try{
            bufr = new BufferedReader(new InputStreamReader(context.getAssets().open("city_and_id.txt")));
            String line = null;
            while((line = bufr.readLine())!=null){
                if(!line.equals("")){
                    String[] data = line.split(",");
                    list.add(data[1]);
                }
            }
        }catch (Exception e){
            Log.e(e.toString(), "加载城市列表失败");
            return null;
        }finally {
            try {
                if (bufr != null)
                    bufr.close();
            }catch (Exception e){
                Log.e(e.toString(),"数据流关闭失败");
            }
        }
        list.addAll(lettersList);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                if(!lhs.matches("[A-Z]"))
                    lhs = CharacterParser.getInstance().getSelling(lhs).toUpperCase();
                if(!rhs.matches("[A-Z]"))
                    rhs = CharacterParser.getInstance().getSelling(rhs).toUpperCase();
                return lhs.compareTo(rhs);
            }
        });
        return list;
    }
}
