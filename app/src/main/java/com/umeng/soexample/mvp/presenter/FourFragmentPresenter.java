package com.umeng.soexample.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.LoginActivity;
import com.umeng.soexample.adapter.AccountAdapter;
import com.umeng.soexample.model.AccountBean;
import com.umeng.soexample.model.SuccessBean;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FourFragmentPresenter extends AppDelegate {
    private List<AccountBean> listBean = new ArrayList<>();
    private RecyclerView recyclerView;
    private RelativeLayout userLayout;
    private TextView userNick;
    private SimpleDraweeView imageView;


    @Override
    public int getLayoutId() {
        return R.layout.layout_four;
    }

    @Override
    public void initData() {
        super.initData();
        setData();
        recyclerView = get(R.id.account_recycler);
        AccountAdapter accountAdapter = new AccountAdapter(context, listBean);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        imageView = get(R.id.image_logo);
        recyclerView.setAdapter(accountAdapter);
        userLayout = get(R.id.user_info);
        userNick = get(R.id.user_nick);

        userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = SharedPreferencesUtils.getString(context, "user_name");
                if (TextUtils.isEmpty(userName)) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else {
                    toast("您已登录过,若想登录其他账号,请退出登录");
                }
            }
        });
//        accountAdapter.result(new AccountAdapter.AdapterListener() {
//            @Override
//            public void success() {
//
//            }
//        });
    }

    @Override
    public void toast(String content) {
        super.toast(content);
    }

    private void setData() {
        AccountBean bean = new AccountBean();
        bean.setImage(R.drawable.car);
        bean.setName("物流");
        listBean.add(bean);
        bean = new AccountBean();
        bean.setImage(R.drawable.order);
        bean.setName("订单");
        listBean.add(bean);
        bean = new AccountBean();
        bean.setImage(R.drawable.quan);
        bean.setName("优惠券");
        listBean.add(bean);
        bean = new AccountBean();
        bean.setImage(R.drawable.shared);
        bean.setName("分享");
        listBean.add(bean);
        bean = new AccountBean();
        bean.setImage(R.drawable.feed_back);
        bean.setName("反馈");
        listBean.add(bean);
        bean = new AccountBean();
        bean.setImage(R.drawable.setting);
        bean.setName("设置");
        listBean.add(bean);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        String userName = SharedPreferencesUtils.getString(context, "user_name");
        String user_nickname = SharedPreferencesUtils.getString(context, "user_nickname");
        String replace = SharedPreferencesUtils.getString(context, "replace");
        String icon = SharedPreferencesUtils.getString(context, "icon");
        Toast.makeText(context, replace + "" + icon, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(icon)) {
            imageView.setImageURI(Uri.parse(icon));
        }
        if (!TextUtils.isEmpty(replace)) {
            imageView.setImageURI(Uri.parse(replace));
        }
        if (!TextUtils.isEmpty(userName)) {
            if (user_nickname != null) {
                userNick.setText(user_nickname);
            } else {
                userNick.setText(userName);
            }
        } else {
            userNick.setText("登录");
        }
    }
}
