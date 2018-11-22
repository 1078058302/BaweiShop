package com.umeng.soexample.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.soexample.R;
import com.umeng.soexample.adapter.ShopLeftAdapter;
import com.umeng.soexample.adapter.ShopRight1Adapter;
import com.umeng.soexample.model.ShopLeftBean;
import com.umeng.soexample.model.ShopRightBean;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.net.Helper;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoFragmentPresenter extends AppDelegate {

    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private String url = "http://www.zhaoapi.cn/product/getCatagory";
    private List<ShopLeftBean.DataBean> data1 = new ArrayList<>();
    private ShopRight1Adapter adapter;
    private ShopLeftAdapter adapter1;

    @Override
    public int getLayoutId() {
        return R.layout.layout_two;
    }

    @Override
    public void initData() {
        super.initData();
        recyclerView3 = get(R.id.recycler3);
        recyclerView4 = get(R.id.recycler4);
        //left
        adapter1 = new ShopLeftAdapter();
        adapter1.setRecyclerView(recyclerView4);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(manager);
        recyclerView3.setAdapter(adapter1);
        //right
        StaggeredGridLayoutManager manager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        adapter = new ShopRight1Adapter();
        recyclerView4.setLayoutManager(manager1);
        recyclerView4.setAdapter(adapter);
        doHttp3();
        doHttp4();
    }

    private void doHttp4() {
        //"http://www.zhaoapi.cn/product/getProductCatagory?cid=1"
        Map<String, String> map = new HashMap<>();
        map.put("cid", "1");
        new HttpHelper().get("/product/getProductCatagory", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                ShopRightBean shopRightBean = new Gson().fromJson(data, ShopRightBean.class);
                List<ShopRightBean.DataBean> data2 = shopRightBean.getData();
                adapter.setContext(context);
                adapter.setList(data2);
            }

            @Override
            public void fail(String error) {

            }
        });
    }


    private void doHttp3() {
        //product/getCatagory
        new HttpHelper().get("/product/getCatagory", null).result(new HttpListener() {
            @Override
            public void success(String data) {
                ShopLeftBean shopLeftBean = new Gson().fromJson(data, ShopLeftBean.class);
                data1 = shopLeftBean.getData();

                adapter1.setContext(context);
                adapter1.setList(data1);
//                adapter.notifyDataSetChanged();
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
