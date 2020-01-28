package com.example.lenovo.medicalProject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.myapplication.R;
import com.medicalproject.util.DBUtils;

/**
 * 登录页面
 * @author 许鹤铭
 */
public class LoginActivity extends AppCompatActivity {

    public static Context sContext;
    private EditText loginUsername;
    private EditText loginPassword;
    private boolean flag;
    private boolean wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sContext = this;

        //判断是否已登录，若已登录则跳转到首页
        if(isLogin()){
            System.out.println("已登录");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        loginPassword = findViewById(R.id.edit_login_password);
        loginUsername = findViewById(R.id.edit_login_username);
    }

    /**
     * 判断是否已登录
     * @return true为已登录,false为未登录
     */
    private boolean isLogin(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userID = sp.getString("userID", "");
        if(userID == null || "".equals(userID))
            return false;
        else
            return true;
    }

    /**
     * @Description “注册”按钮点击事件监听
     * @param v
     */
    public void onRegisterNewAccountClick(View v){
        Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * @Description “登录”按钮点击事件监听
     * @param v
     */
    public void onLoginClick(View v){
        if(loginFormatCheck()) {     //登录输入检查
            if(loginDataCheck()) {     //登录数据库操作
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(sContext, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                loginUsername.setText("");
                loginPassword.setText("");
            }
        }
    }


    /**
     * @Description 检查登录信息格式
     * @return
     */
    private boolean loginFormatCheck() {
        if (loginUsername.getText().toString() == null || "".equals(loginUsername.getText().toString())){
            Toast.makeText(sContext, "用户名为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(loginPassword.getText().toString() == null || "".equals(loginPassword.getText().toString())){
            Toast.makeText(sContext, "密码为空", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    /**
     * 匹配用户名和密码
     * @return 匹配成功与否
     */
    private boolean loginDataCheck() {
        flag = true;
        wait = false;
        //数据库匹配用户名和密码
        //开启线程获取数据库数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userID = DBUtils.loginDataCheck(loginUsername.getText().toString(), loginPassword.getText().toString());
                if(userID == null || "".equals(userID)) {     //匹配失败
                    flag = false;
                }else{
                    saveLogin(userID);
                }
                wait = true;
            }
        }).start();
        for(int i=0; i<100000000; i++){
            if(wait == true) break;
        }
        return flag;
    }

    /**
     * 系统存储用户信息
     * @param userID 用户ID
     */
    private void saveLogin(String userID){
        //将用户信息加入系统存储
        SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userID", userID);
        edit.commit();
    }
}
