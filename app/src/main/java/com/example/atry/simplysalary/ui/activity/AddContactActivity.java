package com.example.atry.simplysalary.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactActivity extends BaseActivity {


    @BindView(R.id.et_add_phone)
    EditText etAddPhone;
    @BindView(R.id.iv_add_head)
    ImageView ivAddHead;
    @BindView(R.id.tv_add_name)
    TextView tvAddName;

    @BindView(R.id.rl_add_contact)
    RelativeLayout rlAddContact;
    @BindView(R.id.tv_add_find)
    TextView tvAddFind;
    @BindView(R.id.btn_add_contact)
    Button btnAddContact;
    private String mName;

    @Override
    protected int setContentView() {
        return R.layout.activity_add_contact;
    }

    @Override
    protected void initView() {
        tvAddFind.setOnClickListener(this);
        btnAddContact.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add_find:
                find();
                break;
            case R.id.btn_add_contact:
                add();
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    /**
     * 查找用户输入的账号
     */
    private void find() {
        //去得到输入框中的值
        mName = etAddPhone.getText().toString().trim();
        //判断值是否为空
        if (TextUtils.isEmpty(mName)) {
            return;
        }
        //去服务区判断当前的用户是否存在
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //如果存在就显示布局，并将查到的信息赋值到布局上
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //显示布局，并赋值
                        rlAddContact.setVisibility(View.VISIBLE);
                        tvAddName.setText(mName);
                    }
                });
            }
        });

    }
    //添加好友
    private void add() {
        //得到服务器返回的好友phone

        //向环信服务器发送添加好友的请求
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(mName,"添加好友");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Uiutils.toast("发送请求成功");
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Uiutils.toast("发送失败："+e.toString());
                        }
                    });
                }
            }
        });
    }


}
