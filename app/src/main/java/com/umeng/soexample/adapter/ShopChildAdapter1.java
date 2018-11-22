package com.umeng.soexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.umeng.soexample.activity.WebviewActivity;
import com.umeng.soexample.model.ShopBean;
import com.umeng.soexample.mvp.view.JisuanView;

import java.util.ArrayList;
import java.util.List;

public class ShopChildAdapter1 extends RecyclerView.Adapter<ShopChildAdapter1.ViewHolder> {
    private Context context;
    private List<ShopBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;

    @NonNull
    @Override
    public ShopChildAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycler_2_1_1, null);
        ViewHolder holder = new ViewHolder(view);
        holder.imageView = view.findViewById(R.id.image_2);
        holder.title = view.findViewById(R.id.title_2);
        holder.desc = view.findViewById(R.id.desc_2);
        holder.linearLayout = view.findViewById(R.id.layout_lin);
        holder.checkBox = view.findViewById(R.id.ck_one);
        holder.price = view.findViewById(R.id.price_car);
        holder.num = view.findViewById(R.id.num_car);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopChildAdapter1.ViewHolder viewHolder, final int i) {
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


//            list.get(i).isStatus()
        viewHolder.checkBox.setChecked(list.get(i).isStatus());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(i).isStatus()) {
                    list.get(i).setStatus(false);
                    listener.callBack(i);
                } else {
                    list.get(i).setStatus(true);
                    listener.callBack(i);
                }
                notifyItemChanged(i);

            }
        });
        String pid = list.get(i).getPid();
        viewHolder.price.setText(list.get(i).getPrice() + "");
        String sellerid = list.get(i).getSellerid();
        String selected = list.get(i).getSelected();
        viewHolder.num.setText(this, list, i, sellerid, pid,selected,position);
        String images = list.get(i).getImages();
        String[] split = images.split("[|]");
//        Picasso.with(context).load(split[0]).fit().into(viewHolder.imageView);
        viewHolder.imageView.setImageURI(Uri.parse(split[0]));
        viewHolder.num.result(new JisuanView.CallBackListener() {
            @Override
            public void callBack() {
                listener.callBack(i);
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

    public void setList(List<ShopBean.DataBean.ListBean> list) {
        this.list = list;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ImageView imageView;
        TextView title;
        TextView desc;
        LinearLayout linearLayout;
        CheckBox checkBox;
        TextView price;
        JisuanView num;
    }

    private CallBackListener listener;

    public void result(CallBackListener listener) {
        this.listener = listener;
    }


    public interface CallBackListener {
        void callBack(int i);
    }
}
