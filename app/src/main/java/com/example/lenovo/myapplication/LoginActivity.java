package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @Description 登录页面
 * @author 许鹤铭
 */
public class LoginActivity extends AppCompatActivity {
    public static Activity loginActivity;
    private EditText loginUsername;
    private EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity=this;

        if(getSharedPreferences("usersInfo",MODE_PRIVATE).getBoolean("flag",false)){
            Intent intent =new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        loginPassword =findViewById(R.id.edit_login_password);
        loginUsername=findViewById(R.id.edit_login_username);


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
        if(loginFormatCheck()) {
            if(loginDataCheck()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        }
    }


    /**
     * @Description 检查登录信息格式
     * @return
     */
    private boolean loginFormatCheck() {
        if(loginUsername.getText().toString().length()==11 && !loginPassword.getText().toString().equals("")){
            return true;
        }
        else if (loginUsername.getText().toString().length()!=11){
            Toast.makeText(getApplicationContext(), "用户名应为11位手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Toast.makeText(getApplicationContext(), "密码为空", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 匹配用户名和密码
     * @return
     */
    private boolean loginDataCheck() {
        SharedPreferences sharedPreferences = getSharedPreferences("usersInfo",MODE_PRIVATE);
        String storageUsername = sharedPreferences.getString("username","");
        String storagePassword = sharedPreferences.getString("password","");

        if(storageUsername.equals("") || storagePassword.equals("")){
            Toast.makeText(getApplicationContext(),"用户不存在，请先注册",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(storageUsername.equals(loginUsername.getText().toString()) && storagePassword.equals(loginPassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putBoolean("flag",true);
            editor.commit();
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
