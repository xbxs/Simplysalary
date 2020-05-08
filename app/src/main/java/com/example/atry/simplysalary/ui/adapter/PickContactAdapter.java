package com.example.atry.simplysalary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.PickUserInfo;

import java.util.ArrayList;
import java.util.List;

public class PickContactAdapter extends BaseAdapter {
    private Context mcontext;
    private List<PickUserInfo> pickUserInfoList = new ArrayList<>();

    private List<String> mexitsMembers = new ArrayList<>();
    public PickContactAdapter(Context context,List<PickUserInfo> pickUserInfos,List<String> exitsMembers) {
        mcontext = context;
        if(pickUserInfos != null && pickUserInfos.size() >= 0){
            pickUserInfoList.clear();
            pickUserInfoList.addAll(pickUserInfos);
        }
        mexitsMembers.clear();
        mexitsMembers.addAll(exitsMembers);
    }

    @Override
    public int getCount() {
        return pickUserInfoList == null ? 0: pickUserInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return pickUserInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PickContactHolder pickContactHolder = null;
        if(null == convertView){
            pickContactHolder = new PickContactHolder();
            convertView = View.inflate(mcontext, R.layout.item_chase_contact,null);
            pickContactHolder.cb_pick = convertView.findViewById(R.id.cb_pick);
            pickContactHolder.iv_pick_head = convertView.findViewById(R.id.iv_pick_icon);
            pickContactHolder.tv_pick_name = convertView.findViewById(R.id.tv_pick_name);
            convertView.setTag(pickContactHolder);
        }else{
            pickContactHolder = (PickContactHolder) convertView.getTag();
        }
        PickUserInfo pickUserInfo =pickUserInfoList.get(position);
        pickContactHolder.cb_pick.setChecked(pickUserInfo.isChecked());
        String name = pickUserInfo.getUser().getName() == null ? pickUserInfo.getUser().getPhonenumber() : pickUserInfo.getUser().getName()+"("
                    +pickUserInfo.getUser().getPhonenumber()+")";
        pickContactHolder.tv_pick_name.setText(name);

        //判断是否已经在群里
        if(mexitsMembers.contains(pickUserInfo.getUser().getPhonenumber())){
            pickContactHolder.cb_pick.setChecked(true);
           // pickContactHolder.cb_pick.setClickable(false);
            pickUserInfo.setChecked(true);
        }
        return convertView;
    }

    public List<String> getPickContacts() {
        List<String> picks = new ArrayList<>();
        for(PickUserInfo pickUserInfo : pickUserInfoList){
            if(pickUserInfo.isChecked()){
                picks.add(pickUserInfo.getUser().getPhonenumber());
            }
        }
        return picks;
    }

    static class PickContactHolder{
        CheckBox cb_pick;
        ImageView iv_pick_head;
        TextView tv_pick_name;
    }

    public void refresh(List<String> exitsMembers){
        if(null != exitsMembers && exitsMembers.size() >=0){
            mexitsMembers.clear();
            mexitsMembers.addAll(exitsMembers);
            notifyDataSetChanged();
        }
    }


}
