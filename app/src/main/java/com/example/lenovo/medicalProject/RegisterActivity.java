package com.example.lenovo.medicalProject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.myapplication.R;
import com.medicalproject.util.DBUtils;

/**
 * 注册页面
 * @author 许鹤铭
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userPassword;
    private EditText userConfirmPassword;
    private Context sContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sContext = this;
        userName = findViewById(R.id.edit_register_username);
        userPassword = findViewById(R.id.edit_register_password);
        userConfirmPassword = findViewById(R.id.edit_register_re_password);
    }

    /**
     * @Description 检查注册信息是否规范
     * @param v
     */
    public void registerCheck(View v){

        if (userPassword.getText().toString().equals(userConfirmPassword.getText().toString())) {
            if(userPassword.getText().toString().length()<=6){
                Toast.makeText(sContext, "密码应大于6位", Toast.LENGTH_SHORT).show();
                userPassword.setText("");
            }
            else if(userPassword.getText().toString().equals("") || userPassword.getText().toString() == null){
                Toast.makeText(sContext, "请输入密码", Toast.LENGTH_SHORT).show();
            }
            else if(userName.getText().toString() == null || "".equals(userName.getText().toString())){
                Toast.makeText(sContext, "请输入用户名", Toast.LENGTH_SHORT).show();
            }
            else {
                addRegisterData();
                Intent intent = new Intent(sContext, LoginActivity.class);
                startActivity(intent);
            }
        }
        else {
                Toast.makeText(sContext, "两次密码不一致", Toast.LENGTH_SHORT).show();
                userPassword.setText("");
                userConfirmPassword.setText("");
        }
    }

    /**
     * 注册数据库操作
     */
    private void addRegisterData(){
        //开启线程处理数据库操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBUtils.addRegisterData(userName.getText().toString(), userPassword.getText().toString());
            }
        }).start();
    }
}
