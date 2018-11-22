package com.umeng.soexample.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.soexample.R;
import com.umeng.soexample.adapter.ShopAdapter1;
import com.umeng.soexample.model.ShopBean;
import com.umeng.soexample.model.SuccessBean;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreeFragmentPresenter extends AppDelegate {

    private RecyclerView recyclerView;
    private List<ShopBean.DataBean> data1 = new ArrayList<>();
    private ShopAdapter1 shopAdapter1;
    private boolean bb = true;
    private double allprice;
    private int allnum;
    private TextView price_all;
    private Button num_all;
    private CheckBox checkBox;
    private TextView bian;
    private String pid;
    private Button delete;
    private boolean click = true;

    @Override
    public int getLayoutId() {
        return R.layout.layout_three;
    }

    @Override
    public void initData() {
        super.initData();
        ///product/getCarts
        init();
        shopAdapter1 = new ShopAdapter1();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(shopAdapter1);
        final String uid = SharedPreferencesUtils.getString(context, "uid");
        final String token = SharedPreferencesUtils.getString(context, "token");
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(token)) {
            toast("请先登录");
        } else {
            doHttp(uid, token);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String trim = num_all.getText().toString().trim();
//                if ("删除".equals(trim)) {
//                    return;
//                }
                if (bb) {
                    SharedPreferencesUtils.putString(context, "threecheck", "5");
                    allCheck(true);
                    bb = false;
                } else {
                    allCheck(false);
                    bb = true;
                }

            }
        });
        bian = get(R.id.bianji);
        delete = get(R.id.delete);

        bian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    bian.setText("完成");
                    price_all.setVisibility(View.GONE);
                    num_all.setVisibility(View.GONE);
                    delete.setVisibility(View.VISIBLE);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            doHttp1(token, uid, pid);
                        }
                    });
                    click = false;
                } else {
                    bian.setText("编辑");
                    price_all.setVisibility(View.VISIBLE);
                    num_all.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.GONE);
                    click = true;
                }

            }
        });
    }

    private void init() {
        recyclerView = get(R.id.recycler5);
        price_all = get(R.id.price_all);
        num_all = get(R.id.jie);
        checkBox = get(R.id.ck_all);
    }

    private void doHttp1(final String token, final String uid, String pid) {
//        https://www.zhaoapi.cn/product/deleteCart?uid=72&pid=1
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("token", token);
        map.put("pid", pid);
        new HttpHelper().get("/product/deleteCart", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                SuccessBean successBean = new Gson().fromJson(data, SuccessBean.class);
                String code = successBean.getCode();
                if (code.equals("0")) {
                    toast("删除成功");
                    doHttp(uid, token);
                    shopAdapter1.notifyDataSetChanged();
                }

            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void allCheck(boolean b) {
        allnum = 0;
        allprice = 0;
        for (int i = 0; i < data1.size(); i++) {
            data1.get(i).setIsbool(bb);
            List<ShopBean.DataBean.ListBean> list = data1.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                list.get(j).setStatus(b);
                int num = list.get(j).getNum();
                double price = list.get(j).getPrice();
                allnum += num;
                allprice = allprice + num * price;
            }
        }
        if (b) {
            price_all.setText(allprice + "");
            num_all.setText("结算(" + allnum + ")");
            checkBox.setChecked(b);
        } else {
            price_all.setText(0 + "");
            num_all.setText("结算(" + 0 + ")");
            checkBox.setChecked(b);
        }
        if (shopAdapter1 != null) {
            shopAdapter1.notifyDataSetChanged();
        }
        SharedPreferencesUtils.putString(context, "threecheck", "4");
    }

    private void doHttp(final String uid, final String token) {
        if (TextUtils.isEmpty(uid)) {
            toast("请先登录");
            data1.clear();
            if (shopAdapter1 != null) {
                shopAdapter1.setList(data1);
                shopAdapter1.setContext(context);
                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(shopAdapter1);
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("uid", uid);
            map.put("token", token);
            try {
                new HttpHelper().get("/product/getCarts", map).result(new HttpListener() {
                    @Override
                    public void success(String data) {
                        if (data.contains("<")) {
                            doHttp(uid, token);
                            return;
                        }
                        if (TextUtils.isEmpty(data)) {
                            toast("您当前并没有想要购买的商品");
                            return;
                        }
                        ShopBean shopBean = new Gson().fromJson(data, ShopBean.class);
                        data1 = shopBean.getData();
                        shopAdapter1.setContext(context);
                        shopAdapter1.setList(data1);

                        method();
                    }

                    @Override
                    public void fail(String error) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void method() {
        if (shopAdapter1 != null) {
            shopAdapter1.result(new ShopAdapter1.ShopCallBackListener() {
                @Override
                public void callBack(List<ShopBean.DataBean> list, int position, int xid) {
                    pid = list.get(xid).getList().get(position).getPid();
                    double allprice = 0;
                    int allnum = 0;
                    int numAll = 0;
                    int num2;
                    for (int i = 0; i < list.size(); i++) {
                        List<ShopBean.DataBean.ListBean> list1 = list.get(i).getList();
                        num2 = 0;
                        for (int j = 0; j < list1.size(); j++) {
                            numAll += list1.get(j).getNum();
                            if (list1.get(j).isStatus()) {
                                num2++;
                                allprice = allprice + (list1.get(j).getPrice() * list1.get(j).getNum());
                                allnum = allnum + list1.get(j).getNum();
                            }
                        }
                        if (list1.size() == num2) {
                            list.get(i).setIsbool(true);
                        } else {
                            list.get(i).setIsbool(false);
                        }
                    }
                    if (allnum < numAll) {
                        checkBox.setChecked(false);
                        bb = true;
                    } else {
                        checkBox.setChecked(true);
                        bb = false;
                    }
                    shopAdapter1.notifyDataSetChanged();
                    price_all.setText(allprice + "");
                    num_all.setText("结算(" + allnum + ")");
                }

            });

        }

        String addSuccess = SharedPreferencesUtils.getString(context, "addSuccess");
        String addstate = SharedPreferencesUtils.getString(context, "addstate");
        if (addSuccess.equals("4") && addstate.equals("0")) {
            SharedPreferencesUtils.putString(context, "addSuccess", "3");
            SharedPreferencesUtils.putString(context, "addstate", "1");
            allCheck(true);
        }

    }

    @Override
    public void toast(String content) {
        super.toast(content);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        String backLogin = SharedPreferencesUtils.getString(context, "backLogin");
        String successLogin = SharedPreferencesUtils.getString(context, "successLogin");
        String addSuccess = SharedPreferencesUtils.getString(context, "addSuccess");
        String check = SharedPreferencesUtils.getString(context, "threecheck");
        String webtype = SharedPreferencesUtils.getString(context, "webtype");
        if (backLogin.equals("2")) {
            SharedPreferencesUtils.putString(context, "backLogin", "1");
            String uid = SharedPreferencesUtils.getString(context, "uid");
            String token = SharedPreferencesUtils.getString(context, "token");
            SharedPreferencesUtils.putString(context, "threecheck", "4");
            doHttp(uid, token);
            allCheck(false);
        }
        if (successLogin.equals("3")) {
            SharedPreferencesUtils.putString(context, "successLogin", "2");
            String uid = SharedPreferencesUtils.getString(context, "uid");
            String token = SharedPreferencesUtils.getString(context, "token");
            doHttp(uid, token);
        }
        if (addSuccess.equals("4")) {
            String uid = SharedPreferencesUtils.getString(context, "uid");
            String token = SharedPreferencesUtils.getString(context, "token");
            doHttp(uid, token);
        }
        if (!addSuccess.equals("4") && check.equals("5")) {
            allCheck(true);
        }
    }
}
