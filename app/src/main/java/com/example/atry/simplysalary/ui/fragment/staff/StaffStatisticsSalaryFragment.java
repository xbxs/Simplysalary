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
public class StaffStatisticsSalaryFragment extends BaseFragment {
    @BindView(R.id.tv_allincome)
    TextView tvAllincome;
    @BindView(R.id.tv_allterm)
    TextView tvAllterm;
    @BindView(R.id.tv_bas)
    TextView tvBas;
    @BindView(R.id.tv_wage)
    TextView tvWage;
    @BindView(R.id.lv_staffsalary)
    ListView lvStaffsalary;
    Unbinder unbinder;
    private Boss_StaffStaticsAdapter staffStaticsSalaryAdapter;
    private List<StaffSalary> staffSalaryList = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.fragment_staffstatisticssalary;
    }

    @Override
    protected void initView() {
            staffStaticsSalaryAdapter = new Boss_StaffStaticsAdapter(getActivity());
            lvStaffsalary.setAdapter(staffStaticsSalaryAdapter);
    }

    @Override
    protected void initData() {
        requestServerStaffStatics();
    }

    protected void requestServerStaffStatics() {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setRequestCode("person_salary");
        commonRequest.addRequestParam("phonenumber", EMClient.getInstance().getCurrentUser());
        commonRequest.addRequestParam("btime", SPUtils.getInstance().getString(ConstantValues.STATICS_LEFT_DATE, "2020-05-04 22:15"));
        commonRequest.addRequestParam("etime", SPUtils.getInstance().getString(ConstantValues.STATICS_RIGHT_DATE, "2020-05-06 22:15"));
        commonRequest.addRequestParam("s_section","1");
        HttpUtils.sendPost(ConstantValues.URL_SALARY + "querySalaryByTimeAndId", commonRequest.getJsonStr(), new Callback() {
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
                    String u_bas = propertyMap.get("u_bas");
                    String u_wage = propertyMap.get("u_wage");
                    Double allmoney = (Double.parseDouble(propertyMap.get("allterm")) + Integer.parseInt(u_bas));
                    String allterm = propertyMap.get("term");
                    staffSalaryList.clear();
                    List<HashMap<String, String>> list = res.getMapList();
                    for (int i = 0; i < list.size(); i++) {
                        StaffSalary staffSalary = new StaffSalary();
                        staffSalary.setS_rtime(list.get(i).get("s_rtime"));
                        staffSalary.setS_term(list.get(i).get("s_term"));
                        staffSalary.setS_shift(list.get(i).get("s_shift"));
                        staffSalary.setS_wage(list.get(i).get("s_wage"));
                        staffSalaryList.add(staffSalary);
                    }
                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            staffStaticsSalaryAdapter.refresh(staffSalaryList);
                            tvAllincome.setText(allmoney+"");
                            tvAllterm.setText(allterm);
                            tvBas.setText(u_bas);
                            tvWage.setText(u_wage);
                        }
                    });
                } else {
                    Uiutils.toast(resMsg);
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


}
