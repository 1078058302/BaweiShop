package com.umeng.soexample.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
    private List<View> mViews = new ArrayList<>();

    public MyViewPagerAdapter(List<View> mViews) {
        this.mViews = mViews;
    }

    @Override
    public int getCount() {
        if (mViews == null)
            return 0;
        return mViews.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return (mViews.get(position));
    }

}
