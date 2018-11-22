package com.umeng.soexample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.umeng.soexample.model.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter1 extends RecyclerView.Adapter<ShopAdapter1.ViewHolder> {

    private List<ShopBean.DataBean> list = new ArrayList<>();
    private Context context;
    private int position;
    private boolean b = true;

    @NonNull
    @Override
    public ShopAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recycler_2_1_, null);
        ViewHolder holder = new ViewHolder(view);
        holder.recyclerView = view.findViewById(R.id.recycler2_1);
        holder.textView = view.findViewById(R.id.text_2);
        holder.checkBox = view.findViewById(R.id.check);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopAdapter1.ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(list.get(i).getSellerName());
        ShopChildAdapter1 shopChildAdapter1 = new ShopChildAdapter1();
        shopChildAdapter1.setContext(context);
        List<ShopBean.DataBean.ListBean> shopchild = this.list.get(i).getList();
        shopChildAdapter1.setList(shopchild);
        String sellerid = list.get(i).getSellerid();
        shopChildAdapter1.setPosition(i);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(manager);
        viewHolder.recyclerView.setAdapter(shopChildAdapter1);
        viewHolder.checkBox.setChecked(list.get(i).isIsbool());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(i).isIsbool()) {
                    list.get(i).setIsbool(false);
                } else {
                    list.get(i).setIsbool(true);
                }
                List<ShopBean.DataBean.ListBean> list1 = ShopAdapter1.this.list.get(i).getList();
                for (int j = 0; j < list1.size(); j++) {
                    if (list1.get(j).isStatus()) {
                        list1.get(j).setStatus(false);
                    } else {
                        list1.get(j).setStatus(true);
                    }
                }
                listener.callBack(list, 0, i);
                notifyItemChanged(i);
            }
        });
        shopChildAdapter1.result(new ShopChildAdapter1.CallBackListener() {
            @Override
            public void callBack(int position) {
                listener.callBack(list, position, i);
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
        CheckBox checkBox;
    }

    private ShopCallBackListener listener;

    public void result(ShopCallBackListener listener) {
        this.listener = listener;
    }

    public interface ShopCallBackListener {
        void callBack(List<ShopBean.DataBean> list, int position, int xid);
    }
}
