package com.example.atry.simplysalary.ui.activity;

import android.content.ContentValues;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.model.dao.DButils;
import com.example.atry.simplysalary.utils.Uiutils;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity {
    private Button btn_send_verify,btn_register;
    private EditText et_register_number,et_register_verify,et_register_password;
    private RadioGroup rg_status;
    private RadioButton rb_staff,rb_boss;

    MyHandler handler = new MyHandler();
    int i = 60;
    private String mNumber;
    private String mPassword;


    @Override
    protected int setContentView() {
        return R.layout.activity_register;
    }

    /**
     * 为变量初始化
     */
    protected void initView() {
        et_register_number = findViewById(R.id.et_register_number);
        et_register_verify = findViewById(R.id.et_register_verify);
        btn_send_verify = findViewById(R.id.btn_send_verify);
        btn_send_verify.setOnClickListener(this);
        et_register_password = findViewById(R.id.et_register_password);
        rg_status = findViewById(R.id.rg_status);
        rb_staff = findViewById(R.id.rb_staff);
        rb_boss = findViewById(R.id.rb_boss);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        EventHandler eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.handleMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        String phoneNums = et_register_number.getText().toString().trim();

        switch (view.getId()){

            case R.id.btn_send_verify:
                if(!Uiutils.judgePhoneNums(phoneNums)){
                    return;
                }
                SMSSDK.getVerificationCode("86",phoneNums);
                btn_send_verify.setClickable(false);
                btn_send_verify.setText("重新发送("+i+")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(;i > 0;i--){
                            handler.sendEmptyMessage(-9);
                            if(i <= 0){
                                break;
                            }

                            SystemClock.sleep(1000);
                        }
                        handler.sendEmptyMessage(-8);
                    }

                }).start();
                break;
            case R.id.btn_register:

                if(!inputIntegrity()){
                    return;
                }
                SMSSDK.submitVerificationCode("86",phoneNums,et_register_verify.getText().toString());
                break;
            default:
                break;
        }
    }

    private boolean registerUser() {
        DButils dButils = new DButils(getApplicationContext());
        User user = dButils.queryUser(mNumber);
        if(user != null){
            Log.i("TAG",user.toString());
        }

        if(dButils.queryUser(mNumber) == null){
            int flag = rb_staff.isChecked() ? 1 :0;
            ContentValues contentValues = new ContentValues();
            contentValues.put("phonenumber",mNumber);
            contentValues.put("name","简记");
            contentValues.put("picture","");
            contentValues.put("flag",flag);
            contentValues.put("bossnumber",1);
            contentValues.put("department","");

            long count = dButils.insertUser(contentValues);
            if(count > 1)
            return true;
        }
        return false;
    }


    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == -9){
                btn_send_verify.setText("重新发送("+i+")");
            }else if(msg.what == -8){
                btn_send_verify.setText("获取验证码");
                btn_send_verify.setClickable(true);
                i = 30;
            }else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                // 提交验证码成功
                if(result == SMSSDK.RESULT_COMPLETE){
                    if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        if(registerUser()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Uiutils.getContext(),"注册成功",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Uiutils.getContext(),"该账号已经注册了",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }
                }else if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Uiutils.getContext(),"正在获取验证码!",Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Uiutils.getContext(),"验证码错误",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

        }
    }



    private boolean inputIntegrity(){
        String verifycode = et_register_verify.getText().toString().trim();
        mNumber = et_register_number.getText().toString().trim();
        mPassword = et_register_password.getText().toString().trim();
        if(!Uiutils.judgePhoneNums(mNumber)){

            return false;
        }
        if(TextUtils.isEmpty(verifycode) || !Uiutils.passwordIsMatch(mPassword)){
            Uiutils.toast("验证码为空或密码不符合格式");
            return false;
        }

        if(!rb_staff.isChecked() && !rb_boss.isChecked()){
            Uiutils.toast("没有选择身份");
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();

    }

}
