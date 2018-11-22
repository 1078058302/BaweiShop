package com.umeng.soexample.mvp.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.soexample.R;
import com.umeng.soexample.adapter.ShopChildAdapter1;
import com.umeng.soexample.model.ShopBean;
import com.umeng.soexample.model.UpdateBean;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JisuanView extends RelativeLayout implements View.OnClickListener {
    //http://www.zhaoapi.cn/product/updateCarts?uid=71&sellerid=1&pid=1&selected=1&num=10&token=C642430271C66ED4E1B19DEC1748BECC

    private TextView add;
    private TextView jian;
    private EditText jisuan;
    private ShopChildAdapter1 shopChildAdapter1;
    private List<ShopBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;
    private String sellerid;
    private String pid;
    private String selected;
    private int xid;

    public JisuanView(Context context) {
        super(context);
        init(context);
    }

    public JisuanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JisuanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;
    private int num;

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.jisuanview, null);
        add = view.findViewById(R.id.add);
        jian = view.findViewById(R.id.jian);
        jisuan = view.findViewById(R.id.edit_jisuan);
        jian.setOnClickListener(this);
        add.setOnClickListener(this);
        jisuan.setOnClickListener(this);
        addView(view);
    }


    @Override
    public void onClick(View v) {
        String token = SharedPreferencesUtils.getString(context, "token");
        String uid = SharedPreferencesUtils.getString(context, "uid");
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(uid)) {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.add:
                num++;
                dohttp(token, uid, num);
                break;
            case R.id.jian:
                num--;
                if (num < 1) {
                    Toast.makeText(context, "商品数量最少为一", Toast.LENGTH_SHORT).show();
                    return;
                }
                dohttp(token, uid, num);
                break;
            case R.id.edit_jisuan:
                String trim = jisuan.getText().toString().trim();
                dohttp(token, uid, Integer.parseInt(trim));
                break;
        }
    }

    private void dohttp(final String token, final String uid, final int num) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("token", token);
        map.put("pid", pid);
        map.put("selected", selected);
        map.put("sellerid", sellerid);
        map.put("num", num + "");
        new HttpHelper().get("/product/updateCarts", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                UpdateBean updateBean = new Gson().fromJson(data, UpdateBean.class);
                String code = updateBean.getCode();
                if ("0".equals(code)) {
//                    Toast.makeText(context, "加入成功", Toast.LENGTH_SHORT).show();
                    jisuan.setText(num + "");
                    list.get(position).setNum(num);
                    listener.callBack();
                    shopChildAdapter1.notifyItemChanged(position);
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    public void setText(ShopChildAdapter1 shopChildAdapter1, List<ShopBean.DataBean.ListBean> list, int i, String sellerid, String pid, String selected, int position) {
        this.shopChildAdapter1 = shopChildAdapter1;
        this.list = list;
        this.position = i;
        this.sellerid = sellerid;
        this.pid = pid;
        this.selected = selected;
        this.xid = position;
        num = list.get(i).getNum();
        jisuan.setText(list.get(i).getNum() + "");
//        Toast.makeText(context, sellerid, Toast.LENGTH_SHORT).show();
    }

    private CallBackListener listener;

    public void result(CallBackListener listener) {
        this.listener = listener;
    }


    public interface CallBackListener {
        void callBack();
    }
}
