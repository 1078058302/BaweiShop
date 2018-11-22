package com.umeng.soexample.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.soexample.R;
import com.umeng.soexample.mvp.presenter.BaseActivityPresenter;
import com.umeng.soexample.mvp.presenter.StartActivityPresenter;

public class StartActivity extends BaseActivityPresenter<StartActivityPresenter> {

    @Override
    public Class<StartActivityPresenter> getClassDelegate() {
        return StartActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
