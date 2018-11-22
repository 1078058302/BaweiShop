package com.umeng.soexample.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.soexample.R;
import com.umeng.soexample.mvp.presenter.BaseActivityPresenter;
import com.umeng.soexample.mvp.presenter.FindActivityPresenter;

public class FindActivity extends BaseActivityPresenter<FindActivityPresenter> {

    @Override
    public Class<FindActivityPresenter> getClassDelegate() {
        return FindActivityPresenter.class;
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
