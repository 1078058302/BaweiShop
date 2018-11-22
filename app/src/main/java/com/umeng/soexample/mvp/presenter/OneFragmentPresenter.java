package com.umeng.soexample.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.umeng.soexample.R;
import com.umeng.soexample.adapter.LunAdapter;
import com.umeng.soexample.adapter.MyViewPagerAdapter;
import com.umeng.soexample.adapter.ShopAdapter;
import com.umeng.soexample.model.BannerBean;
import com.umeng.soexample.model.DataBean;
import com.umeng.soexample.model.HomeIconInfo;
import com.umeng.soexample.model.NineBean;
import com.umeng.soexample.model.ShopBean;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.mvp.view.LayoutBar;
import com.umeng.soexample.mvp.view.NestedScrollView;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.utils.DataBeanJsonUtils;
import com.umeng.soexample.utils.NetworkUtils;
import com.umeng.soexample.utils.UltimateBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.bgabanner.BGABanner;

public class OneFragmentPresenter extends AppDelegate {

    private List<String> imageList = new ArrayList<>();
    private List<String> textList = new ArrayList<>();
    private BGABanner banner;
    private RecyclerView recyclerView2;
    final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
    private SimpleMarqueeView<String> marqueeView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            countDown();
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    private TextView mMiaoshaTimeTv;
    private TextView mMiaoshaMinterTv;
    private TextView mMiaoshaShiTv;
    private TextView mMiaoshaSecondTv;
    private ViewPager viewPager;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;
    /**
     * GridView 一共二页的数据
     */

    private LinearLayout mLlDot;
    private LayoutInflater inflater;
    private List<HomeIconInfo> infos = new ArrayList<>();
    private List<View> mPagerList = new ArrayList<>();
    private MaterialRefreshLayout refreshLayout;
    private LinearLayout layout;
    private ShopAdapter shopAdapter;

