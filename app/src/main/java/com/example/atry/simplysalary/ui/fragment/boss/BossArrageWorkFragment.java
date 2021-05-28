package com.example.atry.simplysalary.ui.fragment.boss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.Schedule;
import com.example.atry.simplysalary.model.bean.Vacate;
import com.example.atry.simplysalary.ui.activity.Boss_StaffStatiticsDetailActivity;
import com.example.atry.simplysalary.ui.activity.PickContactActivity;
import com.example.atry.simplysalary.ui.adapter.BossArrageWorkAdapter;
import com.example.atry.simplysalary.ui.fragment.BaseFragment;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.SPUtils;
import com.example.atry.simplysalary.utils.Uiutils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 李维:
 * 邮箱: 3182430026@qq.com
 */
public class BossArrageWorkFragment extends BaseFragment {
    @BindView(R.id.cav_arragework_boss)
    CalendarView cavArrageworkBoss;
    @BindView(R.id.rb_moring_boss)
    RadioButton rbMoringBoss;
    @BindView(R.id.rb_after_boss)
    RadioButton rbAfterBoss;
    @BindView(R.id.rb_evening_boss)
    RadioButton rbEveningBoss;
    @BindView(R.id.rg_boos_arragework)
    RadioGroup rgBoosArragework;
    @BindView(R.id.lv_arragework)
    ListView lvArragework;
    private Button btn_add_arragework;
    private BossArrageWorkAdapter bossArrageWorkAdapter;
    private String s_time;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date(System.currentTimeMillis());

    private List<Schedule> mlistMoring = new ArrayList<>();
    private List<Schedule> mlistAfter = new ArrayList<>();
    private List<Schedule> mlistEvening = new ArrayList<>();
    //如果为零则
    @Override
    protected int setContentView() {
        return R.layout.fragment_bossarragework;
    }

    @Override
    protected void initView() {
        s_time = simpleDateFormat.format(date);
        rbMoringBoss.setChecked(true);
        bossArrageWorkAdapter = new BossArrageWorkAdapter(getActivity());
        cavArrageworkBoss.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int testmonth = month+1;
                String smonth,sday;
                if(testmonth >=1 && testmonth<10){
                    smonth = "0"+testmonth;
                }else{
                    smonth = testmonth+"";
                }
                if(dayOfMonth >=1 &&dayOfMonth <10){
                    sday= "0"+dayOfMonth;
                }else{
                    sday =dayOfMonth+"";
                }
                s_time = year+smonth+sday;
                mlistMoring.clear();
                mlistAfter.clear();
                mlistEvening.clear();
                requestServerVacate();
            }
        });
        rbMoringBoss.setText("中班\n"+SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_MORING,"9:00-12:00"));
        rbAfterBoss.setText("午班\n"+SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_AFTERNOON,"12:00-17:00"));
        rbEveningBoss.setText("晚班\n"+SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_EVENING,"17:00-22:00"));
        rgBoosArragework.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_moring_boss:
                            bossArrageWorkAdapter.refresh(mlistMoring);
                        break;
                    case R.id.rb_after_boss:
                            bossArrageWorkAdapter.refresh(mlistAfter);
                        break;
                    case R.id.rb_evening_boss:
                            bossArrageWorkAdapter.refresh(mlistEvening);
                        break;
                    default:
                }
            }
        });

        View view = View.inflate(getActivity(),R.layout.item_add_arragework,null);
        btn_add_arragework = view.findViewById(R.id.btn_add_arragework);
        btn_add_arragework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                if(rgBoosArragework.getCheckedRadioButtonId() == R.id.rb_moring_boss){
                    for(Schedule schedule : mlistMoring){
                        buffer.append(schedule.getU_phone()+",");
                    }
                }else if(rgBoosArragework.getCheckedRadioButtonId() == R.id.rb_after_boss){
                    for(Schedule schedule : mlistAfter){
                        buffer.append(schedule.getU_phone()+",");
                    }
                }else {
                    for(Schedule schedule : mlistEvening){
                        buffer.append(schedule.getU_phone()+",");
                    }

                }
                Intent intent = new Intent(getActivity(), PickContactActivity.class);
                intent.putExtra("flag","arragework");
                intent.putExtra("members",buffer.toString());
                startActivityForResult(intent,2);
            }
        });
        lvArragework.addFooterView(view);
        lvArragework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG","position"+position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("删除");
                builder.setMessage("确定移除此排班吗?");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Schedule schedule;
                        if(rgBoosArragework.getCheckedRadioButtonId() == R.id.rb_moring_boss){
                           schedule = mlistMoring.get(position);
                        }else if(rgBoosArragework.getCheckedRadioButtonId() == R.id.rb_after_boss){
                            schedule = mlistAfter.get(position);
                        }else {
                           schedule = mlistEvening.get(position);
                        }
                        requestServerDeleteArrageWork(schedule.getS_id(),position);
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
        lvArragework.setAdapter(bossArrageWorkAdapter);

    }
    //去服务器删除排班信息
    private void requestServerDeleteArrageWork(String s_id,int position) {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.addRequestParam("id",s_id);
        HttpUtils.sendPost(ConstantValues.URL_SCHEDULE+"deleteSchedule",commonRequest.getJsonStr(), new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork ERROR"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                if(resCode.equals("0")){
                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(rgBoosArragework.getCheckedRadioButtonId() == R.id.rb_moring_boss){
                                mlistMoring.remove(position);
                                bossArrageWorkAdapter.refresh(mlistMoring);
                            }else if(rgBoosArragework.getCheckedRadioButtonId() == R.id.rb_after_boss){
                                mlistAfter.remove(position);
                                bossArrageWorkAdapter.refresh(mlistAfter);
                            }else {
                                mlistEvening.remove(position);
                                bossArrageWorkAdapter.refresh(mlistEvening);
                            }
                        }
                    });
                }
                Uiutils.toast(resMsg);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(1 == resultCode) {
            //获得选择的成员信息
            StringBuffer buffer = new StringBuffer();
            String[] members = data.getStringArrayExtra("members");
            String flag = "1";
            if (rgBoosArragework.getCheckedRadioButtonId() == R.id.rb_moring_boss) {
                for (String member : members) {
                    if (!mlistMoring.contains(member)) {
                        buffer.append(member + ",");
                    }
                    flag = "1";
                }
            } else if (rgBoosArragework.getCheckedRadioButtonId() == R.id.rb_after_boss) {
                for (String member : members) {
                    if (!mlistAfter.contains(member)) {
                        buffer.append(member + ",");
                    }
                    flag = "2";
                }
            } else {
                for (String member : members) {
                    if (!mlistEvening.contains(member)) {
                        buffer.append(member + ",");
                    }
                }
                flag = "3";
            }

            if (!TextUtils.isEmpty(buffer.toString())) {
                addToServerVacate(buffer.toString(), flag);
            }
        }

    }

    @Override
    protected void initData() {
        requestServerVacate();
    }


