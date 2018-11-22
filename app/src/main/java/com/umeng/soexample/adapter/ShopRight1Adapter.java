package com.umeng.soexample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.umeng.soexample.model.ShopRightBean;

import java.util.ArrayList;
import java.util.List;

public class ShopRight1Adapter extends RecyclerView.Adapter<ShopRight1Adapter.ViewHolder> {
    private Context context;
    private List<ShopRightBean.DataBean> list = new ArrayList<>();

    @NonNull
    @Override
    public ShopRight1Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycler_4, null);
        ViewHolder holder = new ViewHolder(view);
        holder.textView = view.findViewById(R.id.title_4);
        holder.recyclerView = view.findViewById(R.id.recycler4_1);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopRight1Adapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(list.get(i).getName());
        ShopRight2Adapter adapter = new ShopRight2Adapter();
        List<ShopRightBean.DataBean.ListBean> data1 = this.list.get(i).getList();
        adapter.setContext(context);
        adapter.setList(data1);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(manager);
        viewHolder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<ShopRightBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView textView;
        RecyclerView recyclerView;
    }
}
