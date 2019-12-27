package com.example.atry.simplysalary.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.UserDataHelper;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.PrefUtils;

public class GuideActivity extends BaseActivity {


    @Override
    protected int setContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        MyHandler handler = new MyHandler();
        handler.sendEmptyMessageDelayed(0,3000);
        boolean isfirstenter = PrefUtils.getBoolean(this, ConstantValues.IS_FIRSTENTER,true);
        if(isfirstenter) {
            UserDataHelper dataHelper = new UserDataHelper(this);
            dataHelper.getWritableDatabase();
            PrefUtils.setBoolean(this,ConstantValues.IS_FIRSTENTER,false);
        }



    }

    @Override
    protected void initData() {

    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(isFinishing()){
                return;
            }
            Intent intent = new Intent(GuideActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
         //判断进入主页面还是登陆页面
         toMainOrLogin();
        }
    }

    private void toMainOrLogin() {
        new Thread(){
            @Override
            public void run() {
                super.run();


            }
        }.start();
    }
}
