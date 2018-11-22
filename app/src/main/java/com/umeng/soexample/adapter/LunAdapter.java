package com.umeng.soexample.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.umeng.soexample.R;
import com.umeng.soexample.model.HomeIconInfo;

import java.util.ArrayList;
import java.util.List;

public class LunAdapter extends BaseAdapter {
    private List<HomeIconInfo> infos;
    private LayoutInflater inflater;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize;
    private Context context;

    public LunAdapter(Context context, List<HomeIconInfo> infos, int curIndex, int pageSize) {
        inflater = LayoutInflater.from(context);
        this.infos = infos;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
        this.context = context;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页,如果够，则直接返回每一页显示的最大条目个数pageSize,如果不够，则有几项就返回几,(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return infos.size() > (curIndex + 1) * pageSize ? pageSize : (infos.size() - curIndex * pageSize);

    }

    @Override
    public Object getItem(int position) {
        return infos.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.gridview_item, null);
            holder.imageView = convertView.findViewById(R.id.iv);
            holder.textView = convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        Picasso.with(context).load(mPagerOneData.get(position).getIconPic()).fit().into(holder.imageView);
        int pos = position + curIndex * pageSize;
//        Glide.with(context).load(infos.get(pos).getIconPic()).into(holder.imageView);
        holder.imageView.setImageURI(Uri.parse(infos.get(pos).getIconPic()));
        holder.textView.setText(infos.get(pos).getIconName());
        return convertView;
    }


    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
