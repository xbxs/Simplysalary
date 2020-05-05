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
import com.example.atry.simplysalary.model.dao.UserAccountDao;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.SPUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

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
        String phonenumber = et_number.getText().toString().trim();
        String password =et_password.getText().toString().trim();
        if(Uiutils.judgePhoneNums(phonenumber) && Uiutils.passwordIsMatch(password)){
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    //去服务器登录
                    EMClient.getInstance().login(phonenumber, password, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.i("TAG","number:"+phonenumber+"  password:"+password);
                            //对模型层数据进行处理
                            User user = new User();
                            user.setPhonenumber(phonenumber);
                            Model.getInstance().loginSuccess(user);
                            //保存用户账号信息到本地数据库
                            loginPost(phonenumber,password);
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
    private void loginPost(String phonenumber,String password) {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("phonenumber",phonenumber);
        request.addRequestParam("password",password);
        //调用请求网络的方法
        infoPost(ConstantValues.URL_Login,request.getJsonStr());
    }
    private void infoPost(String url_login, String jsonStr) {
        HttpUtils.sendPost(url_login,jsonStr, new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Uiutils.toast("NetWork ERROR:"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());

                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                if(resCode.equals("100")){
                    HashMap<String,String> map = res.getPropertyMap();
                    //提示登录成功
                    User user = new User(map.get("u_phone"),map.get("u_name"),map.get("u_head"),Integer.parseInt(map.get("u_flag")),map.get("u_section"),Integer.parseInt(map.get("u_bas")),Integer.parseInt(map.get("u_wage")));
                   //保存到本地数据库
                    insertdb(user);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Uiutils.toast("登录成功");
                            // 跳转到主页
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            SPUtils.getInstance().save(ConstantValues.LOGIN_IDENTITY,Integer.parseInt(map.get("u_flag")));
                            SPUtils.getInstance().save(ConstantValues.LOGIN_SECTION,map.get("u_section"));
                            finish();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Uiutils.toast(resMsg);
                        }
                    });
                }
            }
        });
    }
    //插入本地数据库
    boolean insertdb(User user){
        UserAccountDao userAccountDao = Model.getInstance().getUserAccountDao();
        User queryuser = userAccountDao.getUser(user.getPhonenumber());
        if(queryuser == null){
            long count = userAccountDao.addUser(user);
            if(count > 1)
                return true;
        }
        return false;
    }


}
