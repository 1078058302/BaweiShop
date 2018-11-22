package com.umeng.soexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.WebviewActivity;
import com.umeng.soexample.model.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ShopChildAdapter extends RecyclerView.Adapter<ShopChildAdapter.ViewHolder> {
    private Context context;
    private List<ShopBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;

    @NonNull
    @Override
    public ShopChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycler_2_1, null);
        ViewHolder holder = new ViewHolder(view);
        holder.imageView = view.findViewById(R.id.image_2);
        holder.title = view.findViewById(R.id.title_2);
        holder.desc = view.findViewById(R.id.desc_2);
        holder.linearLayout = view.findViewById(R.id.layout_lin);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopChildAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.desc.setText(list.get(i).getSubhead());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detailUrl = list.get(i).getDetailUrl();
                String pid = list.get(i).getPid();
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("url", detailUrl).putExtra("pid", pid + "");
                context.startActivity(intent);
            }
        });
        String images = list.get(i).getImages();
        String[] split = images.split("[|]");
        viewHolder.imageView.setImageURI(Uri.parse(split[0]));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<ShopBean.DataBean.ListBean> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ImageView imageView;
        TextView title;
        TextView desc;
        LinearLayout linearLayout;
    }
}
