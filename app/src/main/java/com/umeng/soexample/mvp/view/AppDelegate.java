package com.umeng.soexample.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tapadoo.alerter.Alerter;
import com.umeng.soexample.R;

public abstract class AppDelegate implements IDelegate {
    private int layoutId;
    private View rootView;
    private SparseArray<View> views = new SparseArray<>();
    public Context context;

    public <T extends View> T get(int id) {
        T view = (T) views.get(id);
        if (view == null) {
            view = rootView.findViewById(id);
            views.put(id, view);
        }
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        rootView = inflater.inflate(getLayoutId(), viewGroup, false);
    }

    @Override
    public View rootView() {
        return rootView;
    }

    public abstract int getLayoutId();

    public void destroy() {
        rootView = null;
    }

    public void onStart() {

    }

    ;

    public void onStop() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onDestroy() {

    }

    public void onResume() {

    }

    private Alerter alerter;

    public void toast(String content) {

        if (alerter == null) {
            alerter = Alerter.create((Activity) context);
        }
        alerter.
                setText(content).
                setDuration(3000).
                setBackgroundColor(R.color.colorPrimary).
                show();

    }
}