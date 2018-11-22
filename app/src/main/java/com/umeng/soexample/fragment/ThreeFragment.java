package com.umeng.soexample.fragment;

import android.content.Context;

import com.umeng.soexample.mvp.presenter.BaseFragmentPresenter;
import com.umeng.soexample.mvp.presenter.FourFragmentPresenter;
import com.umeng.soexample.mvp.presenter.ThreeFragmentPresenter;

public class ThreeFragment extends BaseFragmentPresenter<ThreeFragmentPresenter> {
    @Override
    public Class<ThreeFragmentPresenter> getClassDelegate() {
        return ThreeFragmentPresenter.class;
    }

    @Override
    public void getContext(Context context) {
        super.getContext(context);
        delegate.setContext(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.onResume();
    }

    public void refresh() {
        if (delegate != null) {
            delegate.onResume();
        }
    }
}
