package com.umeng.soexample.mvp.presenter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hjm.bottomtabbar.BottomTabBar;
import com.umeng.soexample.MainActivity;
import com.umeng.soexample.R;
import com.umeng.soexample.fragment.FiveFragment;
import com.umeng.soexample.fragment.FourFragment;
import com.umeng.soexample.fragment.OneFragment;
import com.umeng.soexample.fragment.ThreeFragment;
import com.umeng.soexample.fragment.TwoFragment;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.mvp.view.tabview.TabView;
import com.umeng.soexample.mvp.view.tabview.TabViewChild;
import com.umeng.soexample.utils.SharedPreferencesUtils;
import com.umeng.soexample.utils.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter extends AppDelegate {

    //    private BottomTabBar bottomTabBar;
    private List<TabViewChild> tabViewList = new ArrayList<>();
    private TabView tabView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initData() {
        super.initData();
//        bottomTabBar = get(R.id.bottomtabbar);
//        bottomTabBar.init(((MainActivity) context).getSupportFragmentManager())
//                .setChangeColor(Color.BLUE, Color.BLACK)
//                .setImgSize(50, 50)
//                .addTabItem("首页", R.drawable.index_no, OneFragment.class)
//                .addTabItem("列表", R.drawable.list_no, TwoFragment.class)
//                .addTabItem("发现", R.drawable.look, FiveFragment.class)
//                .addTabItem("购物车", R.drawable.car_no, ThreeFragment.class)
//                .addTabItem("我的", R.drawable.me_no, FourFragment.class);
        tabView = get(R.id.tabview);
        TabViewChild child1 = new TabViewChild(R.drawable.index_yes, R.drawable.index_no, "首页", new OneFragment());
        TabViewChild child2 = new TabViewChild(R.drawable.list_yes, R.drawable.list_no, "列表", new TwoFragment());
        TabViewChild child3 = new TabViewChild(R.drawable.look_no, R.drawable.look, "发现", new FiveFragment());
        TabViewChild child4 = new TabViewChild(R.drawable.car_yes, R.drawable.car_no, "购物车", new ThreeFragment());
        TabViewChild child5 = new TabViewChild(R.drawable.me_yes, R.drawable.me_no, "我的", new FourFragment());
        tabViewList.add(child1);
        tabViewList.add(child2);
        tabViewList.add(child3);
        tabViewList.add(child4);
        tabViewList.add(child5);
        tabView.setTabViewChild(tabViewList, ((MainActivity) context).getSupportFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView imageView, TextView textView) {
                if (position == 3) {
//                    setBar();
                    ThreeFragment fragment = (ThreeFragment) tabViewList.get(3).getmFragment();
                    fragment.refresh();
                }
            }
        });
    }

//    private void setBar() {
//        UltimateBar.newColorBuilder()
//                .statusColor(Color.parseColor("#d43c3c"))   // 状态栏颜色
//                .build((MainActivity) context)
//                .apply();
//    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        String webtype = SharedPreferencesUtils.getString(context, "webtype");
        if ("0".equals(webtype)) {
            SharedPreferencesUtils.putString(context, "webtype", "1");
            tabView.setTabViewDefaultPosition(3);
        }

    }
}
