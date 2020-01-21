package com.example.lenovo.medicalProject;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.medicalproject.adapter.BannerAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int CAROUSEL_TIME = 5000;   //banner滚动时间

    private Context sContext;

    private ViewPager vpBanner;
    private ViewGroup viewGroup;   //显示圆点图片
    private TextView tvTitle1;
    private TextView tvTitle2;
    private TextView tvTitle3;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;

    private BannerAdapter bannerAdapter;    //ViewPager适配器

    private Handler handler = new Handler();
    private int currentItem = 0;    //ViewPager当前位置

    private final Runnable mTicker = new Runnable() {
        @Override
        public void run() {
            long now = SystemClock.uptimeMillis();
            long next = now + (CAROUSEL_TIME - now % CAROUSEL_TIME);

            handler.postAtTime(mTicker, next);    //延迟5秒再次执行runnable

            currentItem++;
            vpBanner.setCurrentItem(currentItem);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sContext = this;

        tvTitle1 = (TextView) findViewById(R.id.tv_main_title_1);
        tvTitle2 = (TextView) findViewById(R.id.tv_main_title_2);
        tvTitle3 = (TextView) findViewById(R.id.tv_main_title_3);

        //设置字体
        AssetManager mgr = getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/shufa.ttf");
        tvTitle1.setTypeface(tf);
        tvTitle2.setTypeface(tf);
        tvTitle3.setTypeface(tf);

        //设置点击跳转事件
        rl1 = (RelativeLayout) findViewById(R.id.rl_main_1);
        rl2 = (RelativeLayout) findViewById(R.id.rl_main_2);
        rl3 = (RelativeLayout) findViewById(R.id.rl_main_3);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sContext, HospitalActivity.class);
                startActivity(intent);
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sContext, NavigationActivity.class);
                startActivity(intent);
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sContext, DiagnoseActivity.class);
                startActivity(intent);
            }
        });

        //设置轮播图
        vpBanner = (ViewPager) findViewById(R.id.vp_banner);
        bannerAdapter = new BannerAdapter(this);
        vpBanner.setOffscreenPageLimit(2);     //缓存页数
        vpBanner.setAdapter(bannerAdapter);    //设置适配器
        vpBanner.addOnPageChangeListener(onPageChangeListener);     //页面改变监听

        viewGroup = (ViewGroup) findViewById(R.id.ll_viewGroup);    //显示圆点控件

        //将圆点加入到ViewGroup中
        for(int i=0; i<bannerAdapter.getBanners().length; i++){
            ImageView imageView = new ImageView(this);
            //设置图片宽和高
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 101));
            if(i == 0){
                imageView.setBackgroundResource(R.mipmap.icon_page_select);
            }else{
                imageView.setBackgroundResource(R.mipmap.icon_page_normal);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;   //左边距
            layoutParams.rightMargin = 10;  //右边距
            viewGroup.addView(imageView, layoutParams);
        }

        //给ViewPager设置当前页
        currentItem = bannerAdapter.getBanners().length * 50;
        vpBanner.setCurrentItem(currentItem);

        handler.postDelayed(mTicker, CAROUSEL_TIME);    //开启计时器
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            currentItem = position;

            //改变圆点图片的选中状态
            setImageBackground(position %= bannerAdapter.getBanners().length);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    /**
     * 改变圆点的切换效果
     * @param selectItems 当前选中位置
     */
    private void setImageBackground(int selectItems){
        for(int i=0; i<bannerAdapter.getBanners().length; i++){
            ImageView imageView = (ImageView) viewGroup.getChildAt(i);
            imageView.setBackgroundDrawable(null);   //设置背景为无
            if(i == selectItems){
                imageView.setBackgroundResource(R.mipmap.icon_page_select);
            }else{
                imageView.setBackgroundResource(R.mipmap.icon_page_normal);
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(mTicker);     //删除计时器
    }
}
