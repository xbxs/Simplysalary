package com.example.atry.simplysalary.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.ui.activity.AddContactActivity;
import com.example.atry.simplysalary.ui.activity.ChatActivity;
import com.example.atry.simplysalary.ui.activity.InviteActivity;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.PrefUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 李维: TZZ on 2019-12-30 15:10
 * 邮箱: 3182430026@qq.com
 */
public class ContactsFragment extends EaseContactListFragment {


    private View mView;
    private ImageView mIv_contact_red;
    private LocalBroadcastManager mManager;
    private LinearLayout mMll_friendapply;
    private BroadcastReceiver ContactInviteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mIv_contact_red.setVisibility(View.VISIBLE);
            PrefUtils.setBoolean(Uiutils.getContext(),ConstantValues.IS_NEW_INVITE,true);
        }
    };
    private BroadcastReceiver ContactChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                //刷新页面
                refreshContact();
        }
    };

    private String mhxid;

    @Override
    protected void initView() {
        super.initView();
        //添加好友
        titleBar.setRightImageResource(R.drawable.add);
        mView = View.inflate(getActivity(),R.layout.header_fragment_contact,null);
        //获取小红点
        mMll_friendapply = mView.findViewById(R.id.ll_friendapply);
        mMll_friendapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //红点处理
                mIv_contact_red.setVisibility(View.GONE);
                PrefUtils.setBoolean(Uiutils.getContext(),ConstantValues.IS_NEW_INVITE,false);
                //跳转页面
                startActivity(new Intent(getActivity(), InviteActivity.class));

            }
        });
        mIv_contact_red = mView.findViewById(R.id.iv_contact_red);
        listView.addHeaderView(mView);
        //设置联系人列表的点击事件
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,user.getUsername());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });

        //初始化小红点显示
        boolean isNewInvite = PrefUtils.getBoolean(Uiutils.getContext(), ConstantValues.IS_NEW_INVITE,false);
        mIv_contact_red.setVisibility(isNewInvite ? View.VISIBLE : View.GONE);

        //注册广播
        mManager = LocalBroadcastManager.getInstance(Uiutils.getContext());
        mManager.registerReceiver(ContactInviteChangeReceiver, new IntentFilter(ConstantValues.CONTACT_INVITE_CHANGED));
        mManager.registerReceiver(ContactChangeReceiver, new IntentFilter(ConstantValues.CONTACT_CHANGED));

        mManager.registerReceiver(ContactInviteChangeReceiver,new IntentFilter(ConstantValues.SECTION_CHANGED));

        //从环信服务器获取联系人
        getContactFromHXServer();

        //绑定listview和contextmenu
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //添加布局
        getActivity().getMenuInflater().inflate(R.menu.delete,menu);

        int position = ((AdapterView.AdapterContextMenuInfo)menuInfo).position;
        EaseUser easeUser = (EaseUser) listView.getItemAtPosition(position);

        mhxid = easeUser.getUsername();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.contact_delete){
            //删除联系人
            deleteContact();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteContact() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.去环信服务器删除
                    EMClient.getInstance().contactManager().deleteContact(mhxid);

                    //2.本地数据库删除
                    Model.getInstance().getDbManager().getContactTableDao().deleteContactByHxId(mhxid);

                    //3.提示和刷新页面
                    if(getActivity() == null){
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //3.1提示
                            Uiutils.toast("删除成功");

                            //3.2刷新页面
                            refreshContact();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getContactFromHXServer() {
        //1.开启网络连接
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> hxids = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    //校验
                    if(hxids != null && hxids.size() >= 0){
                        List<User> userList = new ArrayList<>();
                        for(String hxid : hxids){
                            User user = new User();
                            user.setPhonenumber(hxid);
                            userList.add(user);
                        }

                        //保存好友信息到本地数据库
                        Model.getInstance().getDbManager().getContactTableDao().saveContacts(userList,true);


                        //刷新页面
                        if(getActivity() == null){
                            return;
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshContact();
                            }
                        });
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //刷新联系人页面
    private void refreshContact() {
        //获取数据
        List<User> userList = Model.getInstance().getDbManager().getContactTableDao().getContacts();

        //校验
        if(userList != null && userList.size() >= 0){
            //设置数据
            Map<String, EaseUser> contactMap = new HashMap<>();
            //转换
            for(User user : userList){

                EaseUser user1 = new EaseUser(user.getPhonenumber());
                contactMap.put(user.getPhonenumber(),user1);
            }

            setContactsMap(contactMap);
            //刷新页面
            refresh();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mManager.unregisterReceiver(ContactInviteChangeReceiver);
        mManager.unregisterReceiver(ContactChangeReceiver);
    }
}
