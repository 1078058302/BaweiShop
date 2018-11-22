package com.umeng.soexample.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface IDelegate {
    void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle);

    //创建初始化
    void initData();

    View rootView();
}
