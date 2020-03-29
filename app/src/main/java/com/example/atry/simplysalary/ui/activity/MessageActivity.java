package com.example.atry.simplysalary.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.ui.fragment.MessageFragment;

public class MessageActivity extends BaseActivity {


    @Override
    protected int setContentView() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        MessageFragment messageFragment = new MessageFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_message,messageFragment).commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
