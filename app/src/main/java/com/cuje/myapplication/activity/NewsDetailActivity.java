package com.cuje.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.cuje.myapplication.R;
import com.cuje.myapplication.TtsUtil.TtsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CUJE on 2017/4/10.
 */

public class NewsDetailActivity extends AppCompatActivity {

    private String title = null;//新闻标题
    private String html = null;//新闻的heml代码
    private String content = null;//新闻的文字
    private Context context;//当前上下文
    private TtsUtil ttsUtil;//tts工具
    private boolean isFirstPlay;//是否第一次播放
    private boolean isPause;//是否暂停

    /**
     * 控件绑定
     * ttsStop语音停止按钮
     * newsWeb  记载新闻详情的界面
     * tts_on_pause语音暂停按钮
     * tts_seetings语音设置按钮
     */
    @BindView(R.id.tts_stop)
    Button ttsStop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.newsWeb)
    WebView newsWeb;
    @BindView(R.id.tts_on_pause)
    Button ttsOnPause;
    @BindView(R.id.tts_seetings)
    Button ttsSeetings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        context = this;
        ttsUtil = new TtsUtil();
        getData();
        isFirstPlay = true;
        isPause = false;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //由于API提供的HTML代码图片不适配，所以自己加上
        newsWeb.loadData("<html><head><style>img{width:100%;height:auto;}</style></head><body>" +
                "<h1>" + title + "</h1>"
                + html +
                "</body></html>", "text/html; charset=UTF-8", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttsUtil.destory();
    }

    @OnClick({R.id.tts_on_pause, R.id.tts_stop,R.id.tts_seetings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tts_on_pause:
                if(isFirstPlay){
                    ttsUtil.speak(context, content);
                    ttsOnPause.setText(R.string.pause);
                    isFirstPlay = false;
                }else {
                    if (!isPause){
                        ttsUtil.pause();
                        isPause = true;
                        ttsOnPause.setText(R.string.resume);
                    }else {
                        ttsUtil.resume();
                        isPause = false;
                        ttsOnPause.setText(R.string.pause);
                    }
                }
                break;
            case R.id.tts_stop:
                if (!isFirstPlay){
                    ttsUtil.destory();
                    ttsOnPause.setText(R.string.play);
                    isFirstPlay = true;
                }else{
                    Toast.makeText(context,"还没播放就停止，小拳拳捶你胸口！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tts_seetings:
                Intent intent = new Intent(this,TtsSetting.class);
                startActivity(intent);
                break;
        }
    }

    //接受Intent传来的参数
    private void getData() {
        Intent intent = getIntent();
        html = intent.getStringExtra("html");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
    }
}
