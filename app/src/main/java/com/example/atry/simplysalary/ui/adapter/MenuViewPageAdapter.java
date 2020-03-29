package com.example.atry.simplysalary.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.atry.simplysalary.ui.fragment.BaseFragment;

import java.util.List;

public class MenuViewPageAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;
    public MenuViewPageAdapter(FragmentManager fm, List<BaseFragment> baseFragmentList) {
        super(fm);
        fragments = baseFragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
