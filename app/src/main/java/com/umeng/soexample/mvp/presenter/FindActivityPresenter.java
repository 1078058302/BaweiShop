package com.umeng.soexample.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.umeng.soexample.SQLite.DBDao;
import com.umeng.soexample.activity.ShowActivity;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.mvp.view.SelfView;

import java.util.ArrayList;
import java.util.List;

public class FindActivityPresenter extends AppDelegate {

    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private AutoCompleteTextView textView;
    private List<String> shopname = new ArrayList<>();
    private Button find;
    private SelfView selfView;
    private List<String> strings = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private DBDao dbDao;
    private List<String> select = new ArrayList<>();
    private TextView clear;

    @Override
    public int getLayoutId() {
        return R.layout.activity_find;
    }

    @Override
    public void initData() {
        super.initData();
        textView = get(R.id.autotext);
        listView = get(R.id.listview);
        selfView = get(R.id.self);
        find = get(R.id.find);
        clear = get(R.id.clear);
        dbDao = new DBDao(context);
        addData();
        selfView.setListData(select);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = textView.getText().toString().trim();
                long insert = dbDao.insert(trim);
                if (insert > 0) {
                    selfView.setVisibility(View.VISIBLE);
                    select = dbDao.select();
                    selfView.setListData(select);
                }
                Intent intent = new Intent(context, ShowActivity.class);
                intent.putExtra("tran", trim);
                context.startActivity(intent);

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long delete = dbDao.delete();
                if (delete > 0) {
                    Toast.makeText(context, "清除成功", Toast.LENGTH_SHORT).show();
                    select = dbDao.select();
                    selfView.setListData(select);
                    selfView.setVisibility(View.GONE);
                }
            }
        });
        textView.setThreshold(1);
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, shopname);
        textView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(shopname.get(position));
            }
        });

    }

    private void addData() {
        shopname = new ArrayList<>();
        shopname.add("手机");
        shopname.add("电脑");
        shopname.add("男装");
        shopname.add("台式电脑");
        shopname.add("女装");
        shopname.add("零食");
        shopname.add("男鞋");
        shopname.add("女鞋");
        shopname.add("笔记本");

    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        selfView.setVisibility(View.VISIBLE);
        select = dbDao.select();
        selfView.setListData(select);
    }
}
