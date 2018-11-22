package com.umeng.soexample.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.ShowActivity;
import com.umeng.soexample.adapter.ShopChildAdapter2;
import com.umeng.soexample.model.FindBean;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowActivityPresenter extends AppDelegate {

    private ShopChildAdapter2 adapter2;
    private RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show;
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = ((ShowActivity) context).getIntent();
        String tran = intent.getStringExtra("tran");
        recyclerView = get(R.id.recycler6);
        adapter2 = new ShopChildAdapter2();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter2);
        doHttp(tran);
    }

    private void doHttp(String tran) {
        //https://www.zhaoapi.cn/product/searchProducts?keywords=笔记本&page=1
        Map<String, String> map = new HashMap<>();
        map.put("keywords", tran);
        map.put("page", "1");
        new HttpHelper().get("/product/searchProducts", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (TextUtils.isEmpty(data)) {
                    Toast.makeText(context, "暂无该商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                FindBean findBean = new Gson().fromJson(data, FindBean.class);
                List<FindBean.DataBean> data1 = findBean.getData();
                adapter2.setContext(context);
                adapter2.setList(data1);

            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
