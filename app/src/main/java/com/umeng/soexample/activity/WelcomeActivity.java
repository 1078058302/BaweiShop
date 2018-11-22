package com.umeng.soexample.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.soexample.R;
import com.umeng.soexample.mvp.presenter.BaseActivityPresenter;
import com.umeng.soexample.mvp.presenter.WelcomeActivityPresenter;

public class WelcomeActivity extends BaseActivityPresenter<WelcomeActivityPresenter> {

    @Override
    public Class<WelcomeActivityPresenter> getClassDelegate() {
        return WelcomeActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
