package com.example.atry.simplysalary.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.ui.adapter.MenuViewPageAdapter;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mlocalBroadcastManager.unregisterReceiver(mSectionCreatReceiver);
    }
}
