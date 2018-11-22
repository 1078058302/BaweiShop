package com.umeng.soexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.umeng.soexample.activity.ShezhiActivity;
import com.umeng.soexample.model.AccountBean;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author:AbnerMing
 * date:2018/10/22
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {
    private List<AccountBean> listBean = new ArrayList<>();
    private Context context;

    public AccountAdapter(Context context, List<AccountBean> listBean) {
        this.context = context;
        this.listBean = listBean;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.account_adapter, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mAccountTitle.setText(listBean.get(i).getName());
        myViewHolder.mLeftImage.setImageResource(listBean.get(i).getImage());
        String name = listBean.get(i).getName();
//        if (name.equals("退出登录")) {
//            myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    listener.success();
//                }
//            });
//        }
        if (name.equals("设置")) {
            myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShezhiActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listBean.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mAccountTitle;
        ImageView mLeftImage;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mLeftImage = (ImageView) itemView.findViewById(R.id.account_left_image);
            mAccountTitle = (TextView) itemView.findViewById(R.id.account_title);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rela);
        }
    }

    private AdapterListener listener;

    public void result(AdapterListener listener) {
        this.listener = listener;
    }

    public interface AdapterListener {
        void success();
    }
}
