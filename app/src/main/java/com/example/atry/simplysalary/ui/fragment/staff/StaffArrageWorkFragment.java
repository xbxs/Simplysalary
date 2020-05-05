package com.example.atry.simplysalary.ui.fragment.staff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.ui.fragment.BaseFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

;

/**
 * 李维: TZZ on 2019-12-30 15:10
 * 邮箱: 3182430026@qq.com
 */
public class StaffArrageWorkFragment extends BaseFragment {
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.cdv_arragework_staff)
    CalendarView cdvArrageworkStaff;
    Unbinder unbinder;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

}
