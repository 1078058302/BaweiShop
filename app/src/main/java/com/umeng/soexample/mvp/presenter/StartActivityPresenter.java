package com.umeng.soexample.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.soexample.MainActivity;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.StartActivity;
import com.umeng.soexample.activity.WelcomeActivity;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.utils.SharedPreferencesUtils;
import com.umeng.soexample.utils.UltimateBar;

public class StartActivityPresenter extends AppDelegate implements View.OnClickListener {
    private TextView time;
    private int t = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String frist = SharedPreferencesUtils.getString(context, "frist");
                if (TextUtils.isEmpty(frist)) {
                    t--;
                    time.setText(t + "s");
                    if (t < 3 && t > 0) {
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else if (t == 0) {
                        start(WelcomeActivity.class);
                        ((StartActivity) context).finish();
                        handler.removeCallbacksAndMessages(null);
                    }
                } else {
                    t--;
                    time.setText(t + "s");
                    if (t < 3 && t > 0) {
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else if (t == 0) {
                        start(MainActivity.class);
                        ((StartActivity) context).finish();
                        handler.removeCallbacksAndMessages(null);
                    }
                }
            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initData() {
        super.initData();

        UltimateBar.newImmersionBuilder().applyNav(false).build((Activity) context).apply();
        get(R.id.start_continue).setOnClickListener(this);
        time = get(R.id.start_time);
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    private void start(Class cls) {
        context.startActivity(new Intent(context, cls));
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_continue:
                String frist = SharedPreferencesUtils.getString(context, "frist");
                if (TextUtils.isEmpty(frist)) {
                    start(WelcomeActivity.class);
                    ((StartActivity) context).finish();
                    handler.removeCallbacksAndMessages(null);
                } else {
                    start(MainActivity.class);
                    ((StartActivity) context).finish();
                    handler.removeCallbacksAndMessages(null);
                }

                break;
        }
    }

}
