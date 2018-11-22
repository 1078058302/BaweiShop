package com.umeng.soexample.fragment;

import android.content.Context;

import com.umeng.soexample.mvp.presenter.BaseFragmentPresenter;
import com.umeng.soexample.mvp.presenter.FiveFragmentPresenter;

public class FiveFragment extends BaseFragmentPresenter<FiveFragmentPresenter> {
    @Override
    public Class<FiveFragmentPresenter> getClassDelegate() {
        return FiveFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
