package com.umeng.soexample.fragment;

import android.content.Context;

import com.umeng.soexample.mvp.presenter.BaseFragmentPresenter;
import com.umeng.soexample.mvp.presenter.OneFragmentPresenter;

public class OneFragment extends BaseFragmentPresenter<OneFragmentPresenter> {
    @Override
    public Class<OneFragmentPresenter> getClassDelegate() {
        return OneFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }

}
