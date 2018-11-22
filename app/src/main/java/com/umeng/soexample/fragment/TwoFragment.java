package com.umeng.soexample.fragment;

import android.content.Context;

import com.umeng.soexample.mvp.presenter.BaseFragmentPresenter;
import com.umeng.soexample.mvp.presenter.TwoFragmentPresenter;

public class TwoFragment extends BaseFragmentPresenter<TwoFragmentPresenter> {
    @Override
    public Class<TwoFragmentPresenter> getClassDelegate() {
        return TwoFragmentPresenter.class;
    }
    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }
}
