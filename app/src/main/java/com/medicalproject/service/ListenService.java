package com.medicalproject.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.example.lenovo.medicalProject.DoctorActivity;
import com.example.lenovo.myapplication.R;
import com.medicalproject.util.DBUtils;

import java.util.Timer;
import java.util.TimerTask;

public class ListenService extends Service {

    private Timer timer;
    private String userID;
    private String doctorName;
    private Context mContext;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = this;
        timer = new Timer();
        //获取数据
        SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sp.getString("userID", "");
        doctorName = sp.getString("doctorName", "");

        //启动定时器
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                mHandler.sendMessage(message);
            }
        }, 0, 3000);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0){
                //开启线程进行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(!DBUtils.isCall(userID, doctorName)){
                            System.out.println("被叫号!");
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
                                    .setSmallIcon(R.mipmap.ic_launcher_round)
                                    .setContentTitle("叫号通知")
                                    .setContentText("您已被"+doctorName+"叫号！请尽快前往治疗");
                            Intent intent = new Intent(mContext, ListenService.class);
                            PendingIntent ClickPending = PendingIntent.getActivity(mContext, 0, intent, 0);
                            mBuilder.setContentIntent(ClickPending);
                            mBuilder.setAutoCancel(true);
                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(101, mBuilder.build());
                            timer.cancel();
                        }
                    }
                }).start();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
