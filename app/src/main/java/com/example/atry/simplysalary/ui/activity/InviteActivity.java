package com.example.atry.simplysalary.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.model.bean.InvationInfo;
import com.example.atry.simplysalary.ui.adapter.InviteAdapter;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.BindView;

public class InviteActivity extends BaseActivity {

    @BindView(R.id.iv_invite_list)
    ListView ivInviteList;
    private InviteAdapter mAdapter;
    private InviteAdapter.OnInviteListener mOnInviteListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void OnAccept(InvationInfo invationInfo) {
            //1.告知环信服务器接收了邀请
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String hxid = invationInfo.getUser().getPhonenumber();
                        EMClient.getInstance().contactManager().acceptInvitation(hxid);
                        //2.更新邀请数据库中的信息
                        Model.getInstance().getDbManager().getInviteTableDao().updateInvitationStatus(InvationInfo.InvitationStatus.INVITE_ACCEPT,hxid);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //3.提示
                                Uiutils.toast("接收了邀请");

                                //刷新页面
                                refresh();

                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Uiutils.toast("接收邀请失败");
                            }
                        });
                    }

                }
            });
        }

        @Override
        public void OnReject(InvationInfo invationInfo) {
            //1.告知环信服务器拒绝了邀请
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    String hxid = invationInfo.getUser().getPhonenumber();
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(hxid);
                        //在邀请表中删除邀请的记录
                        Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(hxid);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //提示删除
                                Uiutils.toast("拒绝了邀请");

                                //刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Uiutils.toast("拒绝失败，请重试！");
                            }
                        });
                    }


                }

            });
            }

        @Override
        public void onIviteAccept(InvationInfo invationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    //告诉环信服务器接受了邀请
                    try {
                        EMClient.getInstance().groupManager().acceptInvitation(invationInfo.getGroupInfo().getGroupId(),invationInfo.getUser().getPhonenumber());
                        //本地数据库发生变化
                        invationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_ACCERP_INVITE);
                        Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationInfo);
                        //页面刷新
                        Uiutils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Uiutils.toast("接受失败！");
                            }
                        });
                    }

                }
            });
        }

        @Override
        public void onIviteReject(InvationInfo invationInfo) {
                    Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().groupManager().declineInvitation(invationInfo.getGroupInfo().getGroupId(),invationInfo.getUser().getPhonenumber(),"拒绝邀请");

                                invationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_ACCERP_DECLINED);
                                Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        refresh();
                                    }
                                });
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Uiutils.toast("拒绝失败，请重试！");
                                        refresh();
                                    }
                                });
                            }

                        }
                    });
        }

        @Override
        public void onApplicationAccept(InvationInfo invationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    //告诉服务器接收了申请
                    try {
                        EMClient.getInstance().groupManager().acceptApplication(invationInfo.getGroupInfo().getGroupId(),invationInfo.getUser().getPhonenumber());
                        invationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED);
                        Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationInfo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    }

                }
            });
        }

        @Override
        public void onApplicationReject(InvationInfo invationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().groupManager().declineApplication(invationInfo.getGroupInfo().getGroupId(),invationInfo.getUser().getPhonenumber(),"拒绝申请");
                        invationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED);
                        Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationInfo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Uiutils.toast("拒绝申请失败");
                                refresh();
                            }
                        });
                    }

                }
            });
        }
    };
    private List<InvationInfo> mInvitations;
    private LocalBroadcastManager mLBM;
    private BroadcastReceiver InviteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如何在当前信息页面信息变化，则刷新页面
            refresh();
        }
    };

    @Override
    protected int setContentView() {
        return R.layout.activity_invite;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mAdapter = new InviteAdapter(this,mOnInviteListener);
        mInvitations = Model.getInstance().getDbManager().getInviteTableDao().getInvitations();
        Log.i("TAG","invitations size:"+mInvitations.size());
        ivInviteList.setAdapter(mAdapter);

        mAdapter.refresh(mInvitations);

        //注册邀请信息变化的广播
        mLBM = LocalBroadcastManager.getInstance(this);
        mLBM.registerReceiver(InviteChangeReceiver,new IntentFilter(ConstantValues.CONTACT_INVITE_CHANGED));
        mLBM.registerReceiver(InviteChangeReceiver,new IntentFilter(ConstantValues.SECTION_CHANGED));
    }

    @Override
    public void onClick(View view) {

    }


    private void refresh(){

        //获取数据库中所有的邀请信息
        List<InvationInfo> invationInfos = Model.getInstance().getDbManager().getInviteTableDao().getInvitations();

        //刷新适配器
        mAdapter.refresh(invationInfos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLBM.unregisterReceiver(InviteChangeReceiver);
    }
}
