package com.example.lenovo.medicalProject;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ListView;

import com.example.lenovo.myapplication.R;
import com.medicalproject.bean.DoctorBean;
import com.medicalproject.util.DBUtils;

import java.util.List;

/**
 * 医生查询页Activity
 * @author 汪文博
 */
public class DoctorActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        tv = (TextView)findViewById(R.id.demo);
        final String officeName = getIntent().getStringExtra("officeName");

        new Thread(new Runnable() {
            String officeNameInner = officeName;
            @Override
            public void run() {
                List<DoctorBean> list = DBUtils.getDoctorInfoByOfficeName(officeNameInner);
                Message message = handler.obtainMessage();
                if(list != null && list.size() != 0){
                    message.obj = list.get(0).getDoctorName();
                }else{
                    message.obj = "无";
                }
                handler.sendMessage(message);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            tv.setText((String)msg.obj);
        }
    };
}
