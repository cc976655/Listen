package com.cuje.myapplication.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/4/19.
 */
public class HttpUtils{

    public HttpUtils(){
    }

    public static String getWeatherJsonData(String path){
        if(TextUtils.isEmpty(path)) {
            Log.e("提示信息:", "路径不能为空");
            return null;
        }else{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; Shuame)");
                int code = conn.getResponseCode();
                if (code == 200) {
                    String data = changeInputStreamToString(conn.getInputStream());
                    return data;
                }else{
                    Log.e("提示信息：", "连接失败");
                    return null;
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.e(e.toString(), "获取失败");
                return null;
            }
        }
    }

    /**
     *
     * @param is 输入流
     * @return 返回转换后的字符串
     */
    public static String changeInputStreamToString(InputStream is){
        BufferedReader bufr = new BufferedReader(new InputStreamReader(is));
        try {
            String line = null;
            StringBuilder sb = new StringBuilder();
            while((line = bufr.readLine())!=null){
                sb.append(line);
            }
            //System.out.println(sb.toString());
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            Log.e(e.getMessage(), "转换失败");
            return null;
        }finally {
            try {
                is.close();
            }catch (Exception e){
                e.printStackTrace();
                Log.e(e.getMessage(),"关闭失败");
            }
        }
    }
}