//到服务器去请求排班数据
    private void requestServerVacate() {

        rbMoringBoss.setChecked(true);
        CommonRequest request = new CommonRequest();
        request.addRequestParam("s_time",s_time);
        HttpUtils.sendPost(ConstantValues.URL_SCHEDULE+"querySchedulesByTime",request.getJsonStr(), new okhttp3.Callback() {
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
                    mlistMoring.clear();
                    mlistAfter.clear();
                    mlistEvening.clear();
                    List<HashMap<String, String>> list = res.getMapList();
                    for(int i=0;i < list.size();i++){
                        Schedule schedule = new Schedule();
                        schedule.setS_id(list.get(i).get("s_id"));
                        schedule.setU_phone(list.get(i).get("u_phone"));
                        schedule.setU_phone(list.get(i).get("u_name"));
                        schedule.setS_time(list.get(i).get("s_time"));
                        schedule.setS_shift(list.get(i).get("s_shift"));
                        schedule.setS_term(list.get(i).get("s_term"));
                        if("1".equals(schedule.getS_shift())){
                            mlistMoring.add(schedule);
                        }else if("2".equals(schedule.getS_shift())){
                            mlistAfter.add(schedule);
                        }else{
                            mlistEvening.add(schedule);
                        }
                    }

                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bossArrageWorkAdapter.refresh(mlistMoring);
                        }
                    });
                }
            }
        });
    }

    public void addToServerVacate(String vacate,String flag){
        CommonRequest request = new CommonRequest();
        String[] members = vacate.split(",");
        for(String member : members){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("u_phone",member);
                jsonObject.put("s_time",s_time);
                jsonObject.put("s_shift",flag);
                String s_term;
                if("1".equals(flag)){
                    s_term = SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_MORING,"9:00 - 12:00");
                }else if("2".equals(flag)){
                    s_term =SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_AFTERNOON,"12:00 - 17:00");
                }else{
                    s_term = SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_EVENING,"17:00 - 22:00");
                }
                jsonObject.put("s_term",s_term);

                request.addRequestParam(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        HttpUtils.sendPost(ConstantValues.URL_SCHEDULE+"addSchedule",request.getJsonStr(), new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork ERROR:"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();

                if(resCode.equals("0")){

                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            requestServerVacate();
                        }
                    });

                }
                Uiutils.toast(resMsg);
            }
        });
    }

}
