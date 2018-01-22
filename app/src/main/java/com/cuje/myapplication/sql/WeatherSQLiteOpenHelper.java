package com.cuje.myapplication.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/18.
 */
public class WeatherSQLiteOpenHelper extends SQLiteOpenHelper {
    public WeatherSQLiteOpenHelper(Context context){
        super(context,"weather",null,0);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cityweather(id integer primary key autoincrement,cityName varchar,cityId integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
