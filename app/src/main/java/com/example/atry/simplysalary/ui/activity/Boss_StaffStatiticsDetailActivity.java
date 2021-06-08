package com.example.atry.simplysalary.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.atry.simplysalary.utils.SPUtils;
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
    @BindView(R.id.ll_staffstatics_salary)
    LinearLayout llStaffstaticsSalary;
    @BindView(R.id.iv_staffstaics_add)
    ImageView ivStaffstaicsAdd;
    @BindView(R.id.tv_statics_detail_term)
    TextView tvStaticsDetailTerm;
    @BindView(R.id.tv_staticslist_wage_)
    TextView tvStaticslistWage;
    private String phonenumber, section_id;
    private Boss_StaffStaticsAdapter boss_staffStaticsAdapter;
    private List<StaffSalary> salaryList = new ArrayList<>();
    private String maddname;
    private String u_wage;

    private String flag;

    @Override
    protected int setContentView() {
        return R.layout.activity_boss__staff_statitics_detail;
    }

    @Override
    protected void initView() {
        flag = getIntent().getStringExtra("salary_or_vacate");
        phonenumber = getIntent().getStringExtra("phonenumber");
        section_id = getIntent().getStringExtra("section_id");
        boss_staffStaticsAdapter = new Boss_StaffStaticsAdapter(Boss_StaffStatiticsDetailActivity.this);
        lvStaffstaticsdetail.setAdapter(boss_staffStaticsAdapter);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        if (flag.equals("vacate")) {
            llStaffstaticsSalary.setVisibility(View.GONE);
            ivStaffstaicsAdd.setVisibility(View.GONE);
            tvStaticslistWage.setVisibility(View.GONE);
            tvStaticsDetailTerm.setText("类型");
        }
        llStaffstaticsSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelfDialog(Boss_StaffStatiticsDetailActivity.this);
            }
        });

        ivStaffstaicsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("salary".equals(flag)) {
                    Intent intent = new Intent(Boss_StaffStatiticsDetailActivity.this, RecordSalaryActivity.class);
                    intent.putExtra("addname", maddname);
                    intent.putExtra("phonenumber", phonenumber);
                    intent.putExtra("s_section", section_id);
                    intent.putExtra("u_wage", u_wage);
                    startActivityForResult(intent, 1);
                }
            }
        });

        lvStaffstaticsdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaffSalary staffSalary = salaryList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(Boss_StaffStatiticsDetailActivity.this);
                builder.setTitle("删除");
                String month = staffSalary.getS_rtime().substring(5, 7);
                String day = staffSalary.getS_rtime().substring(8, 10);
                builder.setMessage("删除这条" + month + "月" + day + "日的记录吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestServerDeleteStatics(staffSalary, position);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void requestServerDeleteStatics(StaffSalary staffSalary, int position) {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.addRequestParam("u_phone", phonenumber);
        commonRequest.addRequestParam("s_section", section_id);
        commonRequest.addRequestParam("s_rtime", staffSalary.getS_rtime());
        String URL;
        if ("salary".equals(flag)) {
            URL = ConstantValues.URL_SALARY + "deleteSalary";
        } else {
            URL = ConstantValues.URL_VACATE + "deleteVacate";
        }
        HttpUtils.sendPost(URL, commonRequest.getJsonStr(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork_ERROR:" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                if ("0".equals(resCode)) {
                    salaryList.remove(position);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            boss_staffStaticsAdapter.refresh(salaryList);
                        }
                    });

                }
                Uiutils.toast(resMsg);
            }
        });
    }

    @Override
    protected void initData() {

        CommonRequest commonRequest = new CommonRequest();
        String URL;
        if (flag.equals("salary")) {
            URL = ConstantValues.URL_SALARY + "querySalaryByTimeAndId";
            commonRequest.setRequestCode("person_salary");
        } else {
            URL = ConstantValues.URL_VACATE + "queryVacateByTimeAndId";
            commonRequest.setRequestCode("person_Vacate");
        }
        commonRequest.addRequestParam("phonenumber", phonenumber);
        commonRequest.addRequestParam("s_section", section_id);
        commonRequest.addRequestParam("btime", SPUtils.getInstance().getString(ConstantValues.STATICS_LEFT_DATE, "2020-05-04 19:15"));
        commonRequest.addRequestParam("etime", SPUtils.getInstance().getString(ConstantValues.STATICS_RIGHT_DATE, "2020-05-04 22:15"));

        HttpUtils.sendPost(URL, commonRequest.getJsonStr(), new Callback() {
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
                u_wage = propertyMap.get("u_wage");
                String u_bas = propertyMap.get("u_bas");
                String u_name = propertyMap.get("u_name");
                if (resCode.equals("0")) {
                    List<HashMap<String, String>> list = res.getMapList();
                    for (int i = 0; i < list.size(); i++) {
                        StaffSalary staffSalary = new StaffSalary();
                        staffSalary.setS_rtime(list.get(i).get("s_rtime"));
                        staffSalary.setS_term(list.get(i).get("s_term"));
                        staffSalary.setS_shift(list.get(i).get("s_shift"));
                        staffSalary.setS_wage(list.get(i).get("s_wage"));
                        salaryList.add(staffSalary);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            boss_staffStaticsAdapter.refresh(salaryList);
                            maddname = "null".equals(u_name) ? phonenumber : u_name;
                            tvStaffstaticsName.setText(maddname);
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

    public void showSelfDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = View.inflate(activity, R.layout.item_dialog, null);
        EditText et_itemdialog_bas = view.findViewById(R.id.et_itemdialog_bas);
        EditText et_itemdialog_wage = view.findViewById(R.id.et_itemdialog_wage);

        builder.setView(view);
        Dialog dialog;
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateSalaryToServer(et_itemdialog_bas.getText().toString(), et_itemdialog_wage.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }


    //去服务器更新数据
    public void updateSalaryToServer(String bas, String wage) {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.addRequestParam("phonenumber", phonenumber);
        commonRequest.addRequestParam("u_bas", bas);
        commonRequest.setRequestCode("salary");
        commonRequest.addRequestParam("u_wage", wage);
        HttpUtils.sendPost(ConstantValues.URL_USER + "updateUser", commonRequest.getJsonStr(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork ERROR:" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resCode.equals("0")) {
                            tvSalaryBas.setText(bas);
                            tvSalaryWage.setText(wage);
                        } else {
                            Uiutils.toast(resMsg);
                        }
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            String s_rtime = data.getStringExtra("s_rtime");
            String s_term = data.getStringExtra("s_term");
            String s_shift = data.getStringExtra("s_shift");
            String s_wage = data.getStringExtra("s_wage");
            StaffSalary salary = new StaffSalary(s_rtime, s_term, s_shift,s_wage,"1");
            salaryList.add(salary);
            boss_staffStaticsAdapter.refresh(salaryList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
