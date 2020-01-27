package com.example.lenovo.medicalProject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.medicalproject.util.CrawlerThread;

public class CrawlerActivity extends AppCompatActivity {

    public static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawler);

        textView = findViewById(R.id.crawler_info);
        CrawlerThread myCrawler = new CrawlerThread();
        myCrawler.execute();

    }



}
