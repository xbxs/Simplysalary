package com.example.atry.simplysalary.ui.fragment.staff;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.ui.adapter.MenuViewPageAdapter;
import com.example.atry.simplysalary.ui.fragment.BaseFragment;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.SPUtils;
import com.example.atry.simplysalary.utils.Uiutils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 李维: TZZ on 2019-12-30 15:14
 * 邮箱: 3182430026@qq.com
 */
public class StaffStatisticsFragment extends BaseFragment {


    @BindView(R.id.staff_rb_salary)
    RadioButton staffRbSalary;
    @BindView(R.id.staff_rb_vacate)
    RadioButton staffRbVacate;
    @BindView(R.id.rg_statistics)
    RadioGroup rgStatistics;
    @BindView(R.id.vp_statistics_menu)
    ViewPager vpStatisticsMenu;

    //存储fragment集合
    List<BaseFragment> mFragments;
    @BindView(R.id.btn_staffstatics_left)
    Button btnStaffstaticsLeft;
    @BindView(R.id.tv_staffstatics_left)
    TextView tvStaffstaticsLeft;
    @BindView(R.id.tv_staffstatics_right)
    TextView tvStaffstaticsRight;
    @BindView(R.id.btn_staffstatics_right)
    Button btnStaffstaticsRight;
    Unbinder unbinder;
    private MenuViewPageAdapter mStatisticsAdapter;
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private StaffStatisticsSalaryFragment salaryFragment;
    private StaffStatisticsVacateFragment vacateFragment;

    @Override
    protected int setContentView() {
        return R.layout.fragment_staffstatics;
    }

    @Override
    protected void initView() {
        staffRbSalary.setChecked(true);
        //点击时切换页面
        rgStatistics.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.staff_rb_salary:
                        vpStatisticsMenu.setCurrentItem(0);
                        break;
                    case R.id.staff_rb_vacate:
                        vpStatisticsMenu.setCurrentItem(1);
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
        tvStaffstaticsLeft.setText(leftdate);
        String rightdate = SPUtils.getInstance().getString(ConstantValues.STATICS_RIGHT_DATE,"2020-05-06 22:15");
        if("2020-05-06 22:15".equals(rightdate)){
            SPUtils.getInstance().save(ConstantValues.STATICS_RIGHT_DATE,"2020-05-06 22:15");
        }
        rightdate = rightdate.substring(0,10);
        tvStaffstaticsRight.setText(rightdate);

        btnStaffstaticsLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity(),4,tvStaffstaticsLeft,calendar);
            }
        });
        btnStaffstaticsRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity(),4,tvStaffstaticsRight,calendar);
            }
        });


    }

    @Override
    protected void initData() {
        //初始化数据，将页面加载进viewpager
        mFragments = new ArrayList<>();
        salaryFragment = new StaffStatisticsSalaryFragment();
        vacateFragment = new StaffStatisticsVacateFragment();
        mFragments.add(salaryFragment);
        mFragments.add(vacateFragment);

        mStatisticsAdapter = new MenuViewPageAdapter(getFragmentManager(), mFragments);
        vpStatisticsMenu.setAdapter(mStatisticsAdapter);
        vpStatisticsMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动的时候调用
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            //页面滑动完成后调用
            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    staffRbSalary.setChecked(true);
                    staffRbVacate.setChecked(false);
                } else if (i == 1) {
                    staffRbSalary.setChecked(false);
                    staffRbVacate.setChecked(true);
                }
            }

            //当状态发生改变时调用
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    //显示日期选择
    public void showDatePickerDialog(Context context, int themeResId, final TextView tv, Calendar calendar) {

        new DatePickerDialog(context, themeResId, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String strmonth, strdayOfMonth;
                if (month + 1 > 0 && month + 1 < 10) {
                    strmonth = "0" + (month + 1);
                } else {
                    strmonth = (month + 1) + "";
                }

                if (dayOfMonth > 0 && dayOfMonth < 10) {
                    strdayOfMonth = "0" + dayOfMonth;
                } else {
                    strdayOfMonth = dayOfMonth + "";
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                Date date = new Date(System.currentTimeMillis());
                String time = simpleDateFormat.format(date);
                if (tv.getId() == R.id.tv_staffstatics_right) {
                    String date1 = year + "-" + strmonth + "-" + strdayOfMonth;
                    if (date1.compareTo(tvStaffstaticsLeft.getText().toString()) < 0) {
                        Uiutils.toast("后面的日期不能小于前面的");
                        return;
                    } else {
                        tv.setText(year + "-" + strmonth + "-" + strdayOfMonth);
                        SPUtils.getInstance().save(ConstantValues.STATICS_RIGHT_DATE, year + "-" + strmonth + "-" + strdayOfMonth+" "+time);
                        salaryFragment.requestServerStaffStatics();
                        vacateFragment.initData();
                    }
                } else {
                    tv.setText(year + "-" + strmonth + "-" + strdayOfMonth);
                    SPUtils.getInstance().save(ConstantValues.STATICS_LEFT_DATE,year + "-" + strmonth + "-" + strdayOfMonth+" "+time);
                    salaryFragment.requestServerStaffStatics();
                    vacateFragment.initData();
                }
            }
        }, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
