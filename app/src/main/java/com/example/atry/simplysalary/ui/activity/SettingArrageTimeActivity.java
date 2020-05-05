package com.example.atry.simplysalary.ui.activity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.SPUtils;
import com.example.atry.simplysalary.utils.Uiutils;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingArrageTimeActivity extends BaseActivity {


    @BindView(R.id.iv_arragetime_back)
    ImageView ivArragetimeBack;
    @BindView(R.id.tv_morning_start)
    TextView tvMorningStart;
    @BindView(R.id.tv_morning_line)
    TextView tvMorningLine;
    @BindView(R.id.tv_morning_end)
    TextView tvMorningEnd;
    @BindView(R.id.tv_afternoon_start)
    TextView tvAfternoonStart;
    @BindView(R.id.tv_afternoon_line)
    TextView tvAfternoonLine;
    @BindView(R.id.tv_afternoon_end)
    TextView tvAfternoonEnd;
    @BindView(R.id.tv_evening_start)
    TextView tvEveningStart;
    @BindView(R.id.tv_evening_line)
    TextView tvEveningLine;
    @BindView(R.id.tv_evening_end)
    TextView tvEveningEnd;

    private Calendar calendar = Calendar.getInstance(Locale.CHINA);

    @Override
    protected int setContentView() {
        return R.layout.activity_setting_arrage_time;
    }

    @Override
    protected void initView() {
        //取出存的时间为控件赋值
        String morning[] = SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_MORING,"9:00-12:00").split("-");
        String afternoon[] = SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_AFTERNOON,"12:00-17:00").split("-");
        String evening[] = SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_EVENING,"17:00-22:00").split("-");
        ivArragetimeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvMorningStart.setText(morning[0]);
        tvEveningEnd.setText(morning[1]);

        tvAfternoonStart.setText(afternoon[0]);
        tvAfternoonEnd.setText(afternoon[1]);

        tvEveningStart.setText(evening[0]);
        tvEveningEnd.setText(evening[1]);

        tvMorningStart.setOnClickListener(this);
        tvMorningEnd.setOnClickListener(this);
        tvAfternoonStart.setOnClickListener(this);
        tvAfternoonEnd.setOnClickListener(this);
        tvEveningStart.setOnClickListener(this);
        tvEveningEnd.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        SPUtils.getInstance().save(ConstantValues.ARRAGEWORK_MORING,tvMorningStart.getText().toString()+"-"+tvMorningEnd.getText().toString());
        SPUtils.getInstance().save(ConstantValues.ARRAGEWORK_AFTERNOON,tvAfternoonStart.getText().toString()+"-"+tvAfternoonEnd.getText().toString());
        SPUtils.getInstance().save(ConstantValues.ARRAGEWORK_EVENING,tvEveningStart.getText().toString()+"-"+tvEveningEnd.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_morning_start:
                showTimePickerDialog(SettingArrageTimeActivity.this,2,tvMorningStart,calendar);
                break;
            case R.id.tv_morning_end:
                showTimePickerDialog(SettingArrageTimeActivity.this,2,tvMorningEnd,calendar);
                break;
            case R.id.tv_afternoon_start:
                showTimePickerDialog(SettingArrageTimeActivity.this,2,tvAfternoonStart,calendar);
                break;
            case R.id.tv_afternoon_end:
                showTimePickerDialog(SettingArrageTimeActivity.this,2,tvAfternoonEnd,calendar);
                break;
            case R.id.tv_evening_start:
                showTimePickerDialog(SettingArrageTimeActivity.this,2,tvEveningStart,calendar);
                break;
            case R.id.tv_evening_end:
                showTimePickerDialog(SettingArrageTimeActivity.this,2,tvEveningEnd,calendar);
            default:
                break;
        }
    }

    /**
     * 时间选择
     *
     */
    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText(hourOfDay+":"+minute );
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }

}
