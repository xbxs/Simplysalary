package com.example.atry.simplysalary.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.model.bean.Vacate;
import com.example.atry.simplysalary.ui.adapter.VacateAdapter;
import com.example.atry.simplysalary.ui.view.CustomDatePicker;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.DateFormatUtils;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.SPUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class CreateVacateActivity extends BaseActivity {


    @BindView(R.id.iv_back_createvacate)
    ImageView ivBackCreatevacate;
    @BindView(R.id.tv_begintime_vacate)
    TextView tvBegintimeVacate;
    @BindView(R.id.tv_endtime_vacate)
    TextView tvEndtimeVacate;
    @BindView(R.id.tv_term_vacate)
    TextView tvTermVacate;
    @BindView(R.id.et_reason_vacate)
    EditText etReasonVacate;
    @BindView(R.id.btn_commit_vacate)
    Button btnCommitVacate;
    @BindView(R.id.tv_type_vacate)
    TextView tvTypeVacate;
    private CustomDatePicker mTimerPickerBegin, mTimerPickerEnd;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");


    @Override
    protected int setContentView() {
        return R.layout.activity_create_vacate;
    }

    @Override
    protected void initView() {


        ivBackCreatevacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTypeVacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data[] = {"事假","病假","婚假","丧假","其他"};
                singleDialog(tvTypeVacate,"请假类型",data);
            }
        });
        tvBegintimeVacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimerPickerBegin.show(tvBegintimeVacate.getText().toString());
            }
        });

        tvEndtimeVacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimerPickerEnd.show(tvEndtimeVacate.getText().toString());
            }
        });

        btnCommitVacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!judgeData()) {
                    Uiutils.toast("请填写信息完整");
                }else {
                    requestServerAddVacate();
                }
            }
        });

        initTimerPickerBegin(tvBegintimeVacate);
        initTimerPickerEnd(tvEndtimeVacate);
    }

    private void requestServerAddVacate() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                String groupId = SPUtils.getInstance().getString(ConstantValues.LOGIN_SECTION,"110946096840705");
                //根据群组ID从本地获取群组基本信息
                EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
                //根据群组ID从服务器获取群组基本信息
                SPUtils.getInstance().save(ConstantValues.LOGIN_BOSS,group.getOwner());
                try {
                    EMGroup groupser = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                    SPUtils.getInstance().save(ConstantValues.LOGIN_BOSS,groupser.getOwner());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.addRequestParam("u_phone", EMClient.getInstance().getCurrentUser());
        commonRequest.addRequestParam("v_btime",tvBegintimeVacate.getText().toString());
        commonRequest.addRequestParam("v_term",tvTermVacate.getText().toString());
        commonRequest.addRequestParam("v_shift","1");
        commonRequest.addRequestParam("v_section", SPUtils.getInstance().getString(ConstantValues.LOGIN_SECTION,"110946096840705"));
        commonRequest.addRequestParam("v_reason",etReasonVacate.getText().toString());
        commonRequest.addRequestParam("v_tuser",SPUtils.getInstance().getString(ConstantValues.LOGIN_BOSS,"15643084346"));
        commonRequest.addRequestParam("v_status","1");
        commonRequest.addRequestParam("v_etime",tvEndtimeVacate.getText().toString());
        String v_type = tvTypeVacate.getText().toString();

        if("事假".equals(v_type)){
            v_type = "1";
        }else if("病假".equals(v_type)){
            v_type = "2";
        }else if("婚假".equals(v_type)){
            v_type = "3";
        }else if("丧假".equals(v_type)){
            v_type = "4";
        }else{
            v_type = "5";
        }
        commonRequest.addRequestParam("v_type",v_type);
        commonRequest.addRequestParam("v_rtime",time);
        Vacate vacate = new Vacate();
        vacate.setU_phone(EMClient.getInstance().getCurrentUser());
        vacate.setV_btime(tvBegintimeVacate.getText().toString());
        vacate.setV_term(tvTermVacate.getText().toString());
        vacate.setV_shift("1");
        vacate.setV_section(SPUtils.getInstance().getString(ConstantValues.LOGIN_SECTION,"110946096840705"));
        vacate.setV_tuser(SPUtils.getInstance().getString(ConstantValues.LOGIN_BOSS,"15643084346"));
        vacate.setV_status("1");
        vacate.setV_etime(tvEndtimeVacate.getText().toString());
        vacate.setV_type(v_type);
        vacate.setV_rtime(time);
        HttpUtils.sendPost(ConstantValues.URL_VACATE+"addVacate",commonRequest.getJsonStr(), new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork ERROR"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                if("0".equals(resCode)){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Intent intent = new Intent();
                           Bundle bundle = new Bundle();
                           bundle.putParcelable("vacate",vacate);
                           Log.i("TAG","vacate"+vacate.toString());
                           intent.putExtras(bundle);
                           setResult(1,intent);
                           finish();
                       }
                   });

                }
                Uiutils.toast(resMsg);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
    //单选框
    public void singleDialog(TextView tv,String title,final String data[]) {

        AlertDialog.Builder builder =new AlertDialog.Builder(this)
                .setTitle(title)

                .setSingleChoiceItems(data, -1,new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {

                        String choices =data[i];
                        tv.setText(choices);
                        dialogInterface.dismiss();
                    }
                });

        builder.show();
    }

    private void initTimerPickerBegin(TextView tvdata) {
        String beginTime = "2018-10-17 18:00";
        String endTime = "2040-10-17 18:00";
        long endTimestamp = System.currentTimeMillis();
        tvdata.setText(DateFormatUtils.long2Str(endTimestamp, true));
        tvdata.setText(DateFormatUtils.long2Str(endTimestamp, true));
        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPickerBegin = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                String date = DateFormatUtils.long2Str(timestamp, true);
                String afterdata = tvBegintimeVacate.getText().toString();
                if (date.compareTo(afterdata) == 1 || date.compareTo(afterdata) == 0) {
                    tvEndtimeVacate.setText("请选择");
                }
                tvdata.setText(date);
                if (judgeTerm()) {
                    getVacateTerm(date, afterdata);
                }
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPickerBegin.setCancelable(true);
        // 显示时和分
        mTimerPickerBegin.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPickerBegin.setScrollLoop(false);
        // 允许滚动动画
        mTimerPickerBegin.setCanShowAnim(true);
    }

    private void initTimerPickerEnd(TextView tvdata) {
        String beginTime = "2018-10-17 18:00";
        String endTime = "2040-10-17 18:00";
        long endTimestamp = System.currentTimeMillis();
        tvdata.setText(DateFormatUtils.long2Str(endTimestamp, true));
        tvdata.setText(DateFormatUtils.long2Str(endTimestamp, true));
        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPickerEnd = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                String date = DateFormatUtils.long2Str(timestamp, true);
                String befordate = tvBegintimeVacate.getText().toString();
                if (date.compareTo(befordate) == -1 || date.compareTo(befordate) == 0) {
                    tvBegintimeVacate.setText("请选择");
                }
                tvdata.setText(date);
                if (judgeTerm()) {
                    getVacateTerm(befordate, date);
                }

            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPickerEnd.setCancelable(true);
        // 显示时和分
        mTimerPickerEnd.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPickerEnd.setScrollLoop(false);
        // 允许滚动动画
        mTimerPickerEnd.setCanShowAnim(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //为时长赋值
    public void getVacateTerm(String sbegindate, String senddate) {
        try {
            Date begindate = simpleDateFormat.parse(sbegindate);
            Date enddate = simpleDateFormat.parse(senddate);
            long begindatel = begindate.getTime();
            long enddatel = enddate.getTime();
            long term = (enddatel - begindatel) / (1000 * 60 * 60);

            tvTermVacate.setText(term + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public boolean judgeTerm() {
        String beforeDate = tvBegintimeVacate.getText().toString();
        String endDate = tvEndtimeVacate.getText().toString();
        if ((!beforeDate.equals("请选择")) && (!endDate.equals("请选择")) && (endDate.compareTo(beforeDate) == 1)) {
            return true;
        }
        return false;

    }

    public boolean judgeData() {
        String beforeDate = tvBegintimeVacate.getText().toString();
        String endDate = tvEndtimeVacate.getText().toString();
        if ((!TextUtils.isEmpty(beforeDate)) && (!TextUtils.isEmpty(endDate)) &&  (!beforeDate.equals("请选择")) && (!endDate.equals("请选择")) && (!TextUtils.isEmpty(etReasonVacate.getText().toString()))) {
            return true;
        } else {
            return false;
        }
    }

}
