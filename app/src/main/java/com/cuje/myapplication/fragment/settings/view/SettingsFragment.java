package com.cuje.myapplication.fragment.settings.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cuje.myapplication.R;
import com.cuje.myapplication.activity.TtsSetting;
import com.cuje.myapplication.activity.testActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by CUJE on 2017/4/10.
 */

public class SettingsFragment extends Fragment {
    @BindView(R.id.Ttsset)
    TextView Ttsset;
    @BindView(R.id.myInfo)
    TextView myInfo;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.myInfo, R.id.Ttsset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myInfo:
                startActivity(new Intent().setClass(getActivity(), testActivity.class));
                break;
            case R.id.Ttsset:
                startActivity(new Intent().setClass(getActivity(), TtsSetting.class));
                break;
        }
    }
}