    private void countDown() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String format = df.format(curDate);
        StringBuffer buffer = new StringBuffer();
        String substring = format.substring(0, 11);
        buffer.append(substring);
        Log.d("ccc", substring);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour % 2 == 0) {
            mMiaoshaTimeTv.setText(hour + "点场");
            buffer.append(hour + 2);
            buffer.append(":00:00");
        } else {
            mMiaoshaTimeTv.setText((hour - 1) + "点场");
            buffer.append(hour + 1);
            buffer.append(":00:00");
        }
        String totime = buffer.toString();
        try {
            java.util.Date date = df.parse(totime);
            java.util.Date date1 = df.parse(format);
            long defferenttime = date.getTime() - date1.getTime();
            long days = defferenttime / (1000 * 60 * 60 * 24);
            long hours = (defferenttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minute = (defferenttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = defferenttime % 60000;
            long second = Math.round((float) seconds / 1000);
            mMiaoshaShiTv.setText("0" + hours + "");
            if (minute >= 10) {
                mMiaoshaMinterTv.setText(minute + "");
            } else {
                mMiaoshaMinterTv.setText("0" + minute + "");
            }
            if (second >= 10) {
                mMiaoshaSecondTv.setText(second + "");
            } else {
                mMiaoshaSecondTv.setText("0" + second + "");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_one;
    }


    @Override
    public void initData() {
        super.initData();
        banner = get(R.id.banner);
        UltimateBar.newImmersionBuilder().applyNav(false).build((Activity) context).apply();
//        recyclerView1 = get(R.id.recycler1);
        recyclerView2 = get(R.id.recycler2);
        marqueeView = (SimpleMarqueeView) get(R.id.simpleMarqueeView);
        mMiaoshaTimeTv = get(R.id.tv_miaosha_time);
        mMiaoshaMinterTv = get(R.id.tv_miaosha_minter);
        mMiaoshaShiTv = get(R.id.tv_miaosha_shi);
        mMiaoshaSecondTv = get(R.id.tv_miaosha_second);
        viewPager = get(R.id.viewPager);
        mLlDot = get(R.id.points);
        refreshLayout = get(R.id.refresh);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                doHttp();
                doHttp1();
                doHttp2();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
            }
        });
        SimpleMF<String> marqueeFactory = new SimpleMF(context);
        marqueeFactory.setData(datas);
        NestedScrollView scrollView = get(R.id.scroll);
        layout = get(R.id.linea);
        scrollView.setOnScrollListener(new NestedScrollView.OnScrollListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onScroll(int scrollY) {
                int i = dip2px(context, scrollY);
                int dp = px2dp(context, i);
                if (dp > 200) {
                    layout.setBackgroundColor(R.color.colorPrimary);
                } else {
                    layout.setBackgroundColor(Color.argb(99, 255, 255, 255));
                }
            }
        });
        DataBeanJsonUtils.getNewsBeanJsonUtils().init(context);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
        shopAdapter = new ShopAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(manager);
        recyclerView2.setAdapter(shopAdapter);
        doHttp();
        doHttp1();
        doHttp2();
        startCountDown();
    }

    private void doHttp2() {
        if (!NetworkUtils.isConnected(context)) {
            DataBean buyShop = DataBeanJsonUtils.getNewsBeanJsonUtils().query("1");
            String data = buyShop.getData();
            ShopBean shopBean = new Gson().fromJson(data, ShopBean.class);
            List<ShopBean.DataBean> data1 = shopBean.getData();
            data1.remove(0);
            shopAdapter.setContext(context);
            shopAdapter.setList(data1);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("uid", "71");
            new HttpHelper().get("/product/getCarts", map).result(new HttpListener() {
                @Override
                public void success(String data) {
                    if (data.contains("<")) {
                        return;
                    }
                    if (!TextUtils.isEmpty(data)) {
                        DataBean dataBean = new DataBean();
                        dataBean.setData(data);
                        dataBean.setType("BuyShop");
                        DataBeanJsonUtils.getNewsBeanJsonUtils().insert(dataBean);
                    }
                    ShopBean shopBean = new Gson().fromJson(data, ShopBean.class);
                    List<ShopBean.DataBean> data1 = shopBean.getData();
                    data1.remove(0);
                    shopAdapter.setContext(context);
                    shopAdapter.setList(data1);
                    refreshLayout.finishRefresh();
                }

                @Override
                public void fail(String error) {

                }
            });
        }
    }

    private void doHttp1() {
        new HttpHelper().get("/product/getCatagory", null).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("<")) {
                    return;
                }
                NineBean nineBean = new Gson().fromJson(data, NineBean.class);
                List<NineBean.DataBean> data1 = nineBean.getData();
                if (infos.size() >= data1.size()) {
                    return;
                }
                for (int i = 0; i < data1.size(); i++) {
                    infos.add(new HomeIconInfo(data1.get(i).getName(), data1.get(i).getIcon()));
                }
                inflater = LayoutInflater.from(context);
                //总的页数=总数/每页数量，并取整
                pageCount = (int) Math.ceil(infos.size() * 1.0 / pageSize);
                for (int i = 0; i < pageCount; i++) {
                    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.viewpager_item, viewPager, false);
                    GridView gridView = layout.findViewById(R.id.gridview);
                    gridView.setAdapter(new LunAdapter(context, infos, i, pageSize));
                    mPagerList.add(layout);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(context, infos.get((int) id).getIconName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                viewPager.setAdapter(new MyViewPagerAdapter(mPagerList));
                setOvalLayout();
                refreshLayout.finishRefresh();
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.page__selected_indicator);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.page__normal_indicator);
                // 圆点选中
                mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.page__selected_indicator);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    private void doHttp() {
        new HttpHelper().get("/ad/getAd", null).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("<")) {
                    return;
                }
                BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
                List<BannerBean.DataBean> data1 = bannerBean.getData();
                if (imageList.size() >= data1.size()) {
                    return;
                } else {
                    for (int i = 0; i < data1.size(); i++) {
                        String image = data1.get(i).getIcon();
                        String replace = image.replace("https", "http");
                        String title = data1.get(i).getTitle();
                        imageList.add(replace);
                        textList.add(title);
                    }
                }
                banner.setAdapter(new BGABanner.Adapter() {
                    @Override
                    public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                        Picasso.with(context).load(imageList.get(position)).fit().into((ImageView) itemView);
                    }
                });
                banner.setData(imageList, textList);
                refreshLayout.finishRefresh();
            }

            @Override
            public void fail(String error) {

            }
        });
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

    //将像素转换为px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //将px转换为dp
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private void startCountDown() {
        handler.sendEmptyMessage(0);
    }

}
