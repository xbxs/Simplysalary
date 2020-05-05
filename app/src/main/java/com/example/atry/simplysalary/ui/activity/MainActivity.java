package com.example.atry.simplysalary.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.ui.fragment.boss.BossArrageWorkFragment;
import com.example.atry.simplysalary.ui.fragment.boss.BossStatisticsFragment;
import com.example.atry.simplysalary.ui.fragment.staff.StaffArrageWorkFragment;
import com.example.atry.simplysalary.ui.fragment.boss.BossOurFragment;
import com.example.atry.simplysalary.ui.fragment.staff.StaffOurFragment;
import com.example.atry.simplysalary.ui.fragment.staff.StaffStatisticsFragment;
import com.example.atry.simplysalary.ui.view.NoScrollViewPager;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    //排班的按钮
    @BindView(R.id.rb_arrage_work)
    RadioButton rbArrageWork;
    //统计按钮
    @BindView(R.id.rb_statistics)
    RadioButton rbStatistics;
    //我的按钮
    @BindView(R.id.rb_our)
    RadioButton rbOur;
    //单选框的父布局
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    //实现侧滑的viewpager
    @BindView(R.id.vp_menu)
    NoScrollViewPager vpMenu;
    //用于装fragment的集合
    private List<Fragment> mFragments;
    //fragment和viewpage的适配器
    private MenuViewPageAdater mMenuViewPageAdater;
    //记录上次也买所在的位置，默认是主页（排班页）

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //点击单选按钮改变页面
        rbArrageWork.setChecked(true);
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.i("TAG","00000:"+i);
                switch (i) {
//                    case R.id.rb_message:
//                        vpMenu.setCurrentItem(0);
//                        break;
//                    case R.id.rb_contact:
//                        vpMenu.setCurrentItem(1);
//                        break;
                    case R.id.rb_arrage_work:
                        vpMenu.setCurrentItem(0);
                        break;
                    case R.id.rb_statistics:
                        vpMenu.setCurrentItem(1);
                        break;
                    case R.id.rb_our:
                        vpMenu.setCurrentItem(2);
                        break;
                    default:
                }
            }
        });
    }

    @Override
    protected void initData() {
        //初始化总的布局
        int flag = SPUtils.getInstance().getInt( ConstantValues.LOGIN_IDENTITY,0);
        mFragments = new ArrayList<>();
        if(flag == 0) {
            mFragments.add(new StaffArrageWorkFragment());
            mFragments.add(new StaffStatisticsFragment());
            mFragments.add(new StaffOurFragment());
        }else if(flag == 1){
            mFragments.add(new BossArrageWorkFragment());
            mFragments.add(new BossStatisticsFragment());
            mFragments.add(new BossOurFragment());
        }
        mMenuViewPageAdater = new MenuViewPageAdater(getSupportFragmentManager());
        vpMenu.setAdapter(mMenuViewPageAdater);
    }

    @Override
    public void onClick(View view) {

    }


    /**
     * fragment的适配器
     *
     */

    class MenuViewPageAdater extends FragmentPagerAdapter{

        public MenuViewPageAdater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }


}
