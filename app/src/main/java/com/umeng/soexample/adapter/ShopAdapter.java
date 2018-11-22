package com.umeng.soexample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umeng.soexample.R;
import com.umeng.soexample.model.NineBean;
import com.umeng.soexample.model.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<ShopBean.DataBean> list = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycler_2, null);
        ViewHolder holder = new ViewHolder(view);
        holder.recyclerView = view.findViewById(R.id.recycler2_1);
        holder.textView = view.findViewById(R.id.text_2);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(list.get(i).getSellerName());
        ShopChildAdapter shopChildAdapter = new ShopChildAdapter();
        shopChildAdapter.setContext(context);
        List<ShopBean.DataBean.ListBean> shopchild = this.list.get(i).getList();
        shopChildAdapter.setList(shopchild);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(manager);
        viewHolder.recyclerView.setAdapter(shopChildAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<ShopBean.DataBean> list) {
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
