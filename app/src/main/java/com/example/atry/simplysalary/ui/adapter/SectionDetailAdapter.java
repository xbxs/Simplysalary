package com.example.atry.simplysalary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.SectionUser;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.utils.Uiutils;

import java.util.ArrayList;
import java.util.List;

public class SectionDetailAdapter extends BaseAdapter {
    private Context mcontext;
    private List<SectionUser> mUser = new ArrayList<>();
    public SectionDetailAdapter(Context context){
        this.mcontext = context;
    }
    @Override
    public int getCount() {
        return mUser == null ? 0 : mUser.size();
    }

    @Override
    public Object getItem(int position) {
        return mUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BoosStaticsExpandAdapter.ChildHolder childHolder = null;
        if(null == convertView){
            convertView = View.inflate(Uiutils.getContext(), R.layout.item_salary_boosstaticschild,null);
            childHolder = new BoosStaticsExpandAdapter.ChildHolder();
            childHolder.tv_name = convertView.findViewById(R.id.tv_salary_name);
            childHolder.tv_salary = convertView.findViewById(R.id.tv_salary_money);
            childHolder.tv_detail = convertView.findViewById(R.id.tv_salary_detail);

            convertView.setTag(childHolder);
        }else{
            childHolder  = (BoosStaticsExpandAdapter.ChildHolder) convertView.getTag();
        }
        childHolder.tv_name.setText(mUser.get(position).getName());
        childHolder.tv_salary.setText(mUser.get(position).getAllstatics()+"￥");
        return convertView;
    }

    static class ChildHolder{
        TextView tv_name;
        TextView tv_salary;
        TextView tv_detail;
    }
    public void refresh(List<SectionUser> users){
        if(users != null && users.size() >= 0){
            mUser.clear();
            mUser.addAll(users);
            notifyDataSetChanged();
        }
    }
}
