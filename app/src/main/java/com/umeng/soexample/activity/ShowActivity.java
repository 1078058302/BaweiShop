package com.umeng.soexample.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.soexample.mvp.presenter.BaseActivityPresenter;
import com.umeng.soexample.mvp.presenter.ShowActivityPresenter;

public class ShowActivity extends BaseActivityPresenter<ShowActivityPresenter> {

    @Override
    public Class<ShowActivityPresenter> getClassDelegate() {
        return ShowActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
