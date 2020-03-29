package com.example.atry.simplysalary.globe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.atry.simplysalary.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rootContentView());
        ButterKnife.bind(this);
        initView();

        initData();
    }

    /**
     * 加载布局
     */
    protected abstract int setContentView();
    //初始化布局
    protected abstract void initView();
    /**
     * 初始化数据
     */
    protected abstract void initData();
    //点击事件

    /**
     * 获取根View
     */
    public View rootContentView(){
        if(setContentView() != 0){
            return View.inflate(this,setContentView(),null);

        }else {
            return View.inflate(this,R.layout.activity_base,null);
        }
    }

}
