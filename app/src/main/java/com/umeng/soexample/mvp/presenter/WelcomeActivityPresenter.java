package com.umeng.soexample.mvp.presenter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.umeng.soexample.R;
import com.umeng.soexample.adapter.WelcomeAdapter;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.utils.SharedPreferencesUtils;

public class WelcomeActivityPresenter extends AppDelegate {

    private ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        super.initData();
        SharedPreferencesUtils.putString(context, "frist", "scuess");
        viewPager = get(R.id.we_vp);
        WelcomeAdapter welcomeAdapter = new WelcomeAdapter(context);
        viewPager.setAdapter(welcomeAdapter);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
