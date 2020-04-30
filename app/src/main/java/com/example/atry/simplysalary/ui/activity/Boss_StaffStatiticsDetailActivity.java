package com.example.atry.simplysalary.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.bean.StaffSalary;
import com.example.atry.simplysalary.ui.adapter.Boss_StaffStaticsAdapter;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.Uiutils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Boss_StaffStatiticsDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_salary_bas)
    TextView tvSalaryBas;
    @BindView(R.id.tv_salary_wage)
    TextView tvSalaryWage;
    @BindView(R.id.lv_staffstaticsdetail)
    ListView lvStaffstaticsdetail;
    @BindView(R.id.tv_staffstatics_name)
    TextView tvStaffstaticsName;
    private String phonenumber, section_id;
    private Boss_StaffStaticsAdapter boss_staffStaticsAdapter;
    private List<StaffSalary> salaryList = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.activity_boss__staff_statitics_detail;
    }

    @Override
    protected void initView() {
        phonenumber = getIntent().getStringExtra("phonenumber");
        section_id = getIntent().getStringExtra("section_id");
        boss_staffStaticsAdapter = new Boss_StaffStaticsAdapter(Boss_StaffStatiticsDetailActivity.this);
        lvStaffstaticsdetail.setAdapter(boss_staffStaticsAdapter);
    }

    @Override
    protected void initData() {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setRequestCode("person_salary");
        commonRequest.addRequestParam("phonenumber", phonenumber);
        commonRequest.addRequestParam("s_section", section_id);
        commonRequest.addRequestParam("btime", "2020042811");
        commonRequest.addRequestParam("etime", "2020042811");

        HttpUtils.sendPost(ConstantValues.URL_SALARY + "querySalaryByTimeAndId", commonRequest.getJsonStr(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork_ERROR:" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                HashMap<String, String> propertyMap = res.getPropertyMap();
                String u_wage = propertyMap.get("u_wage");
                String u_bas = propertyMap.get("u_bas");
                String u_name = propertyMap.get("u_name");
                if (resCode.equals("0")) {
                    List<HashMap<String, String>> list = res.getMapList();
                    for (int i = 0; i < list.size(); i++) {
                        StaffSalary staffSalary = new StaffSalary();
                        staffSalary.setS_rtime(list.get(i).get("s_rtime"));
                        staffSalary.setS_term(list.get(i).get("s_term"));
                        staffSalary.setS_shift(list.get(i).get("s_shift"));
                        salaryList.add(staffSalary);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            boss_staffStaticsAdapter.refresh(salaryList);
                            tvStaffstaticsName.setText(TextUtils.isEmpty(u_name) ? phonenumber : u_name);
                            tvSalaryBas.setText(u_bas);
                            tvSalaryWage.setText(u_wage);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
