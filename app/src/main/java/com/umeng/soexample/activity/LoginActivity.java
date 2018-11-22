package com.umeng.soexample.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.soexample.R;
import com.umeng.soexample.mvp.presenter.BaseActivityPresenter;
import com.umeng.soexample.mvp.presenter.LoginActivityPresenter;

public class LoginActivity extends BaseActivityPresenter<LoginActivityPresenter> {

    @Override
    public Class<LoginActivityPresenter> getClassDelegate() {
        return LoginActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
