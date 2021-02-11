package com.cuje.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cuje.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OptionActivity extends AppCompatActivity implements View.OnClickListener {

    //返回按钮
    @BindView(R.id.backImage)
    ImageView backImage;
    //城市布局
    @BindView(R.id.cityManageLL)
    LinearLayout cityManageLL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {//resultcode 返回值为1，直接后台关闭
            setResult(1, new Intent());
            finish();
        }
    }

    @OnClick({R.id.backImage, R.id.cityManageLL})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backImage:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.cityManageLL:
                startActivityForResult(new Intent(this, CityManageActivity.class), 1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }
}
