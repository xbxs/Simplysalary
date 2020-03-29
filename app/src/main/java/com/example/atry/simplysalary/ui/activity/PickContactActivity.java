package com.example.atry.simplysalary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.model.bean.PickUserInfo;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.ui.adapter.PickContactAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickContactActivity extends BaseActivity {


    @BindView(R.id.tv_pick_savecontact)
    TextView tvPickSavecontact;
    @BindView(R.id.lv_pick)
    ListView lvPick;
    private List<PickUserInfo> pickUserInfos;
    private PickContactAdapter pickContactAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_pick_contact;
    }

    @Override
    protected void initView() {

    }
    //设置条目点击的监听
    private void initListener() {
        lvPick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb_pick = view.findViewById(R.id.cb_pick);
                cb_pick.setChecked(!cb_pick.isChecked());

                PickUserInfo pickUserInfo = pickUserInfos.get(position);
                pickUserInfo.setChecked(cb_pick.isChecked());

                pickContactAdapter.notifyDataSetChanged();
            }
        });

        tvPickSavecontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到已经选择的联系人
                List<String> names = pickContactAdapter.getPickContacts();
                //给启动页面返回数据
                Intent intent = new Intent();
                intent.putExtra("members",names.toArray(new String[0]));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
            //获取联系人的信息
        List<User> contacts = Model.getInstance().getDbManager().getContactTableDao().getContacts();
            //转换为PickUserInfo
        pickUserInfos = new ArrayList<>();
        for(User user : contacts){
                pickUserInfos.add(new PickUserInfo(user,false));
         }
            //初始化listview的适配器
        pickContactAdapter = new PickContactAdapter(PickContactActivity.this, pickUserInfos);
            lvPick.setAdapter(pickContactAdapter);

        initListener();
    }

    @Override
    public void onClick(View v) {

    }

}
