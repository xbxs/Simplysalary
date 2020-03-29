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
    public PickContactAdapter(Context context,List<PickUserInfo> pickUserInfos) {
        mcontext = context;
        if(pickUserInfos != null && pickUserInfos.size() >= 0){
            pickUserInfoList.clear();
            pickUserInfoList.addAll(pickUserInfos);
        }
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
        pickContactHolder.tv_pick_name.setText(pickUserInfo.getUser().getPhonenumber());
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


}