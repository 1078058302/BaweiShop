package com.umeng.soexample.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umeng.soexample.R;
import com.umeng.soexample.model.ShopRightBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShopRight2Adapter extends RecyclerView.Adapter<ShopRight2Adapter.ViewHolder> {
    private Context context;
    private List<ShopRightBean.DataBean.ListBean> list = new ArrayList<>();

    @NonNull
    @Override
    public ShopRight2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycler_4_1, null);
        ViewHolder holder = new ViewHolder(view);
        holder.textView = view.findViewById(R.id.shopname_4_1);
        holder.imageView = view.findViewById(R.id.image_4_1);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopRight2Adapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(list.get(i).getName());
//        Picasso.with(context).load(list.get(i).getIcon()).fit().into(viewHolder.imageView);
        viewHolder.imageView.setImageURI(Uri.parse(list.get(i).getIcon()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<ShopRightBean.DataBean.ListBean> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView textView;
        ImageView imageView;
    }
}
