package com.example.atry.simplysalary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.bean.StaffSalary;
import com.example.atry.simplysalary.model.bean.Vacate;
import com.example.atry.simplysalary.model.interface_package.OnItemClickListener;
import com.example.atry.simplysalary.ui.adapter.VacateAdapter;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class VacateActivity extends BaseActivity {

    String vacateName = "";
    @BindView(R.id.iv_back_vacate)
    ImageView ivBackVacate;
    @BindView(R.id.iv_vacate_add)
    ImageView ivVacateAdd;
    @BindView(R.id.lv_vacate_apply)
    ListView lvVacateApply;
    private List<Vacate> mvacates = new ArrayList<>();
    private VacateAdapter vacateAdapter ;
    private int flag= 0;
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
                if(view.getId() == R.id.btn_refuse_vacate){
                    responseServerApply("2",position);
                }else{
                    responseServerApply("3",position);
                }
        }
    };
    @Override
    protected int setContentView() {
        return R.layout.activity_vacate;
    }

    @Override
    protected void initView() {
        flag = SPUtils.getInstance().getInt(ConstantValues.LOGIN_IDENTITY,0);
        vacateAdapter = new VacateAdapter(VacateActivity.this);
        vacateAdapter.setselfItemClickListener(onItemClickListener);
        lvVacateApply.setAdapter(vacateAdapter);
        if(1== flag){
            ivVacateAdd.setVisibility(View.INVISIBLE);
        }else{
            ivVacateAdd.setVisibility(View.VISIBLE);
        }
        ivVacateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacateActivity.this, CreateVacateActivity.class);
                startActivityForResult(intent,1);
            }
        });

        ivBackVacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        CommonRequest commonRequest = new CommonRequest();

        if(0 == flag){
                commonRequest.addRequestParam("flag","0");
        }else{
                commonRequest.addRequestParam("flag","1");
        }
        commonRequest.addRequestParam("v_tuser", EMClient.getInstance().getCurrentUser());
        HttpUtils.sendPost(ConstantValues.URL_VACATE+"queryVacateByApply",commonRequest.getJsonStr(), new okhttp3.Callback() {
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
                    List<HashMap<String, String>> list = res.getMapList();
                    mvacates.clear();
                    for (int i = 0; i < list.size(); i++) {
                        Vacate vacate = new Vacate();
                        vacate.setV_id(list.get(i).get("v_id"));
                        vacate.setU_phone(list.get(i).get("u_phone"));
                        vacate.setU_name(list.get(i).get("u_name"));
                        vacateName = list.get(i).get("u_name");
                        vacate.setV_rtime(list.get(i).get("v_rtime"));
                        vacate.setV_term(list.get(i).get("v_term"));
                        vacate.setV_type(list.get(i).get("v_type"));
                        vacate.setV_btime(list.get(i).get("v_btime"));
                        vacate.setV_etime(list.get(i).get("v_etime"));
                        vacate.setV_status(list.get(i).get("v_status"));
                        mvacates.add(vacate);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Collections.reverse(mvacates);
                            vacateAdapter.refresh(mvacates);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            Vacate vacate = data.getParcelableExtra("vacate");
            vacate.setU_name(vacateName);
            mvacates.add(0,vacate);
            vacateAdapter.refresh(mvacates);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void responseServerApply(String v_status,int position){
        CommonRequest request = new CommonRequest();
        Vacate vacate = mvacates.get(position);
        vacate.setV_status(v_status);
        request.addRequestParam("v_status",vacate.getV_status());
        request.addRequestParam("v_id",vacate.getV_id());
        HttpUtils.sendPost(ConstantValues.URL_VACATE+"queryVacateById", request.getJsonStr(),new okhttp3.Callback() {
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
                            vacateAdapter.refresh(mvacates);
                        }
                    });

                }
                Uiutils.toast(resMsg);
            }
        });

    }
}
