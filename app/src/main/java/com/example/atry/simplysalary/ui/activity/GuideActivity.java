package com.example.atry.simplysalary.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.model.db.UserDataHelper;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.SPUtils;
import com.hyphenate.chat.EMClient;

public class GuideActivity extends BaseActivity {


    private MyHandler mHandler;

    @Override
    protected int setContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        mHandler = new MyHandler();
        mHandler.sendEmptyMessageDelayed(0,3000);
        boolean isfirstenter = SPUtils.getInstance().getBoolean( ConstantValues.IS_FIRSTENTER,true);
        if(isfirstenter) {
            UserDataHelper dataHelper = new UserDataHelper(this);
            dataHelper.getWritableDatabase();
            SPUtils.getInstance().save(ConstantValues.IS_FIRSTENTER,false);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //如果当前的activity已经退出，则不处理
            if(isFinishing()){
                return;
            }

         //判断进入主页面还是登陆页面
         toMainOrLogin();
        }
    }
    //判断进入主页还是登陆页面  如果登陆过则进入主页，否则进入登陆页面 并结束当前界面
    private void toMainOrLogin() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //判断当前账号是否登录过
                if(EMClient.getInstance().isLoggedInBefore()){
                    //获取到当前用户登录的信息
                    User user = Model.getInstance().getUserAccountDao().getUser(EMClient.getInstance().getCurrentUser());
                    Intent intent = null;
                    if(user != null){
                        intent = new Intent(GuideActivity.this,MainActivity.class);
                        Model.getInstance().loginSuccess(user);
                    }else {
//                        Model.getInstance().loginSuccess(user);
                        intent = new Intent(GuideActivity.this,LoginActivity.class);
                    }
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(GuideActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果发送过消息，则销毁消息
        mHandler.removeCallbacksAndMessages(null);
    }
}
