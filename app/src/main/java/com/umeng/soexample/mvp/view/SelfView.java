package com.umeng.soexample.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.umeng.soexample.activity.ShowActivity;

import java.util.List;

public class SelfView extends RelativeLayout {

    private LinearLayout mLayout;
    private List<String> listData;

    public SelfView(Context context) {
        super(context);
        init(context);
    }

    public SelfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.self_view, null);
        mLayout = view.findViewById(R.id.self_all);
        addView(view);
    }

    private LinearLayout view;
    private int position = 0;

    public void setList() {
        mLayout.removeAllViews();
        view = (LinearLayout) View.inflate(context, R.layout.self_view_h, null);
        int len = 0;
        mLayout.addView(view);
        view.removeAllViews();
        for (int i = 0; i < listData.size(); i++) {
            position = i;
            final String msg = listData.get(i);
            len = len + msg.length();
            if (len > 20) {
                view = (LinearLayout) View.inflate(context, R.layout.self_view_h, null);
                mLayout.addView(view);
                len = 0;
            }
            final View view1 = View.inflate(context, R.layout.self_view_txt, null);
            final TextView textView = view1.findViewById(R.id.tv_txt);
            textView.setText(msg);
            view.addView(view1);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view1.getLayoutParams();
            params.weight = 1;
            params.leftMargin = 10;
            params.rightMargin = 10;
            params.topMargin = 10;
            view1.setLayoutParams(params);

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, textView.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ShowActivity.class);
                    intent.putExtra("tran", msg);
                    context.startActivity(intent);
                }
            });
            textView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    view.removeView(view1);
                    listData.remove(position);
                    setList();
                    return true;
                }
            });
        }


    }

    public void setListData(List<String> listData) {
        this.listData = listData;
        if (!listData.isEmpty()) {
            setList();
        }
    }

}
