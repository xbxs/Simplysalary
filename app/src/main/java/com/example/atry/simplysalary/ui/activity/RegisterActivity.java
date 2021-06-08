package com.example.atry.simplysalary.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.model.dao.UserAccountDao;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    private Button btn_send_verify, btn_register;
    private EditText et_register_number, et_register_verify, et_register_password;
    private RadioGroup rg_status;
    private RadioButton rb_staff, rb_boss;

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

        EventHandler eventHandler = new EventHandler() {
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

        switch (view.getId()) {
            //发送验证码按钮
            case R.id.btn_send_verify:
                if (!Uiutils.judgePhoneNums(phoneNums)) {
                    return;
                }
                //验证码
                SMSSDK.getVerificationCode("86", phoneNums);
                btn_send_verify.setClickable(false);
                btn_send_verify.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }

                            SystemClock.sleep(1000);
                        }
                        handler.sendEmptyMessage(-8);
                    }

                }).start();
                break;
            case R.id.btn_register:

                if (!inputIntegrity()) {
                    return;
                }
                SMSSDK.submitVerificationCode("86",phoneNums,et_register_verify.getText().toString());
//                registerUser();
                break;
            default:
                break;
        }
    }

    private boolean registerUser() {
//        int flag = rb_staff.isChecked() ? 0 : 1;
//        User user = new User(mNumber, "备注", "", flag, "", 0, 0);
//        RegisterPost(user);
//        return false;
        //去服务器注册账号

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(mNumber, mPassword);
                    //如果环信服务器注册成功
                    int flag = rb_staff.isChecked() ? 0 : 1;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(mNumber, "备注", "", flag, "", 0, 0);
                            RegisterPost(user);
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                            insertdb();
                            finish();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "注册失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        return false;
    }

    //去自己的服务器添加用户
    private void RegisterPost(User user) {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("account", user.getPhonenumber());
        request.addRequestParam("password", mPassword);
        request.addRequestParam("flag", user.getFlag() + "");
        request.addRequestParam("u_name", etRegisterName.getText().toString());
        //调用请求网络的方法
        infoPost(ConstantValues.URL_Register, request.getJsonStr());
    }

    private void infoPost(String url_login, String jsonStr) {
        HttpUtils.sendPost(url_login, jsonStr, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                showResponse("NetWork ERROR" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                showResponse(resMsg);
                if (resCode.equals(ConstantValues.SUCCESSCODE_REGISTER)) {
                    finish();
                }
            }
        });
    }

    //显示请求数据结果
    private void showResponse(String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Uiutils.toast(info);
            }
        });

    }

    boolean insertdb() {
        UserAccountDao userAccountDao = Model.getInstance().getUserAccountDao();
        User user = userAccountDao.getUser(mNumber);
        if (user == null) {
            int flag = rb_staff.isChecked() ? 0 : 1;
            User adduser = new User(mNumber, "备注", "", flag, "", 0, 0);
            long count = userAccountDao.addUser(adduser);
            if (count > 1)
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == -9) {
                btn_send_verify.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                btn_send_verify.setText("获取验证码");
                btn_send_verify.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                // 提交验证码成功
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        registerUser();
                    }
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Uiutils.getContext(), "正在获取验证码!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Uiutils.getContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

        }
    }


    private boolean inputIntegrity() {
        String verifycode = et_register_verify.getText().toString().trim();
        mNumber = et_register_number.getText().toString().trim();
        mPassword = et_register_password.getText().toString().trim();
        if (!Uiutils.judgePhoneNums(mNumber)) {
            return false;
        }
        if (TextUtils.isEmpty(etRegisterName.getText().toString())) {
            Uiutils.toast("姓名为空");
            return false;
        }
        if (TextUtils.isEmpty(verifycode) || !Uiutils.passwordIsMatch(mPassword)) {
            Uiutils.toast("验证码为空或密码不符合格式");
            return false;
        }

        if (!rb_staff.isChecked() && !rb_boss.isChecked()) {
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
