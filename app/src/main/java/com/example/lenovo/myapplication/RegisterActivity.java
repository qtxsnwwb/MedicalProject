package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @Description 注册页面
 * @author 许鹤铭
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userPassword;
    private EditText userConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName=findViewById(R.id.edit_register_username);
        userPassword=findViewById(R.id.edit_register_password);
        userConfirmPassword=findViewById(R.id.edit_register_re_password);
    }

    /**
     * @Description 检查注册信息是否规范
     * @param v
     */
    public void registerCheck(View v){
        SharedPreferences sharedPreferences = getSharedPreferences("usersInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(userName.getText().toString().length() !=11){
            Toast.makeText(getApplicationContext(), "请输入有效手机号", Toast.LENGTH_SHORT).show();
        }

        else{
            editor.putString("username", userName.getText().toString());

            if (userPassword.getText().toString().equals(userConfirmPassword.getText().toString())) {
                if(userPassword.getText().toString().length()<=6){
                    Toast.makeText(getApplicationContext(), "密码应大于6位", Toast.LENGTH_SHORT).show();
                }
                else if(userPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                }

                else {
                    editor.putString("password", userPassword.getText().toString());
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    editor.apply();

                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }

            else {
                Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
