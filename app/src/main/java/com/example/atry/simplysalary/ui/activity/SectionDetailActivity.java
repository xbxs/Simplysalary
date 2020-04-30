package com.example.atry.simplysalary.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.Model;
import com.example.atry.simplysalary.model.bean.SectionUser;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.ui.adapter.SectionDetailAdapter;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class SectionDetailActivity extends BaseActivity {


    @BindView(R.id.tv_section_detailname)
    TextView tvSectionDetailname;
    @BindView(R.id.lv_section_detail)
    ListView lvSectionDetail;
    @BindView(R.id.iv_section_add)
    ImageView ivSectionAdd;
    private SectionDetailAdapter detailAdapter;
    private String mgroupId;
    private List<SectionUser> userList = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.activity_section_detail;
    }

    @Override
    protected void initView() {
        //指定部门编号查询
        mgroupId = getIntent().getStringExtra(ConstantValues.SECTION_ID);
        tvSectionDetailname.setText(getIntent().getStringExtra(ConstantValues.SECTION_NAME));
        detailAdapter = new SectionDetailAdapter(this);
        lvSectionDetail.setAdapter(detailAdapter);
        //删除部门里面的成员
        lvSectionDetail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SectionDetailActivity.this);
                builder.setTitle("删除");
                builder.setMessage("你确认要删除" + userList.get(position).getName() + "吗?");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMember(userList.get(position));
                    }
                });
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
        //进入员工详情
       lvSectionDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(SectionDetailActivity.this,Boss_StaffStatiticsDetailActivity.class);
               intent.putExtra("phonenumber",userList.get(position).getPhonenumber());
               intent.putExtra("section_id",mgroupId);
               startActivity(intent);
           }
       });

        ivSectionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SectionDetailActivity.this,PickContactActivity.class);
                intent.putExtra(ConstantValues.SECTION_ID,mgroupId);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            //获得选择的群成员信息
            String[] members = data.getStringArrayExtra("members");
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().groupManager().addUsersToGroup(mgroupId,members);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Uiutils.toast("发送邀请成功");
                                getMembersFromHxServer();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Uiutils.toast("发送邀请失败");
                            }
                        });
                    }
                }
            });
        }
    }

    //删除联系人
    private void deleteMember(SectionUser user) {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().removeUserFromGroup(mgroupId, user.getPhonenumber());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getMembersFromHxServer();
                            Uiutils.toast("删除联系人成功！");
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Uiutils.toast("删除联系人失败"+e.getDescription());
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void initData() {
        getMembersFromHxServer();
    }

    private void getMembersFromHxServer() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(mgroupId);
                    // List<String> members = emGroup.getMembers();

                    List<String> members = new ArrayList<>();
                    EMCursorResult<String> result = null;
                    final int pageSize = 20;
                    do {
                        result = EMClient.getInstance().groupManager().fetchGroupMembers(mgroupId,
                                result != null ? result.getCursor() : "", pageSize);
                        members.addAll(result.getData());
                    } while (!TextUtils.isEmpty(result.getCursor()) && result.getData().size() == pageSize);

                    userList.clear();
                    if (members != null && members.size() >= 0) {
                        //转换，去自己的服务器查询每个用户的具体数据
                        CommonRequest request = new CommonRequest();
                        JSONArray jsonArray = new JSONArray();
                        for (int i= 0;i < members.size();i++) {
                            request.addRequestParam(members.get(i));
                        }
                        //添加请求参数
                        request.addRequestParam("btime","2020042811");
                        request.addRequestParam("etime","2020042811");
                        request.addRequestParam("s_section",mgroupId);
                        request.setRequestCode("section_salary");
                        requestSelfServer(request);
                    }else{
                        Uiutils.toast("该部门没有人员");
                    }

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    //到服务器请求具体数据
    public void requestSelfServer(CommonRequest request){

        HttpUtils.sendPost(ConstantValues.URL_SALARY+"querySalaryByTimeAndId",request.getJsonStr(), new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork ERROR:"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();

                if(resCode.equals("0")){
                    List<HashMap<String,String>> list = res.getMapList();
                    Log.i("list:",list.size()+"");
                    for(int i = 0;i < list.size();i++){
                        SectionUser sectionUser = new SectionUser();
                        sectionUser.setName(list.get(i).get("phonenumber"));
                        sectionUser.setPhonenumber(list.get(i).get("phonenumber"));
                        sectionUser.setAllstatics(list.get(i).get("allterm"));
                        userList.add(sectionUser);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //刷新适配器
                            detailAdapter.refresh(userList);
                        }
                    });
                }else{
                    Uiutils.toast(resMsg);
                }
            }
        });
    }

}
