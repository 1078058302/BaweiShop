package com.umeng.soexample.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.soexample.R;
import com.umeng.soexample.model.ShopLeftBean;
import com.umeng.soexample.model.ShopRightBean;
import com.umeng.soexample.net.Helper;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopLeftAdapter extends RecyclerView.Adapter<ShopLeftAdapter.ViewHolder> {
    private Context context;
    private List<ShopLeftBean.DataBean> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private boolean b = false;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycler_3, null);
        ViewHolder holder = new ViewHolder(view);
        holder.textView = view.findViewById(R.id.shopname);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(list.get(i).getName());
        if (list.get(i).isCheck()) {
            viewHolder.textView.setBackgroundColor(Color.RED);
        } else {
            viewHolder.textView.setBackgroundColor(Color.WHITE);
        }

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < list.size(); j++) {
                    if (i == j) {
                        list.get(j).setCheck(true);
                    } else {
                        list.get(j).setCheck(false);
                    }
                }
                list.get(i).setCheck(true);
                int cid = list.get(i).getCid();
                doHttp4(cid);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<ShopLeftBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView textView;
    }

    private void doHttp4(int cid) {
        //http://www.zhaoapi.cn/product/getProductCatagory?cid=
        Map<String, String> map = new HashMap<>();
        map.put("cid", String.valueOf(cid));
        new HttpHelper().get("/product/getProductCatagory", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data == null) {
                    Toast.makeText(context, "暂无商品", Toast.LENGTH_SHORT).show();
                }
                ShopRightBean shopRightBean = new Gson().fromJson(data, ShopRightBean.class);
                List<ShopRightBean.DataBean> data1 = shopRightBean.getData();
                ShopRight1Adapter adapter = new ShopRight1Adapter();
                adapter.setContext(context);
                adapter.setList(data1);
                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
