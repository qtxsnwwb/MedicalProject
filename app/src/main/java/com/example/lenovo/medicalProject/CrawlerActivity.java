package com.example.lenovo.medicalProject;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.medicalproject.util.CrawlerUtils;

import java.util.List;

/**
 * 药物详情页Activity
 * @author 许鹤铭
 */
public class CrawlerActivity extends AppCompatActivity {

    private TextView tvMedicineTitle;    //标题
    private TextView tvMedicineName;     //药品名称
    private TextView tvMedicineComposition;     //成份
    private TextView tvMedicineIndication;      //适应症
    private TextView tvMedicineUsage;    //用法用量
    private TextView tvMedicineAdverseReaction;     //不良反应
    private TextView tvMedicineTaboo;    //禁忌
    private TextView tvMedicineNote;     //注意事项
    private TextView tvMedicineDrugInteraction;     //药物相互作用
    private TextView tvMedicineApprovalNumber;      //批准文号
    private TextView tvMedicineManufacturer;        //生产企业
    private TextView tvMedicineDrugClassification;  //药物分类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawler);

        tvMedicineTitle = (TextView) findViewById(R.id.tv_medicine_title);
        tvMedicineName = (TextView) findViewById(R.id.tv_medicine_name_content);
        tvMedicineComposition = (TextView) findViewById(R.id.tv_medicine_composition_content);
        tvMedicineIndication = (TextView) findViewById(R.id.tv_medicine_indication_content);
        tvMedicineUsage = (TextView) findViewById(R.id.tv_medicine_usage_content);
        tvMedicineAdverseReaction = (TextView) findViewById(R.id.tv_medicine_adverse_reaction_content);
        tvMedicineTaboo = (TextView) findViewById(R.id.tv_medicine_taboo_content);
        tvMedicineNote = (TextView) findViewById(R.id.tv_medicine_note_content);
        tvMedicineDrugInteraction = (TextView) findViewById(R.id.tv_medicine_drug_interaction_content);
        tvMedicineApprovalNumber = (TextView) findViewById(R.id.tv_medicine_approval_number_content);
        tvMedicineManufacturer = (TextView) findViewById(R.id.tv_medicine_manufacturer_content);
        tvMedicineDrugClassification = (TextView) findViewById(R.id.tv_medicine_drug_classification_content);

        //设置字体
        AssetManager mgr = getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/kaiti.ttf");
        tvMedicineName.setTypeface(tf);
        tvMedicineComposition.setTypeface(tf);
        tvMedicineIndication.setTypeface(tf);
        tvMedicineUsage.setTypeface(tf);
        tvMedicineAdverseReaction.setTypeface(tf);
        tvMedicineTaboo.setTypeface(tf);
        tvMedicineNote.setTypeface(tf);
        tvMedicineDrugInteraction.setTypeface(tf);
        tvMedicineApprovalNumber.setTypeface(tf);
        tvMedicineManufacturer.setTypeface(tf);
        tvMedicineDrugClassification.setTypeface(tf);

        //设置标题
        String value = getIntent().getStringExtra("medicineName");
        tvMedicineTitle.setText(value);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                List<String> medicineList = CrawlerUtils.getMedicineInfo("penicillin");
                message.obj = medicineList;
                handler.sendMessage(message);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            List<String> list = (List<String>) msg.obj;
            tvMedicineName.setText(list.get(0));
            tvMedicineComposition.setText(list.get(1));
            tvMedicineIndication.setText(list.get(2));
            tvMedicineUsage.setText(list.get(3));
            tvMedicineAdverseReaction.setText(list.get(4));
            tvMedicineTaboo.setText(list.get(5));
            tvMedicineNote.setText(list.get(6));
            tvMedicineDrugInteraction.setText(list.get(7));
            tvMedicineApprovalNumber.setText(list.get(8));
            tvMedicineManufacturer.setText(list.get(9));
            tvMedicineDrugClassification.setText(list.get(10));
        }
    };
}
