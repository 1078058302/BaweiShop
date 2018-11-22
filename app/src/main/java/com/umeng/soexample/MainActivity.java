package com.umeng.soexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.soexample.mvp.presenter.BaseActivityPresenter;
import com.umeng.soexample.mvp.presenter.MainActivityPresenter;

public class MainActivity extends BaseActivityPresenter<MainActivityPresenter> {

    @Override
    public Class<MainActivityPresenter> getClassDelegate() {
        return MainActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        delegate.onResume();
    }
}
