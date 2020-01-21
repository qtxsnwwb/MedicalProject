package com.medicalproject.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.myapplication.R;

/**
 * 轮播图适配器
 * @author 汪文博
 */
public class BannerAdapter extends PagerAdapter {

    private Context context;
    private View.OnClickListener onBannerClickListener;

    //图片列表
    private int[] banners = new int[]{R.mipmap.banner_one, R.mipmap.banner_two, R.mipmap.banner_three};

    public BannerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount(){
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1){
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        position %= banners.length;

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);     //设置图片缩放类型
        imageView.setTag(position);   //将当前下标通过setTag设置进去
        imageView.setImageResource(banners[position]);
        container.addView(imageView);
        return imageView;
    }

    public int[] getBanners(){
        return banners;
    }
}
