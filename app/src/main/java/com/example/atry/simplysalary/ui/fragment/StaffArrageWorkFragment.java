package com.example.atry.simplysalary.ui.fragment;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.calendar.cons.DPMode;
import com.example.atry.simplysalary.calendar.views.MonthView;
import com.example.atry.simplysalary.calendar.views.WeekView;
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
public class StaffArrageWorkFragment extends BaseFragment implements MonthView.OnDateChangeListener,MonthView.OnDatePickedListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.month_calendar)
    MonthView monthView;
    @BindView(R.id.week_text)
    MyTextView weekText;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.week_calendar)
    WeekView weekView;


    private Calendar now;

    @Override
    protected int setContentView() {
        return R.layout.fragment_staffarragework;
    }

    @Override
    protected void initView() {
        //得到当前系统的日历
        now = Calendar.getInstance();
        toolbar.setTitle(now.get(Calendar.YEAR) + "." + (now.get(Calendar.MONTH) + 1));
        monthView.setDPMode(DPMode.SINGLE);
        monthView.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1);
        monthView.setFestivalDisplay(true);
        monthView.setTodayDisplay(true);
        monthView.setOnDateChangeListener(this);
        monthView.setOnDatePickedListener(this);

        weekView.setDPMode(DPMode.SINGLE);
        weekView.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1);
        weekView.setFestivalDisplay(true);
        weekView.setTodayDisplay(true);
        weekView.setOnDatePickedListener(this);
        for(int i = 0; i< 7; i++) {
            ContentItemViewAbs cia = new ContentItemViewAbs(Uiutils.getContext());
            contentLayout.addView(cia);
        }
    }

    @Override
    protected void initData() {

    }



    @Override
    public void onDateChange(int year, int month) {

        if (null != toolbar)
            toolbar.setTitle(year + "." + month);
    }

    @Override
    public void onDatePicked(String date) {
        try {
            //时间
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
            //星期几
            SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
            Date choosedate = format1.parse(date);
            //如果点击的日期 == 今天的日期，则不显示星期几
            weekText.setText(format2.format(choosedate));
            if (date.equals(now.get(Calendar.YEAR) + "." + (now.get(Calendar.MONTH) + 1) + "." + now.get(Calendar.DAY_OF_MONTH))) {
                weekText.setVisibility(View.INVISIBLE);
            } else {
                weekText.setVisibility(View.VISIBLE);
            }
            //先将contentLayout的布局清空 这个布局添加的是热门事件
            contentLayout.removeAllViews();
            for(int i = 0; i< 2; i++) {
                ContentItemViewAbs cia = new ContentItemViewAbs(Uiutils.getContext());
                contentLayout.addView(cia);
            }
            Toast.makeText(Uiutils.getContext(), "" + date, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
