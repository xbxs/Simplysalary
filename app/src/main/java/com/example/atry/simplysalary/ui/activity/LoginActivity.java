package com.example.atry.simplysalary.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends BaseActivity {
    private EditText et_number,et_password;
    private Button btn_login,btn_login_unregister;

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
                startActivity(intent);
                break;
            case R.id.btn_login:
                    login();
                break;
            case R.id.btn_login_forgetpassword:
                break;
            default:
                break;
        }


    }

    public boolean login(){
        String number = et_number.getText().toString().trim();
        String password =et_password.getText().toString().trim();
        if(Uiutils.judgePhoneNums(number) && Uiutils.passwordIsMatch(password)){
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    //去服务器登录
                    EMClient.getInstance().login(number, password, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.i("TAG","number:"+number+"  password:"+password);
                            //对模型层数据进行处理
                            User user = new User();
                            user.setPhonenumber(number);
                            Model.getInstance().loginSuccess(user);
                            //保存用户账号信息到本地数据库

                            //提示登录成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Uiutils.toast("登录成功");
                                    // 跳转到主页
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onError(int i, String s) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Uiutils.toast("登录失败:"+s);
                                    }
                                });
                        }

                        @Override
                        public void onProgress(int i, String s) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Uiutils.toast("正在验证:"+s);
                                }
                            });
                        }
                    });
                }
            });
        }else{
            Uiutils.toast("系统繁忙请稍后！");
        }
        return  false;
    }

}
