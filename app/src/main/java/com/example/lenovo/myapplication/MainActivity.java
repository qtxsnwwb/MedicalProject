package com.example.lenovo.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.youth.banner.Banner;

import java.util.ArrayList;


/**
 * @Description 主页面
 * @author 许鹤铭
 */
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginActivity.loginActivity.finish();
        setBanner();

    }


    /**
     * 设置轮播图
     */
    public void setBanner(){
        Banner banner = (Banner) findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        ArrayList<String> images=new ArrayList<>();

        //轮播图片设置
        images.add("https://s1.hdslb.com/bfs/static/jinkela/space/asserts/icon-auth.png");
        images.add("https://s1.hdslb.com/bfs/static/jinkela/space/asserts/icon-auth.png");
        images.add("https://s1.hdslb.com/bfs/static/jinkela/space/asserts/icon-auth.png");
        images.add("https://s1.hdslb.com/bfs/static/jinkela/space/asserts/icon-auth.png");
        banner.setImages(images);
        //设置轮播时间
        banner.setDelayTime(4000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        ImageView imageView=findViewById(R.id.image);
    }

}
