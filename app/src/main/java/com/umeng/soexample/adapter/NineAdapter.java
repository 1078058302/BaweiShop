package com.umeng.soexample.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umeng.soexample.R;
import com.umeng.soexample.model.NineBean;

import java.util.ArrayList;
import java.util.List;

public class NineAdapter extends RecyclerView.Adapter<NineAdapter.ViewHolder> {

    private List<NineBean.DataBean> list = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public NineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycler_1, null);
        ViewHolder holder = new ViewHolder(view);
        holder.imageView = view.findViewById(R.id.image_1);
        holder.textView = view.findViewById(R.id.text_1);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NineAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(list.get(i).getName());
//        Picasso.with(context).load(list.get(i).getIcon()).fit().into(viewHolder.imageView);
        viewHolder.imageView.setImageURI(Uri.parse(list.get(i).getIcon()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<NineBean.DataBean> list) {
        this.list = list;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ImageView imageView;
        TextView textView;
    }
}
