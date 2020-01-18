package com.example.lenovo.medicalProject;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.lenovo.myapplication.R;
import com.medicalproject.fragment.NavigationFragment;
import com.medicalproject.fragment.StoreFragment;

/**
 * 望杏林模块主Activity
 * @author 汪文博
 */
public class NavigationActivity extends FragmentActivity {

    //要切换显示的两个Fragment
    private NavigationFragment navigationFragment;
    private StoreFragment storeFragment;

    //当前选中id，默认是医院导航
    private int currentId = R.id.tv_navi_left;

    //底部两个TextView
    private TextView tvLeft;
    private TextView tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        tvLeft = (TextView) findViewById(R.id.tv_navi_left);
        tvRight = (TextView) findViewById(R.id.tv_navi_right);
        tvLeft.setSelected(true);    //医院导航默认被选中

        //默认加载医院导航页
        navigationFragment = new NavigationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, navigationFragment).commit();

        //设置点击事件监听器
        tvLeft.setOnClickListener(tabClickListener);
        tvRight.setOnClickListener(tabClickListener);
    }

    private OnClickListener tabClickListener = new OnClickListener(){
        @Override
        public void onClick(View v){
            //若当前选中与上次相同，则不需要处理
            if(v.getId() != currentId){
                changeSelect(v.getId());    //修改选中状态
                changeFragment(v.getId());    //切换Fragment
                currentId = v.getId();      //修改当前选中ID
            }
        }
    };

    /**
     * 修改TextView选中状态
     * @param resId TextView的ID
     */
    private void changeSelect(int resId){
        tvLeft.setSelected(false);
        tvRight.setSelected(false);

        switch (resId){
            case R.id.tv_navi_left:
                tvLeft.setSelected(true);
                break;
            case R.id.tv_navi_right:
                tvRight.setSelected(true);
                break;
        }
    }

    /**
     * 切换Fragment
     * @param resId TextView的ID
     */
    private void changeFragment(int resId){
        //开启Fragment事务
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        //隐藏所有Fragment
        if(navigationFragment != null) trans.hide(navigationFragment);
        if(storeFragment != null) trans.hide(storeFragment);
        //切换
        if(resId == R.id.tv_navi_left){
            if(navigationFragment == null){
                navigationFragment = new NavigationFragment();
                trans.add(R.id.main_container, navigationFragment);
            }else{
                trans.show(navigationFragment);
            }
        }else if(resId == R.id.tv_navi_right){
            if(storeFragment == null){
                storeFragment = new StoreFragment();
                trans.add(R.id.main_container, storeFragment);
            }else{
                trans.show(storeFragment);
            }
        }
        trans.commit();   //提交事务
    }
}
