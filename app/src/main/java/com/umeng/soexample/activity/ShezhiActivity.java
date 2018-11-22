package com.umeng.soexample.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.soexample.mvp.presenter.BaseActivityPresenter;
import com.umeng.soexample.mvp.presenter.ShezhiActivityPresenter;

public class ShezhiActivity extends BaseActivityPresenter<ShezhiActivityPresenter> {

    @Override
    public Class<ShezhiActivityPresenter> getClassDelegate() {
        return ShezhiActivityPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        delegate.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        delegate.onResume();
    }
}
