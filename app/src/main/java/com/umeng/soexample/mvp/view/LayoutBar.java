package com.umeng.soexample.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.umeng.soexample.R;
import com.umeng.soexample.activity.FindActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;

public class LayoutBar extends LinearLayout {
    public LayoutBar(Context context) {
        super(context);
        init(context);
    }

    public LayoutBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LayoutBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        View view = View.inflate(context, R.layout.layout_bar, null);
        ImageView sys = view.findViewById(R.id.sys);
        view.findViewById(R.id.edit_sousuo).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FindActivity.class);
                context.startActivity(intent);
            }
        });
        sys.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CaptureActivity.class);
                context.startActivity(intent);
            }
        });
        addView(view);
    }
}
