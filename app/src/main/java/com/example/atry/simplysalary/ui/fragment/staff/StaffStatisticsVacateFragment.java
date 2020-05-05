package com.example.atry.simplysalary.ui.fragment.staff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.StaffSalary;
import com.example.atry.simplysalary.ui.adapter.Boss_StaffStaticsAdapter;
import com.example.atry.simplysalary.ui.fragment.BaseFragment;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.SPUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 李维: TZZ on 2020-01-02 18:57
 * 邮箱: 3182430026@qq.com
 */
public class StaffStatisticsVacateFragment extends BaseFragment {
    @BindView(R.id.tv_staffvacate_moring)
    TextView tvStaffvacateMoring;
    @BindView(R.id.tv_staffvacate_afternoon)
    TextView tvStaffvacateAfternoon;
    @BindView(R.id.tv_staffvacate_evening)
    TextView tvStaffvacateEvening;
    @BindView(R.id.lv_vacate)
    ListView lvVacate;
    Unbinder unbinder;
    private Boss_StaffStaticsAdapter staffStaticsSalaryAdapter;
    private List<StaffSalary> staffSalaryList = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.fragment_staffstatisticsvacate;
    }

    @Override
    protected void initView() {
        staffStaticsSalaryAdapter = new Boss_StaffStaticsAdapter(getActivity());
        lvVacate.setAdapter(staffStaticsSalaryAdapter);
    }

    @Override
    protected void initData() {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setRequestCode("person_Vacate");
        commonRequest.addRequestParam("phonenumber", EMClient.getInstance().getCurrentUser());
        commonRequest.addRequestParam("btime", SPUtils.getInstance().getString(ConstantValues.STATICS_LEFT_DATE, "2020-05-04 22:15"));
        commonRequest.addRequestParam("etime", SPUtils.getInstance().getString(ConstantValues.STATICS_RIGHT_DATE, "2020-05-06 22:15"));
        commonRequest.addRequestParam("s_section","1");
        HttpUtils.sendPost(ConstantValues.URL_VACATE + "queryVacateByTimeAndId", commonRequest.getJsonStr(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork ERROR:" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                if (resCode.equals("0")) {
                    HashMap<String, String> propertyMap = res.getPropertyMap();
                    String allmoring = propertyMap.get("allmoring");
                    String allafternoon = propertyMap.get("allafternoon");
                    String allevening = propertyMap.get("allevening");
                    String allterm = propertyMap.get("term");

                    List<HashMap<String, String>> list = res.getMapList();
                    for (int i = 0; i < list.size(); i++) {
                        StaffSalary staffSalary = new StaffSalary();
                        staffSalary.setS_rtime(list.get(i).get("s_rtime"));
                        staffSalary.setS_term(list.get(i).get("s_term"));
                        staffSalary.setS_shift(list.get(i).get("s_shift"));
                        staffSalaryList.add(staffSalary);
                    }
                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            staffStaticsSalaryAdapter.refresh(staffSalaryList);
                            tvStaffvacateMoring.setText(allmoring);
                            tvStaffvacateAfternoon.setText(allafternoon);
                            tvStaffvacateEvening.setText(allevening);
                        }
                    });
                } else {
                    Uiutils.toast(resMsg);
                }

            }
        });
    }

}
