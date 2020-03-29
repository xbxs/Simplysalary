package com.example.atry.simplysalary.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends BaseActivity {


    private String mhxid;

    @Override
    protected int setContentView() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView() {
        //这个fragment为环信里内置的聊天界面
        EaseChatFragment chatFragment = new EaseChatFragment();
        mhxid = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);

        chatFragment.setArguments(getIntent().getExtras());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_chat,chatFragment).commit();
    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View v) {

    }
}
