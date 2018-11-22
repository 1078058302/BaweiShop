package com.umeng.soexample.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;
import com.umeng.soexample.R;
import com.umeng.soexample.fragment.ThreeFragment;
import com.umeng.soexample.model.AddCarBean;
import com.umeng.soexample.model.DetailsBean;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

public class WebviewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWeb;
    private RelativeLayout layoutCar;
    private String url;
    private WebSettings settings;
    private String pid;
    private ImageView shopImage;
    private TextView shopPrice, shopTitle;
    private DetailsBean bean;
    private int sellerid;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWeb = findViewById(R.id.webview);
        layoutCar = findViewById(R.id.layout_progress);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        pid = intent.getStringExtra("pid");
        try {
            setData(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doHttp1();
        shopImage = (ImageView) findViewById(R.id.shop_image);
        shopPrice = (TextView) findViewById(R.id.shop_price);
        shopTitle = (TextView) findViewById(R.id.shop_title);
        findViewById(R.id.left_back).setOnClickListener(this);
        findViewById(R.id.shop_close).setOnClickListener(this);
        findViewById(R.id.sure).setOnClickListener(this);
        layout = findViewById(R.id.layout_shop_add_car);
        findViewById(R.id.backcar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backLogin = SharedPreferencesUtils.getString(WebviewActivity.this, "backLogin");
                if (backLogin.equals("2")) {
                    toast("请先登录");
                }
                SharedPreferencesUtils.putString(WebviewActivity.this, "webtype", "0");
                finish();

            }
        });
        findViewById(R.id.add_shop_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShopCar();
            }
        });
    }

    private void addCar() {
        String token = SharedPreferencesUtils.getString(WebviewActivity.this, "token");
        String uid = SharedPreferencesUtils.getString(WebviewActivity.this, "uid");
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(uid)) {
            toast("请先登录");
        } else {
            doHttp(pid, token, uid);
        }
    }

    public void doHttp1() {
        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        new HttpHelper().get("/product/getProductDetail", map).result(new HttpListener() {
            @Override
            public void success(String data) {
//                Toast.makeText(WebviewActivity.this, data, Toast.LENGTH_SHORT).show();
                bean = new Gson().fromJson(data, DetailsBean.class);
                sellerid = bean.getSeller().getSellerid();
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void doHttp(String pid, String token, String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        map.put("token", token);
        map.put("uid", uid);
        new HttpHelper().get("/product/addCart", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                AddCarBean addCarBean = new Gson().fromJson(data, AddCarBean.class);
                String msg = addCarBean.getMsg();
                toast(msg);
//                "加购成功".equals(msg)
                if ("0".equals(addCarBean.getCode())) {
                    SharedPreferencesUtils.putString(WebviewActivity.this, "addSuccess", "4");
                    SharedPreferencesUtils.putString(WebviewActivity.this, "addstate", "0");
                    hintShopCar();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void setData(String url) throws Exception {
        settings = mWeb.getSettings();
        settings.setUseWideViewPort(true);//设置加载进来的页面自适应屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setUseWideViewPort(false);//禁止webview做自动缩放
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSupportMultipleWindows(false);
        settings.setAppCachePath(getDir("cache", Context.MODE_PRIVATE).getPath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWeb.setFocusable(true);
        mWeb.requestFocus();
        mWeb.setWebChromeClient(new WebChromeClient());  //解决android与H5协议交互,弹不出对话框问题
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //页面加载完成之后
                layoutCar.setVisibility(View.GONE);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWeb.loadUrl(url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return true;

            }
        });
        mWeb.loadUrl(url);
    }

    private Alerter alerter;

    public void toast(String content) {

        if (alerter == null) {
            alerter = Alerter.create(this);
        }
        alerter.
                setText(content).
                setDuration(3000).
                setBackgroundColor(R.color.colorPrimary).
                show();

    }

    //显示
    private void showShopCar() {
        int height = this.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, "translationY", height, 600);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        layout.setVisibility(View.VISIBLE);
        if (bean != null) {
            shopTitle.setText(bean.getData().getTitle());
            shopPrice.setText(bean.getData().getPrice() + "");
            Picasso.with(this).load(bean.getData().getImages().split("\\|")[0]).fit().into(shopImage);
        }
    }

    //关闭
    private void hintShopCar() {
        int height = this.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, "translationY", 600, height);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setVisibility(View.GONE);
            }
        }, 1000);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_shop_car://添加购物车
                showShopCar();
                break;
            case R.id.shop_close://关闭
                hintShopCar();
                break;
            case R.id.sure://确定添加
                addCar();
                break;
        }
    }
}
