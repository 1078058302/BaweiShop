package com.umeng.soexample.mvp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.soexample.R;

/**
 * author:AbnerMing
 * date:2018/10/22
 * 头部信息
 */
public class HeadView extends RelativeLayout {
    private boolean isShow;
    private String title;

    public HeadView(Context context) {
        super(context);
        init(context);
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ty = context.obtainStyledAttributes(attrs, R.styleable.HeadView);
        title = ty.getString(R.styleable.HeadView_title);
        isShow = ty.getBoolean(R.styleable.HeadView_show, true);
        ty.recycle();
        init(context);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        View view = View.inflate(context, R.layout.layout_back, null);
        TextView titleTxt = (TextView) view.findViewById(R.id.head_title);
        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }
        RelativeLayout layoutBack = (RelativeLayout) view.findViewById(R.id.layout_back);
        if (isShow) {
            layoutBack.setVisibility(VISIBLE);
        } else {
            layoutBack.setVisibility(GONE);
        }
        layoutBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) context).finish();//关闭
            }
        });
        addView(view);
    }
}
