package com.example.atry.simplysalary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.model.bean.PickUserInfo;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.ui.adapter.PickContactAdapter;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.Uiutils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PickContactActivity extends BaseActivity {


    @BindView(R.id.tv_pick_savecontact)
    TextView tvPickSavecontact;
    @BindView(R.id.lv_pick)
    ListView lvPick;
    @BindView(R.id.iv_nothingdata)
    ImageView ivNothingdata;
    private List<PickUserInfo> pickUserInfos;
    private PickContactAdapter pickContactAdapter;
    private List<String> mExistMembers;
    String flag = "statics";
    private String[] members;

    @Override
    protected int setContentView() {
        return R.layout.activity_pick_contact;
    }

    @Override
    protected void initView() {
        flag = getIntent().getStringExtra("flag");
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
                if ("arragework".equals(flag)) {
                    intent.putExtra("members", names.toArray(new String[0]));
                    setResult(1, intent);
                } else {
                    intent.putExtra("members", names.toArray(new String[0]));
                    setResult(RESULT_OK, intent);
                }

                finish();
            }
        });
    }

    @Override
    protected void initData() {
        getIntentData();

    }

    //得到群成员
    private void getIntentData() {
        if (mExistMembers == null) {
            mExistMembers = new ArrayList<>();
        }
        pickUserInfos = new ArrayList<>();
        if ("arragework".equals(flag)) {
            String smember = getIntent().getStringExtra("members");
            members = smember.split(",");
            for (String memeber : members) {
                mExistMembers.add(memeber);
            }
        }

//            //转换为PickUserInfo
//            for (User user : contacts) {
//                pickUserInfos.add(new PickUserInfo(user, false));
//            }
//            //初始化listview的适配器
//            if (pickUserInfos.size() == 0) {
//                tvPickSavecontact.setVisibility(View.INVISIBLE);
//                ivNothingdata.setVisibility(View.VISIBLE);
//            }
//            pickContactAdapter = new PickContactAdapter(PickContactActivity.this, pickUserInfos, mExistMembers);
//            lvPick.setAdapter(pickContactAdapter);
//            initListener();
//
//        } else {
//            String groupId = getIntent().getStringExtra(ConstantValues.SECTION_ID);
//            if (groupId != null) {
//                EMGroup emGroup = EMClient.getInstance().groupManager().getGroup(groupId);
//                //获取群里面已经存在的成员
//                mExistMembers = emGroup.getMembers();
//            }

            requestServerUser();
//        }
    }

    @Override
    public void onClick(View v) {

    }

    public void requestServerUser() {
        CommonRequest request = new CommonRequest();
        String URL;
        if("arragework".equals(flag)){
            URL = ConstantValues.URL_USER+"queryBossContact";
            //获取联系人的信息
            List<User> contacts = Model.getInstance().getDbManager().getContactTableDao().getContacts();
            for(User user :contacts){
                request.addRequestParam(user.getPhonenumber());
            }
        }else{
            URL = ConstantValues.URL_USER + "queryUserSection";
        }
        HttpUtils.sendPost(URL, request.getJsonStr(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork ERROR" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                if (resCode.equals("0")) {
                    List<HashMap<String, String>> list = res.getMapList();
                    for (int i = 0; i < list.size(); i++) {
                        User user = new User();
                        user.setPhonenumber((list.get(i).get("u_phone")));
                        user.setName(list.get(i).get("u_name"));
                        pickUserInfos.add(new PickUserInfo(user, false));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pickContactAdapter = new PickContactAdapter(PickContactActivity.this, pickUserInfos, mExistMembers);
                            lvPick.setAdapter(pickContactAdapter);
                            initListener();
                            if (pickUserInfos.size() == 0) {
                                tvPickSavecontact.setVisibility(View.INVISIBLE);
                                ivNothingdata.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
