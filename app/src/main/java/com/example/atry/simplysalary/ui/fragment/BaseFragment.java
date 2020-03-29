package com.example.atry.simplysalary.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atry.simplysalary.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 *    创建Fragment的基类,这里主要写了ButterKnife的绑定
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;
    // TODO: Rename and change types of parameters

    private View mView;

    public BaseFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = rootContentView(inflater);
        mUnbinder = ButterKnife.bind(this, mView);
        initView();
        initData();
        return mView;
    }





    /**
     * 在销毁时解绑
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 加载布局
     */
    protected abstract int setContentView();
    //初始化布局
    protected abstract void initView();
    /**
     * 初始化数据
     */
    protected abstract void initData();

    //点击事件

    /**
     * 获取根View  这里通过传入LayoutInflater来加载布局
     */
    public View rootContentView(LayoutInflater inflater){
        if(setContentView() != 0){
            return inflater.inflate(setContentView(),null);

        }else {
            return inflater.inflate(R.layout.fragment_base,null);
        }
    }
}
