package com.example.atry.simplysalary.ui.fragment.staff;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.ui.fragment.BaseFragment;
import com.example.atry.simplysalary.ui.view.ContentItemViewAbs;
import com.example.atry.simplysalary.ui.view.MyTextView;
import com.example.atry.simplysalary.utils.Uiutils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

;

/**
 * 李维: TZZ on 2019-12-30 15:10
 * 邮箱: 3182430026@qq.com
 */
public class StaffArrageWorkFragment extends BaseFragment{
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;



    private Calendar now;

    @Override
    protected int setContentView() {
        return R.layout.fragment_staffarragework;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }
}
