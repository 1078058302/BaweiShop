package com.umeng.soexample.fragment;

import android.content.Context;

import com.umeng.soexample.R;
import com.umeng.soexample.mvp.presenter.BaseFragmentPresenter;
import com.umeng.soexample.mvp.presenter.FourFragmentPresenter;

public class FourFragment extends BaseFragmentPresenter<FourFragmentPresenter> {
    @Override
    public Class<FourFragmentPresenter> getClassDelegate() {
        return FourFragmentPresenter.class;
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
}
