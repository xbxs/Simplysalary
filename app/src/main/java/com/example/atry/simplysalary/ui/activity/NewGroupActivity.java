package com.example.atry.simplysalary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class NewGroupActivity extends BaseActivity {


    @BindView(R.id.et_input_sectionname)
    EditText etInputSectionname;
    @BindView(R.id.et_input_sectiondes)
    EditText etInputSectiondes;
    @BindView(R.id.btn_input_sectioncrt)
    Button btnInputSectioncrt;
    private LocalBroadcastManager mlocalBroadcastManager;

    @Override
    protected int setContentView() {
        return R.layout.activity_new_group;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        btnInputSectioncrt.setOnClickListener(this);
        mlocalBroadcastManager = LocalBroadcastManager.getInstance(NewGroupActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_input_sectioncrt:
                if(TextUtils.isEmpty(etInputSectionname.getText().toString()) || TextUtils.isEmpty(etInputSectiondes.getText().toString())){
                    Uiutils.toast("信息不能为空!");
                }else{
                   Intent intent = new Intent(NewGroupActivity.this,PickContactActivity.class);
                   startActivityForResult(intent,1);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            createGroup(data.getStringArrayExtra("members"));
        }
    }

    private void createGroup(String[] members) {
        final String groupname = etInputSectionname.getText().toString();
        final String groupdes  = etInputSectiondes.getText().toString();
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //参数1：群名称，参数2：群描述，参数3：群成员，参数4：原因，参数5：参数设置
                EMGroupOptions options = new EMGroupOptions();
                options.maxUsers = 200;
                EMGroupManager.EMGroupStyle emGroupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;
                options.style = emGroupStyle;

                try {
                    EMGroup emGroup = EMClient.getInstance().groupManager().createGroup(groupname,groupdes,members,"申请加入群",options);
                    toServerUpdateUserSection(emGroup.getGroupId(),emGroup.getOwner(),members);
                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Uiutils.toast("创建部门成功");
                            mlocalBroadcastManager.sendBroadcast(new Intent(ConstantValues.SECTION_CREATE));
                            finish();
                        }

                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Uiutils.toast("创建部门失败");
                        }
                    });
                }

            }
        });
    }
    //去服务器将用户的群组信息更新
    private void toServerUpdateUserSection(String groupId, String ower,String[] members) {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setRequestCode("newgroup");
        commonRequest.addRequestParam("groupId",groupId);
        commonRequest.addRequestParam("ower",ower);
        for(String uphone : members){
            commonRequest.addRequestParam(uphone);
        }
        HttpUtils.sendPost(ConstantValues.URL_USER+"updateUser",commonRequest.getJsonStr(), new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork ERROR"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                if(resCode.equals("0")){

                }
            }
        });
    }
}
