package com.example.atry.simplysalary.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

public class BoosStaticsExpandAdapter extends BaseExpandableListAdapter {
    private Context mcontext;
    private List<EMGroup> emGroupList = new ArrayList<>();

    public BoosStaticsExpandAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    @Override
    public int getGroupCount() {
        Log.i("TAG","groupList_size:"+emGroupList.size());
        return emGroupList == null ? 0 : emGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return emGroupList.get(groupPosition) == null ? 0 : emGroupList.get(groupPosition).getMemberCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return emGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return emGroupList.get(groupPosition).getMembers().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    //是否有独立的id
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //1.判断是否为空
        GroupHolder groupHolder = null;
        if(null == convertView){
            convertView = View.inflate(Uiutils.getContext(), R.layout.item_salary_boosstatics,null);
            groupHolder = new GroupHolder();
            groupHolder.iv_seconticon = convertView.findViewById(R.id.iv_staticssalary_sectionicon);
            groupHolder.tv_sectionname = convertView.findViewById(R.id.tv_staticssalary_sectionname);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        //生成viewholder


        //赋值

        groupHolder.tv_sectionname.setText(emGroupList.get(groupPosition).getGroupName());
        //返回convertView
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if(null == convertView){
           convertView = View.inflate(Uiutils.getContext(),R.layout.item_salary_boosstaticschild,null);
           childHolder = new ChildHolder();
           childHolder.tv_name = convertView.findViewById(R.id.tv_salary_name);
           childHolder.tv_salary = convertView.findViewById(R.id.tv_salary_money);
           childHolder.tv_detail = convertView.findViewById(R.id.tv_salary_detail);

           convertView.setTag(childHolder);
        }else{
         childHolder  = (ChildHolder) convertView.getTag();
        }

        childHolder.tv_name.setText(emGroupList.get(groupPosition).getMembers().get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupHolder{
        ImageView iv_seconticon;
        TextView tv_sectionname;
    }
    static class ChildHolder{
        TextView tv_name;
        TextView tv_salary;
        TextView tv_detail;
    }

    public void refresh(List<EMGroup> groupList){
        if(null != groupList && groupList.size() >= 0){
            emGroupList.clear();
            emGroupList.addAll(groupList);
            notifyDataSetChanged();
        }
    }
}
