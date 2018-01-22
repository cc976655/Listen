package com.cuje.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cuje.myapplication.R;
import com.cuje.myapplication.utils.SettingEditor;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CUJE on 2017/4/16.
 */

public class TtsSetting extends AppCompatActivity {
    @BindView(R.id.speed_tv)
    TextView speedTv;       //语速TV
    @BindView(R.id.speedSeek)
    AppCompatSeekBar speedSeek;     //语速SEEK
    @BindView(R.id.volume_tv)
    TextView volumeTv;          //音量TV
    @BindView(R.id.volumeSeek)
    AppCompatSeekBar volumeSeek;        //音量SEEK
    @BindView(R.id.voicer_spinner)
    Spinner voicerSpinner;      //发音人Spinner选择框
    @BindView(R.id.save)
    Button save;        //保存按钮

    private Context context;        //当前上下文
    private String name, speed, volume;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        context = this;
        setUp();

        //语速控件
        speedSeek.setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = String.valueOf(progress);
                speedTv.setText(speed);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //音量控件
        volumeSeek.setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volume = String.valueOf(progress);
                volumeTv.setText(volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    private void setUp() {              //打开页面前提取参数，并给相应控件设置参数
        Map<String, String> settingMap = new SettingEditor().getSettings(context);
        name = settingMap.get("name");
        speed = settingMap.get("speed");
        volume = settingMap.get("volume");
        speedTv.setText(speed);
        volumeTv.setText(volume);
        volumeSeek.setProgress(Integer.valueOf(volume));
        speedSeek.setProgress(Integer.valueOf(speed));
        voicerSpinner.setSelection(Integer.valueOf(name));
    }

    @OnClick(R.id.save)
    public void onClick() {
        name = String.valueOf(voicerSpinner.getSelectedItemId());
        new SettingEditor().saveSetting(context, name, speed, volume);      //保存参数按钮
        Toast.makeText(context,"保存成功！",Toast.LENGTH_SHORT).show();
    }
}
