package com.umeng.soexample.mvp.presenter;

import android.content.Context;

import com.umeng.soexample.R;
import com.umeng.soexample.mvp.view.AppDelegate;

public class FiveFragmentPresenter extends AppDelegate {
    @Override
    public int getLayoutId() {
        return R.layout.layout_five;
    }

    @Override
    public void initData() {
        super.initData();
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
