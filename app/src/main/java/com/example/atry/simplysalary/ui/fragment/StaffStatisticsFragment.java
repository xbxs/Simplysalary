package com.example.atry.simplysalary.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.ui.adapter.MenuViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    private MenuViewPageAdapter mStatisticsAdapter;

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
                switch (i){
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

    }

    @Override
    protected void initData() {
        //初始化数据，将页面加载进viewpager
        mFragments = new ArrayList<>();
        mFragments.add(new StaffStatisticsSalaryFragment());
        mFragments.add(new StaffStatisticsVacateFragment());

        mStatisticsAdapter = new MenuViewPageAdapter(getFragmentManager(),mFragments);
        vpStatisticsMenu.setAdapter(mStatisticsAdapter);
        vpStatisticsMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动的时候调用
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            //页面滑动完成后调用
            @Override
            public void onPageSelected(int i) {
                if(i == 0){
                    staffRbSalary.setChecked(true);
                    staffRbVacate.setChecked(false);
                }else  if(i == 1 ){
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
}
