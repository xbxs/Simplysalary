package com.example.atry.simplysalary.ui.fragment.staff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.Schedule;
import com.example.atry.simplysalary.ui.fragment.BaseFragment;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.adapter.EMABase;

import org.jetbrains.annotations.NotNull;

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
    private String s_time;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date(System.currentTimeMillis());
    private List<Schedule> scheduleList = new ArrayList<>();


    @Override
    protected int setContentView() {
        return R.layout.fragment_staffarragework;
    }

    @Override
    protected void initView() {
        s_time = simpleDateFormat.format(date);
        cdvArrageworkStaff.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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
                requestServerVacate();
            }
        });
    }

    @Override
    protected void initData() {
        requestServerVacate();
    }


    private void requestServerVacate() {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("s_time",s_time);
        request.addRequestParam("u_phone", EMClient.getInstance().getCurrentUser());
        HttpUtils.sendPost(ConstantValues.URL_SCHEDULE+"queryScheduleByUphoneAndTime",request.getJsonStr(), new okhttp3.Callback() {
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
                    scheduleList.clear();
                    List<HashMap<String, String>> list = res.getMapList();
                    for(int i=0;i < list.size();i++){
                        Schedule schedule = new Schedule();
                        schedule.setS_id(list.get(i).get("s_id"));
                        schedule.setU_phone(list.get(i).get("u_phone"));
                        schedule.setS_time(list.get(i).get("s_time"));
                        schedule.setS_shift(list.get(i).get("s_shift"));
                        schedule.setS_term(list.get(i).get("s_term"));
                        scheduleList.add(schedule);
                    }

                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contentLayout.removeAllViews();
                            for (Schedule schedule : scheduleList){
                                View view = View.inflate(getActivity(),R.layout.item_arragework_staff,null);
                                String shift="早班";
                                if("2".equals(schedule.getS_shift())){
                                    shift = "中班";
                                }else if("3".equals(schedule.getS_shift())){
                                    shift = "晚班";
                                }
                                ((TextView)view.findViewById(R.id.tv_shift_staff)).setText(shift);
                                ((TextView)view.findViewById(R.id.tv_term_staff)).setText(schedule.getS_term());
                                contentLayout.addView(view);
                            }
                        }
                    });
                }
            }
        });
    }

}
