package com.cuje.myapplication.JsonService;

import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by CUJE on 2017/4/10.
 */

public class NewsApi {
    private OkHttpClient client;
    private int page = 1;
    private String url ;
    private final static String showapi_appid = "32703";
    private final static String showapi_sign = "9c6c99e7d81145f28f9a2567b0757389";
    private JSONArray jsonArray ;

    public void newsHttp(int page){
        this.page = page;
        url = "http://route.showapi.com/109-35?" +
                "showapi_appid="+showapi_appid+"&" +
                "showapi_sign="+showapi_sign +"&" +
                "page="+String.valueOf(page)+"&"+
                "channelid=5572a108b3cdc86cf39001cd&" +
                "needAllList=0&" +
                "needHtml=1";
        client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("get error !","----------------" + e.toString() );
            }

            @Override
            public void onResponse(Response response)throws IOException{
                try {
                    JSONObject object = new JSONObject(response.body().string().toString());
                    jsonArray = object.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
                }catch (Exception e){
                    Log.i("Exception in NewsApi","----------------------"+e.toString());
                }
            }
        });
    }
}
