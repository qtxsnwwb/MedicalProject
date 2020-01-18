package com.example.lenovo.medicalProject;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.lenovo.myapplication.R;
import com.medicalproject.fragment.OfficeRecommendFragment;
import com.medicalproject.fragment.OfficeSearchFragment;

/**
 * 访庵庐模块主Activity
 * @author 汪文博
 */
public class HospitalActivity extends FragmentActivity {

    //要切换显示的两个Fragment
    private OfficeRecommendFragment officeRecommendFragment;
    private OfficeSearchFragment officeSearchFragment;

    //当前选中id，默认是智能分诊
    private int currentId = R.id.tv_hosp_left;

    //底部两个TextView
    private TextView tvLeft;
    private TextView tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        tvLeft = (TextView) findViewById(R.id.tv_hosp_left);
        tvRight = (TextView) findViewById(R.id.tv_hosp_right);
        tvLeft.setSelected(true);      //智能分诊默认被选中

        //默认加载智能分诊页
        officeRecommendFragment = new OfficeRecommendFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container_hospital, officeRecommendFragment).commit();

        //设置点击事件监听器
        tvLeft.setOnClickListener(tabClickListener);
        tvRight.setOnClickListener(tabClickListener);
    }

    private OnClickListener tabClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            //若当前选中与上次相同，则不需要处理
            if(view.getId() != currentId){
                changeSelect(view.getId());     //修改选中状态
                changeFragment(view.getId());   //切换Fragment
                currentId = view.getId();       //修改当前选中ID
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
            case R.id.tv_hosp_left:
                tvLeft.setSelected(true);
                break;
            case R.id.tv_hosp_right:
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
        if(officeRecommendFragment != null) trans.hide(officeRecommendFragment);
        if(officeSearchFragment != null) trans.hide(officeSearchFragment);
        //切换
        if(resId == R.id.tv_hosp_left){
            if(officeRecommendFragment == null){
                officeRecommendFragment = new OfficeRecommendFragment();
                trans.add(R.id.main_container_hospital, officeRecommendFragment);
            }else{
                trans.show(officeRecommendFragment);
            }
        }else if(resId == R.id.tv_hosp_right){
            if(officeSearchFragment == null){
                officeSearchFragment = new OfficeSearchFragment();
                trans.add(R.id.main_container_hospital, officeSearchFragment);
            }else{
                trans.show(officeSearchFragment);
            }
        }
        trans.commit();   //提交事务
    }
}
