package com.umeng.soexample.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.LoginActivity;
import com.umeng.soexample.model.RegBean;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.net.Helper;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegActivityPresenter extends AppDelegate implements View.OnClickListener {

//    String APPKEY = "101732155b605";
//    String APPSECRETE = "69d1850f4b74100266ab576b64e6cb16";

    // 手机号输入框
    private EditText inputPhoneEt;

    // 验证码输入框
    private EditText inputCodeEt;

    // 获取验证码按钮
    private Button requestCodeBtn;

    // 注册按钮
    private Button commitBtn;

    //注册网址
//    private String url = "http://www.zhaoapi.cn";
    //
    int i = 30;
    private EditText pass;
    private String passCode;
    private String phoneCode;


    @Override
    public int getLayoutId() {
        return R.layout.reg;
    }

    @Override
    public void initData() {
        super.initData();

        init();
    }

    private void init() {
        inputPhoneEt = (EditText) get(R.id.login_input_phone_et);
        inputCodeEt = (EditText) get(R.id.login_input_code_et);
        pass = get(R.id.user_pass);
        requestCodeBtn = (Button) get(R.id.login_request_code_btn);
        commitBtn = (Button) get(R.id.login_commit_btn);
        requestCodeBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);

        // 启动短信验证sdk
//        SMSSDK.initSDK(this, APPKEY, APPSECRETE);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        String phoneNums = inputPhoneEt.getText().toString();
        switch (v.getId()) {
            case R.id.login_request_code_btn:
                // 1. 通过规则判断手机号
                if (!judgePhoneNums(phoneNums)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                requestCodeBtn.setClickable(false);
                requestCodeBtn.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;

            case R.id.login_commit_btn:


                //将收到的验证码和手机号提交再次核对
                SMSSDK.submitVerificationCode("86", phoneNums, inputCodeEt
                        .getText().toString());
//                    createProgressBar();
                String code = inputCodeEt.getText().toString().trim();
                String phone1 = inputPhoneEt.getText().toString().trim();
                String pass1 = this.pass.getText().toString().trim();
                if (TextUtils.isEmpty(phone1)) {
//                    Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show();
                    toast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(pass1)) {
//                    Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                    toast("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
//                    Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
                    toast("请输入验证码");
                    return;
                }


                break;
        }
    }

    private void doHttp(final String passCode, final String phoneCode) {
        ///user/reg?mobile=
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phoneCode);
        map.put("password", passCode);
        new HttpHelper().get("/user/reg", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                SharedPreferencesUtils.putString(context, "passCode", passCode);
                SharedPreferencesUtils.putString(context, "phoneCode", phoneCode);
//                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                RegBean regBean = new Gson().fromJson(data, RegBean.class);
                String code = regBean.getCode();
                if (code.equals("0")) {
//                    Toast.makeText(context, regBean.getMsg(), Toast.LENGTH_SHORT).show();
                    toast(regBean.getMsg());
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("phoneCode", phoneCode);
                    intent.putExtra("passCode", passCode);
                    context.startActivity(intent);
                } else {
//                    Toast.makeText(context, regBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("phoneCode", phoneCode);
                    intent.putExtra("passCode", passCode);
                    context.startActivity(intent);
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    /**
     *
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                requestCodeBtn.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                requestCodeBtn.setText("获取验证码");
                requestCodeBtn.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        passCode = pass.getText().toString().trim();
                        phoneCode = inputPhoneEt.getText().toString().trim();
//                        Toast.makeText(context, "提交验证码成功",
//                                Toast.LENGTH_SHORT).show();
                        toast("提交验证码成功");
                        doHttp(passCode, phoneCode);
//                        Intent intent = new Intent(context,
//                                MainActivity.class);
//                        context.startActivity(intent);
//                        ((RegActivity) context).finish();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(context, "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    public void toast(String content) {
        super.toast(content);
    }

    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
//        Toast.makeText(context, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        toast("手机号码输入有误！");
        return false;
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * progressbar
     */
    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) get(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar mProBar = new ProgressBar(context);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }

    @Override
    public void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }


    public void setContext(Context context) {
        this.context = context;

    }

}
