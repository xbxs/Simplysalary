package com.example.atry.simplysalary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.utils.Uiutils;

public class LoginActivity extends BaseActivity {
    private EditText et_number,et_password;
    private Button btn_login,btn_login_unregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        et_number = findViewById(R.id.et_number);
        et_password = findViewById(R.id.et_password);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_login_unregister = findViewById(R.id.btn_login_unregister);
        btn_login_unregister.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }



    @Override
    public void onClick(View v) {
        Intent intent= null;
        switch (v.getId()){
            case R.id.btn_login_unregister:
                intent = new Intent(LoginActivity.this,RegisterActivity.class);
                break;
            case R.id.btn_login:

                break;
            case R.id.btn_login_forgetpassword:
                break;
            default:
                break;
        }

        startActivity(intent);
    }

    public boolean login(){
        String number = et_number.getText().toString().trim();
        String password =et_password.getText().toString().trim();
        if(Uiutils.judgePhoneNums(number)){
            
        }
        return  false;
    }

}
