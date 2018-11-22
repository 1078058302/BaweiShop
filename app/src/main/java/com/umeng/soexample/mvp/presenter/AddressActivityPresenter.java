package com.umeng.soexample.mvp.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.soexample.R;
import com.umeng.soexample.adapter.AddressAdapter;
import com.umeng.soexample.model.AddressBean;
import com.umeng.soexample.model.UpdateBean;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressActivityPresenter extends AppDelegate {

    private String uid;
    private AlertDialog.Builder builder;
    private String token;
    private RecyclerView recyclerView;
    private AddressAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    public void initData() {
        super.initData();
        builder = new AlertDialog.Builder(context);
        uid = SharedPreferencesUtils.getString(context, "uid");
        token = SharedPreferencesUtils.getString(context, "token");
        recyclerView = get(R.id.recycler7);
        adapter = new AddressAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        get(R.id.addAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(context, R.layout.layout_address, null);
                final EditText address = view.findViewById(R.id.address);
                final EditText mobile = view.findViewById(R.id.mb);
                final EditText name = view.findViewById(R.id.name);
                builder.setTitle("设置收货地址")
                        .setIcon(R.drawable.address)
                        .setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String as = address.getText().toString().trim();
                                String mb = mobile.getText().toString().trim();
                                String na = name.getText().toString().trim();
                                doHttp1(as, mb, na);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
            }
        });
        doHttp();
    }

    private void doHttp1(String as, String mb, String na) {

        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("addr", as);
        map.put("mobile", mb);
        map.put("name", na);
        map.put("token", token);
        new HttpHelper().get("/user/addAddr", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                UpdateBean updateBean = new Gson().fromJson(data, UpdateBean.class);
                String msg = updateBean.getMsg();
                toast(msg);
                doHttp();
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void doHttp() {
//        https://www.zhaoapi.cn/user/getAddrs?uid=71

        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        new HttpHelper().get("/user/getAddrs", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                AddressBean addressBean = new Gson().fromJson(data, AddressBean.class);
                List<AddressBean.DataBean> data1 = addressBean.getData();
                if (data1.isEmpty()) {
                    toast("请添加收货地址");
                    return;
                } else {
                    adapter.setList(data1);
                    adapter.setContext(context);

                }
            }

            @Override
            public void fail(String error) {

            }
        });
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

    }
}
