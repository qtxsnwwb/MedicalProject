package com.example.lenovo.medicalProject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;

/**
 * 医生查询页Activity
 * @author 汪文博
 */
public class DoctorActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        tv = (TextView)findViewById(R.id.demo);
        String officeName = getIntent().getStringExtra("officeName");
        tv.setText(officeName);
    }
}
