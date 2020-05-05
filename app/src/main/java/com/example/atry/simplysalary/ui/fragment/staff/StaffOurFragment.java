package com.example.atry.simplysalary.ui.fragment.staff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.ui.activity.ContactsActivity;
import com.example.atry.simplysalary.ui.activity.CreateVacateActivity;
import com.example.atry.simplysalary.ui.activity.LoginActivity;
import com.example.atry.simplysalary.ui.activity.MessageActivity;
import com.example.atry.simplysalary.ui.activity.VacateActivity;
import com.example.atry.simplysalary.ui.fragment.BaseFragment;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StaffOurFragment extends BaseFragment {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_selfinformation)
    RelativeLayout rlSelfinformation;
    @BindView(R.id.rl_message)
    RelativeLayout rlMessage;
    @BindView(R.id.rl_contacts)
    RelativeLayout rlContacts;
    @BindView(R.id.rl_create_vacate)
    RelativeLayout rlCreateVacate;
    @BindView(R.id.rl_privacystate)
    RelativeLayout rlPrivacystate;
    @BindView(R.id.rl_aboutus)
    RelativeLayout rlAboutus;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    Unbinder unbinder;

    @Override
    protected int setContentView() {
        return R.layout.fragment_staffour;
    }

    @Override
    protected void initView() {
        tvName.setText(EMClient.getInstance().getCurrentUser());
        rlMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
        rlContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactsActivity.class);
                startActivity(intent);
            }
        });

        rlCreateVacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VacateActivity.class);
                startActivity(intent);
            }
        });
    }
    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确定要退出应用？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                //关闭数据库
                                Model.getInstance().getDbManager().close();
                                //切换到主线程
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //提示成功
                                        Uiutils.toast("退出成功");
                                        //回到登录页面
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });

                            }

                            @Override
                            public void onError(int i, String s) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Uiutils.toast("退出失败:" + s);
                                    }
                                });
                            }

                            @Override
                            public void onProgress(int i, String s) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Uiutils.toast("退出进行中:" + s);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();

    }
    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
