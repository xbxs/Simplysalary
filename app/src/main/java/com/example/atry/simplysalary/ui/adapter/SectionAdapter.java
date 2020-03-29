package com.example.atry.simplysalary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

public class SectionAdapter extends BaseAdapter {
    private Context mcontext;
    private List<EMGroup> emGroupList = new ArrayList<>();
    public SectionAdapter(Context context){
        this.mcontext = context;
    }
    @Override
    public int getCount() {
        return mcontext == null ? 0 : emGroupList.size();
    }

    @Override
    public Object getItem(int position) {
        return emGroupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1.判断是否为空
        BoosStaticsExpandAdapter.GroupHolder groupHolder = null;
        if(null == convertView){
            convertView = View.inflate(Uiutils.getContext(), R.layout.item_salary_boosstatics,null);
            groupHolder = new BoosStaticsExpandAdapter.GroupHolder();
            groupHolder.iv_seconticon = convertView.findViewById(R.id.iv_staticssalary_sectionicon);
            groupHolder.tv_sectionname = convertView.findViewById(R.id.tv_staticssalary_sectionname);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (BoosStaticsExpandAdapter.GroupHolder) convertView.getTag();
        }
        //生成viewholder


        //赋值

        groupHolder.tv_sectionname.setText(emGroupList.get(position).getGroupName());
        //返回convertView
        return convertView;
    }
    static class GroupHolder{
        ImageView iv_seconticon;
        TextView tv_sectionname;
    }
    public void refresh(List<EMGroup> groupList){
        if(null != groupList && groupList.size() >= 0){
            emGroupList.clear();
            emGroupList.addAll(groupList);
            notifyDataSetChanged();
        }
    }


}
