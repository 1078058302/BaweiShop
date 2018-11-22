package com.umeng.soexample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.umeng.soexample.model.AddressBean;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private List<AddressBean.DataBean> list = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.address_item, null);
        ViewHolder holder = new ViewHolder(view);
        holder.address = view.findViewById(R.id.address_a);
        holder.mobile = view.findViewById(R.id.mobile_a);
        holder.name = view.findViewById(R.id.name_a);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.address.setText(list.get(i).getAddr());
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.mobile.setText(list.get(i).getMobile() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<AddressBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView name;
        TextView address;
        TextView mobile;
    }

}
