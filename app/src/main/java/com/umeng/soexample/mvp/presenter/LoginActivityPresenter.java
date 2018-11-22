package com.umeng.soexample.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.LoginActivity;
import com.umeng.soexample.activity.RegActivity;
import com.umeng.soexample.model.SuccessBean;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.net.Helper;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivityPresenter extends AppDelegate {

    private String phoneCode;
    private String passCode;
    //    private String url = "http://www.zhaoapi.cn";
    private EditText username;
    private EditText userpass;
    private String name;
    private String pass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();
        get(R.id.user_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RegActivity.class));
            }
        });
        phoneCode = SharedPreferencesUtils.getString(context, "phoneCode");
        passCode = SharedPreferencesUtils.getString(context, "passCode");
        username = get(R.id.user_name);
        userpass = get(R.id.user_pass);
//        username.setText(phoneCode);
//        userpass.setText(passCode);
        get(R.id.user_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = username.getText().toString().trim();
                pass = userpass.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                doHttp(name, pass);
                SharedPreferencesUtils.putString(context, "successLogin", "3");
            }
        });
    }

    private void doHttp(String phoneCode, final String passCode) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phoneCode);
        map.put("password", passCode);
        new HttpHelper().get("/user/login", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                SuccessBean successBean = new Gson().fromJson(data, SuccessBean.class);
                String msg = "";
                if (successBean.getCode().equals("0")) {
                    msg = successBean.getMsg();
                    SuccessBean.DataBean data1 = successBean.getData();
                    String icon = data1.getIcon();
                    String replace = icon.replace("https", "http");
                    SharedPreferencesUtils.putString(context, "user_id", String.valueOf(data1.getUid()));
                    SharedPreferencesUtils.putString(context, "user_nickname", data1.getNickname());
                    SharedPreferencesUtils.putString(context, "token", data1.getToken());
                    SharedPreferencesUtils.putString(context, "uid", String.valueOf(data1.getUid()));
                    SharedPreferencesUtils.putString(context, "user_name", data1.getUsername());
                    SharedPreferencesUtils.putString(context, "icon", replace);
                    SharedPreferencesUtils.putString(context, "user_pass", pass);
                    toast(msg);
                }
                if (successBean.getCode().equals("1")) {
                    msg = successBean.getMsg();
                    toast(msg);
                    return;
                }
                SharedPreferencesUtils.putString(context, "msg", msg);
                ((LoginActivity) context).finish();
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
}
