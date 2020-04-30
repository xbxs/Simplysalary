package com.example.atry.simplysalary.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.ui.activity.SectionDetailActivity;
import com.example.atry.simplysalary.ui.adapter.BoosStaticsExpandAdapter;
import com.example.atry.simplysalary.ui.adapter.SectionAdapter;
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
 * A simple {@link Fragment} subclass.
 */
public class BossStaticsSalaryFragment extends BaseFragment {


    @BindView(R.id.lv_section)
    ListView lv_section;
    Unbinder unbinder;
    private SectionAdapter sectionAdapter;
    private List<EMGroup> emGroups = new ArrayList<EMGroup>();
    @Override
    protected int setContentView() {
        return R.layout.fragment_boss_statics_salary;
    }

    @Override
    protected void initView() {
       sectionAdapter = new SectionAdapter(getActivity());
       lv_section.setAdapter(sectionAdapter);
       lv_section.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getActivity(), SectionDetailActivity.class);
               Bundle bundle = new Bundle();
               intent.putExtra(ConstantValues.SECTION_ID,emGroups.get(position).getGroupId());
               intent.putExtra(ConstantValues.SECTION_NAME,emGroups.get(position).getGroupName());
               startActivity(intent);
           }
       });
       lv_section.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setTitle("删除");
               EMGroup emGroup = emGroups.get(position);
               if(EMClient.getInstance().getCurrentUser().equals(emGroup.getOwner())){
                builder.setMessage("你确认要解散这个部吗?");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                            @Override
                            public void run() {
                                //去环信服务器解散群
                                try {
                                    EMClient.getInstance().groupManager().destroyGroup(emGroup.getGroupId());
                                    //发生群解散的广播
                                    exitGroupBroatCast();

                                    Uiutils.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Uiutils.toast("解散部成功");
                                            refresh(EMClient.getInstance().groupManager().getAllGroups());
                                        }
                                    });
                                } catch (HyphenateException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
               }else{
                   builder.setMessage("你确认要退出这个部吗?");
                   builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                               @Override
                               public void run() {
                                   try {
                                       EMClient.getInstance().groupManager().leaveGroup(emGroup.getGroupId());
                                       //发送广播
                                       exitGroupBroatCast();
                                       //刷新页面
                                       Uiutils.runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               Uiutils.toast("退出部成功");
                                               refresh(EMClient.getInstance().groupManager().getAllGroups());
                                           }
                                       });
                                   } catch (HyphenateException e) {
                                       e.printStackTrace();
                                   }
                               }
                           });
                       }
                   });
               }
               builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });
               builder.create().show();
               return true;
           }
       });
    }

    private void exitGroupBroatCast() {

    }

    @Override
    protected void initData() {

    }

    public void refresh(List<EMGroup> emGroupList){
            emGroups.clear();
            emGroups.addAll(emGroupList);
            sectionAdapter.refresh(emGroupList);
    }


}
