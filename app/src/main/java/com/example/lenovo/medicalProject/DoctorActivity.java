package com.example.lenovo.medicalProject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.R;
import com.medicalproject.adapter.DoctorAdapter;
import com.medicalproject.bean.DoctorBean;
import com.medicalproject.util.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 医生查询页Activity
 * @author 汪文博
 */
public class DoctorActivity extends AppCompatActivity {

    private ListView listView;
    private static Context sContext;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        sContext = this;    //获取Activity上下文，供Handler使用
        listView = (ListView) findViewById(R.id.lv_doctor);
        tv = (TextView) findViewById(R.id.tv_doctor_return);
        //获取科室编号数据
        final String officeName = getIntent().getStringExtra("officeName");
        //开启线程获取数据库数据
        new Thread(new Runnable() {
            String officeNameInner = officeName;
            @Override
            public void run() {
                List<DoctorBean> list = DBUtils.getDoctorInfoByOfficeName(officeNameInner);
                Message message = handler.obtainMessage();
                message.obj = list;
                handler.sendMessage(message);
            }
        }).start();

        //返回首页按钮点击事件
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回首页（返回时清除所有activity）
                Intent intent = new Intent(sContext, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //定义动态数组
            ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
            //接收数据库数据
            List<DoctorBean> list = (List<DoctorBean>) msg.obj;
            if(list != null && list.size() != 0){
                for(int i=0; i<list.size(); i++){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ItemName", list.get(i).getDoctorName());
                    map.put("ItemIntro", list.get(i).getDoctorIntro());
                    map.put("ItemBtn", R.id.btn_doctor_item);
                    listItem.add(map);
                }
                DoctorAdapter doctorAdapter = new DoctorAdapter(DoctorActivity.sContext, listItem);
                //为ListView绑定Adapter
                listView.setAdapter(doctorAdapter);

            }else{      //科室无医生或系统出现问题
                Toast.makeText(DoctorActivity.sContext, "当前科室无医生或系统出现问题，请重新选择！", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };
}
