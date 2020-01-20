package com.example.lenovo.medicalProject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.R;
import com.medicalproject.bean.ReportBean;
import com.medicalproject.util.DBUtils;

import java.util.List;

/**
 * 开药方模块主页面
 * @author 汪文博
 */
public class DiagnoseActivity extends AppCompatActivity {

    private static Context sContext;
    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_age;
    private TextView tv_office;
    private TextView tv_doctor;
    private TextView tv_diagnose;
    private TextView tv_medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);

        sContext = this;    //获取Activity上下文，供Handler使用
        tv_name = (TextView) findViewById(R.id.tv_patient_name);
        tv_sex = (TextView) findViewById(R.id.tv_patient_sex);
        tv_age = (TextView) findViewById(R.id.tv_patient_age);
        tv_office = (TextView) findViewById(R.id.tv_diagnose_office);
        tv_doctor = (TextView) findViewById(R.id.tv_diagnose_doctor);
        tv_diagnose = (TextView) findViewById(R.id.tv_diagnose_result);
        tv_medicine = (TextView) findViewById(R.id.tv_medicine_advice);

        //获取用户信息
        SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String userID = sp.getString("userID", "");
        //开启线程获取数据库数据
        new Thread(new Runnable() {
            String userIDInner = userID;
            @Override
            public void run() {
                List<ReportBean> list = DBUtils.getReportInfoByUserID(userIDInner);
                Message message = handler.obtainMessage();
                message.obj = list;
                handler.sendMessage(message);
            }
        }).start();

        //为用药设置点击事件监听器
        tv_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此处设置爬虫


            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //接收数据库数据
            List<ReportBean> list = (List<ReportBean>) msg.obj;
            if(list != null && list.size() != 0){
                ReportBean reportBean = list.get(0);
                tv_name.setText(reportBean.getUserName());
                tv_sex.setText(reportBean.getUserSex());
                tv_age.setText(reportBean.getUserAge());
                tv_office.setText(reportBean.getOffice());
                tv_doctor.setText(reportBean.getDoctor());
                tv_diagnose.setText(reportBean.getDiagnoseRsult());
                tv_medicine.setText(reportBean.getMedicine());

            }else{      //无报告单记录
                Toast.makeText(DiagnoseActivity.sContext, "暂时没有报告单记录！", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };

}
