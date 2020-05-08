package com.example.atry.simplysalary.ui.fragment.boss;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.ui.adapter.MenuViewPageAdapter;
import com.example.atry.simplysalary.ui.fragment.BaseFragment;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.SPUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 李维: TZZ on 2019-12-30 15:14
 * 邮箱: 3182430026@qq.com
 */
public class BossStatisticsFragment extends BaseFragment {
    @BindView(R.id.btn_boosstatics_left)
    Button btnBoosstaticsLeft;
    @BindView(R.id.tv_bossstatics_left)
    TextView tvBossstaticsLeft;
    @BindView(R.id.tv_bossstatics_right)
    TextView tvBossstaticsRight;
    @BindView(R.id.btn_boosstatics_right)
    Button btnBoosstaticsRight;
    @BindView(R.id.boos_rb_salary)
    RadioButton boosRbSalary;
    @BindView(R.id.boos_rb_vacate)
    RadioButton boosRbVacate;
    @BindView(R.id.rg_boos_statistics)
    RadioGroup rgBoosStatistics;
    @BindView(R.id.vp_bossstatistics_menu)
    ViewPager vpBossstatisticsMenu;
    Unbinder unbinder;
    //存储fragment集合
    List<BaseFragment> mFragments;
    private MenuViewPageAdapter mBossStatisticsAdapter;
    private BossStaticsSalaryFragment bossStaticsSalaryFragment;
    private BossStaticsVacateFragment bossStaticsVacateFragment;

    private LocalBroadcastManager mlocalBroadcastManager;
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    BroadcastReceiver mSectionCreatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    };

    @Override
    protected int setContentView() {
        return R.layout.fragment_bossstatics;
    }

    @Override
    protected void initView() {
            boosRbSalary.setChecked(true);
            rgBoosStatistics.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.boos_rb_salary:
                            vpBossstatisticsMenu.setCurrentItem(0);
                            break;
                        case R.id.boos_rb_vacate:
                            vpBossstatisticsMenu.setCurrentItem(1);
                            break;
                        default:
                            break;
                    }
                }
            });
            String leftdate = SPUtils.getInstance().getString(ConstantValues.STATICS_LEFT_DATE,"2020-05-03 22:15");
            if("2020-05-03 22:15".equals(leftdate)){
                SPUtils.getInstance().save(ConstantValues.STATICS_LEFT_DATE,"2020-05-03 22:15");
            }
            leftdate = leftdate.substring(0,10);
            tvBossstaticsLeft.setText(leftdate);
            String rightdate = SPUtils.getInstance().getString(ConstantValues.STATICS_RIGHT_DATE,"2020-05-06 22:15");
            if("2020-05-06 22:15".equals(rightdate)){
                SPUtils.getInstance().save(ConstantValues.STATICS_RIGHT_DATE,"2020-05-06 22:15");
            }
            rightdate = rightdate.substring(0,10);
            tvBossstaticsRight.setText(rightdate);

            btnBoosstaticsLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(getActivity(),4,tvBossstaticsLeft,calendar);
                }
            });

            btnBoosstaticsRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(getActivity(),4,tvBossstaticsRight,calendar);
                }
            });
    }

    @Override
    protected void initData() {
        //集合存储两个跳转页面
        mFragments = new ArrayList<>();
        bossStaticsSalaryFragment = new BossStaticsSalaryFragment();
        bossStaticsVacateFragment = new BossStaticsVacateFragment();
        mFragments.add(bossStaticsSalaryFragment);
        mFragments.add(bossStaticsVacateFragment);
//        为滑动的viewpager设置适配器
        mBossStatisticsAdapter = new MenuViewPageAdapter(getFragmentManager(),mFragments);
        vpBossstatisticsMenu.setAdapter(mBossStatisticsAdapter);
        vpBossstatisticsMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                    if(i == 0){
                        boosRbSalary.setChecked(true);
                        boosRbVacate.setChecked(false);
                    }else if(1 ==  i){
                        boosRbSalary.setChecked(false);
                        boosRbVacate.setChecked(true);
                    }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mlocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());

        mlocalBroadcastManager.registerReceiver(mSectionCreatReceiver, new IntentFilter(ConstantValues.SECTION_CREATE));
        //从环信服务器获取所有群组信息
        getGroupsFromServer();

    }

    private void getGroupsFromServer() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<EMGroup> emGroups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();

                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Uiutils.toast("加载成功");
                            //刷新
                            refresh();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Uiutils.toast("加载失败");
                        }
                    });
                }
            }
        });
    }

    private void refresh() {
        Log.i("TAG","refresh");
        bossStaticsSalaryFragment.refresh(EMClient.getInstance().groupManager().getAllGroups());
        bossStaticsVacateFragment.refresh(EMClient.getInstance().groupManager().getAllGroups());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mlocalBroadcastManager.unregisterReceiver(mSectionCreatReceiver);
    }

    public  void showDatePickerDialog(Context context,int themeResId,final  TextView tv,Calendar calendar){

       new DatePickerDialog(context, themeResId, new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               String strmonth,strdayOfMonth;
               if(month+1 >0 && month+1 < 10) {
                   strmonth = "0" +( month + 1);
               }else{
                   strmonth = (month+1)+"";
               }

               if(dayOfMonth > 0 && dayOfMonth<10){
                   strdayOfMonth = "0"+dayOfMonth;
               }else{
                   strdayOfMonth = dayOfMonth+"";
               }
               SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
               Date date = new Date(System.currentTimeMillis());
               String time = simpleDateFormat.format(date);

               if(tv.getId() == R.id.tv_bossstatics_right){
                   String date1 = year+"-"+strmonth+"-"+strdayOfMonth;
                   if(date1.compareTo(tvBossstaticsLeft.getText().toString()) < 0) {
                       Uiutils.toast("后面的日期不能小于前面的");
                       return;
                   }else{
                       tv.setText(year+"-"+strmonth+"-"+strdayOfMonth);
                       SPUtils.getInstance().save(ConstantValues.STATICS_RIGHT_DATE, year + "-" + strmonth + "-" + strdayOfMonth+" "+time);

                   }
               }else {
                   tv.setText(year+"-"+strmonth+"-"+strdayOfMonth);
                   SPUtils.getInstance().save(ConstantValues.STATICS_LEFT_DATE,year+"-"+strmonth+"-"+strdayOfMonth+" "+time);
               }
           }
       },calendar.get(Calendar.YEAR)
        ,calendar.get(Calendar.MONTH)
        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
